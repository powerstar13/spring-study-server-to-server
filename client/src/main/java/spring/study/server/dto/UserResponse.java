package spring.study.server.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {

    private String name;
    private int age;

    @Override
    public String toString() {
        return "UserResponse{" +
            "name='" + name + '\'' +
            ", age=" + age +
            '}';
    }
}
