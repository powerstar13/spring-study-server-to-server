package spring.study.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import spring.study.server.dto.Request;
import spring.study.server.dto.User;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@Slf4j
@RestController
@RequestMapping("/api/server")
public class ServerApiController {

    @GetMapping("/hello")
    public User hello(@RequestParam String name, @RequestParam int age) {

        return User.builder()
            .name(name)
            .age(age)
            .build();
    }

    @PostMapping("/user/{userId}/name/{userName}")
    public User post(
        @RequestBody User user,
        @PathVariable int userId,
        @PathVariable String userName
    ) {
        log.info("client request: {}", user);
        log.info("userId: {}, userName: {}", userId, userName);

        return user;
    }

    @PostMapping("/exchange/user/{userId}/name/{userName}")
    public User exchange(
        @RequestBody User user,
        @PathVariable int userId,
        @PathVariable String userName,
        @RequestHeader("x-authorization") String authorization,
        @RequestHeader("custom-header") String customHeader
    ) {
        log.info("client request: {}", user);
        log.info("userId: {}, userName: {}", userId, userName);
        log.info("authorization: {}, customHeader: {}", authorization, customHeader);

        return user;
    }

    @PostMapping("/generic-exchange/user/{userId}/name/{userName}")
    public Request<User> genericExchange(
//        HttpEntity<String> entity, // 순수한 Request를 확인할 수 있다.
        @RequestBody Request<User> user,
        @PathVariable int userId,
        @PathVariable String userName,
        @RequestHeader("x-authorization") String authorization,
        @RequestHeader("custom-header") String customHeader
    ) {
//        log.info("entity: {}", entity.getBody());
        log.info("client request: {}", user);
        log.info("userId: {}, userName: {}", userId, userName);
        log.info("authorization: {}, customHeader: {}", authorization, customHeader);

        Request<User> response = Request.<User>builder()
            .header(new Request.Header())
            .httpBody(user.getHttpBody())
            .build();

        return response;
    }

    @GetMapping("/naver")
    public String naver() throws IOException {

        String query = "갈비집";

        URI uri = UriComponentsBuilder
            .fromUriString("https://openapi.naver.com")
            .path("/v1/search/local.json")
            .queryParam("query", query)
            .queryParam("display", 10)
            .queryParam("start", 1)
            .queryParam("sort", "random")
            .encode(StandardCharsets.UTF_8)
            .build()
            .toUri();

        RestTemplate restTemplate = new RestTemplate();

        Resource targetFileResource = new ClassPathResource("naver-api.properties");

        FileInputStream fis = new FileInputStream(targetFileResource.getURI().getPath());

        Properties prop = new Properties();

        prop.load(fis);

        RequestEntity<Void> request = RequestEntity
            .get(uri)
            .header("X-Naver-Client-Id", prop.getProperty("X-Naver-Client-Id"))
            .header("X-Naver-Client-Secret", prop.getProperty("X-Naver-Client-Secret"))
            .build();

        ResponseEntity<String> result = restTemplate.exchange(request, String.class);

        return result.getBody();
    }
}
