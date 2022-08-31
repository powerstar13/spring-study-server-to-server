package spring.study.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import spring.study.server.dto.Request;
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

    public UserResponse exchange() {

        URI uri = UriComponentsBuilder
            .fromUriString("http://localhost:9090")
            .path("/api/server/exchange/user/{userId}/name/{userName}")
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

        RequestEntity<UserRequest> requestEntity = RequestEntity
            .post(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .header("x-authorization", "abcd")
            .header("custom-header", "fffff")
            .body(request);

        ResponseEntity<UserResponse> response = restTemplate.exchange(requestEntity, UserResponse.class);

        return response.getBody();
    }

    public Request<UserResponse> genericExchange() {

        URI uri = UriComponentsBuilder
            .fromUriString("http://localhost:9090")
            .path("/api/server/generic-exchange/user/{userId}/name/{userName}")
            .encode()
            .build()
            .expand(100, "master") // expand() 메서드는 path에서 지정한 PathVariable 자리에 순서대로 반영해준다.
            .toUri();

        System.out.println("uri = " + uri);

        // http body -> object -> object mapper -> json -> rest template -> http body json
        UserRequest userRequest = UserRequest.builder()
            .name("master")
            .age(10)
            .build();

        Request<UserRequest> request = Request.<UserRequest>builder()
            .header(new Request.Header())
            .httpBody(userRequest)
            .build();

        RequestEntity<Request<UserRequest>> requestEntity = RequestEntity
            .post(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .header("x-authorization", "abcd")
            .header("custom-header", "fffff")
            .body(request);

        ResponseEntity<Request<UserResponse>> response = restTemplate.exchange(requestEntity, new ParameterizedTypeReference<>() {
        }); // 매개 변수로 제네릭 타입은 .class로 보낼 수 없으므로 ParameterizedTypeReference를 생성할 때 담아주면 된다.

        return response.getBody();
    }
}
