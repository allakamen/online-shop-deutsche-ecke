package ait.project.deutscheecke.users.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "users")
public class User {

    public enum Role {
        CLIENT, MANAGER, ADMINISTRATOR;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Setter
    String login;
    @Setter
    String password;
    @Setter
    String firstName;
    @Setter
    String lastName;
    @Setter
    String email;
    @Setter
    String country;
    @Enumerated(value = EnumType.STRING)
    @Setter
    Role role;


}
