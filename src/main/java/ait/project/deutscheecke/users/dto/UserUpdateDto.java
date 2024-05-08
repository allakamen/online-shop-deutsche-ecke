package ait.project.deutscheecke.users.dto;

import lombok.Getter;

@Getter
public class UserUpdateDto {
    String login;
    String firstName;
    String lastName;
    String email;
    String country;

}
