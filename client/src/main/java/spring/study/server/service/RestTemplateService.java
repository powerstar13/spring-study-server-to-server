package spring.study.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import spring.study.server.dto.UserRequest;
import spring.study.server.dto.UserResponse;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class RestTemplateService {

    private final RestTemplate restTemplate;

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

        ResponseEntity<UserResponse> response = restTemplate.getForEntity(uri, UserResponse.class);

        System.out.println("response status code = " + response.getStatusCode());
        System.out.println("response body = " + response.getBody());

        return response.getBody();
    }

    public UserResponse post() {

        URI uri = UriComponentsBuilder
            .fromUriString("http://localhost:9090")
            .path("/api/server/user/{userId}/name/{userName}")
            .encode()
            .build()
            .expand(100, "master") // expand() 메서드는 path에서 지정한 PathVariable 자리에 순서대로 반영해준다.
            .toUri();

        System.out.println("uri = " + uri);

        // http body -> object -> object mapper -> json -> rest template -> http body json
        UserRequest request = UserRequest.builder()
            .name("master")
            .age(10)
            .build();

        ResponseEntity<UserResponse> response = restTemplate.postForEntity(uri, request, UserResponse.class);

        System.out.println("response status code = " + response.getStatusCode());
        System.out.println("response headers = " + response.getHeaders());
        System.out.println("response body = " + response.getBody());

        return response.getBody();
    }
}
