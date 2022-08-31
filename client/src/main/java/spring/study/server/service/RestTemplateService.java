package spring.study.server.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import spring.study.server.dto.UserResponse;

import java.net.URI;

@Service
public class RestTemplateService {

    public UserResponse hello() {

        URI uri = UriComponentsBuilder
            .fromUriString("http://localhost:9090")
            .path("/api/server/hello")
            .queryParam("name", "master")
            .queryParam("age", 10)
            .encode()
            .build()
            .toUri();

        System.out.println("uri = " + uri.toString());

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UserResponse> result = restTemplate.getForEntity(uri, UserResponse.class);

        System.out.println("result status code = " + result.getStatusCode());
        System.out.println("result body = " + result.getBody());

        return result.getBody();
    }
}
