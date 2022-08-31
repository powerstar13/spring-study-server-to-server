package spring.study.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

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
