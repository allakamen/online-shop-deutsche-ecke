package ait.project.deutscheecke.users.model;

import ait.project.deutscheecke.cart.model.Cart;
import ait.project.deutscheecke.order.model.Order;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


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
    Long id;
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

    @Setter
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Cart cart;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orders;


}
