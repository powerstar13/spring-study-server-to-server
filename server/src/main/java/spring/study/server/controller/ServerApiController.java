package spring.study.server.controller;

import org.springframework.web.bind.annotation.*;
import spring.study.server.dto.User;

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
}
