package spring.study.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import spring.study.server.dto.Request;
import spring.study.server.dto.User;

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
}
