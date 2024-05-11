package ait.project.deutscheecke.users.dto;

import lombok.*;

import java.util.Set;
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RolesDto {
    String login;
    String role;
}
