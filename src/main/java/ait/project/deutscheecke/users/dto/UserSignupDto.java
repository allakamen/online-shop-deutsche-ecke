package ait.project.deutscheecke.users.dto;

import lombok.Getter;

@Getter
public class UserSignupDto {
    int id;
    String login;
    String password;
    String email;
    String firstName;
    String lastName;
    String address;
    String phone;
}
