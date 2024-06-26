package ait.project.deutscheecke.users.dto;

import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class UserDto {
    Integer id;
    String login;
    String password;
    String firstName;
    String lastName;
    String email;
    String country;
    String role;
}
