package spring.study.server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.study.server.dto.Request;
import spring.study.server.dto.UserResponse;
import spring.study.server.service.RestTemplateService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/client")
public class ClientApiController {

    private final RestTemplateService restTemplateService;

    @GetMapping("/hello")
    public UserResponse getHello() {
        return restTemplateService.hello();
    }

    @GetMapping("/user")
    public UserResponse post() {
        return restTemplateService.post();
    }

    @GetMapping("/exchange")
    public UserResponse exchange() {
        return restTemplateService.exchange();
    }

    @GetMapping("/generic-exchange")
    public Request<UserResponse> genericExchange() {
        return restTemplateService.genericExchange();
    }
}
