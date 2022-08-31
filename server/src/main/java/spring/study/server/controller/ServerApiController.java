package spring.study.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
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
    public User post(@RequestBody User user, @PathVariable int userId, @PathVariable String userName) {
        log.info("client request: {}", user);
        log.info("userId: {}, userName: {}", userId, userName);

        return user;
    }
}
