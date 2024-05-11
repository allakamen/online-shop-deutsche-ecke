package ait.project.deutscheecke.users.dto;

import lombok.*;

import java.util.Set;

@Getter
public class UserSignupDto {

    String login;
    String password;
    String firstName;
    String lastName;
    String email;
    String country;


}
