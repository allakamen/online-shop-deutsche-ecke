package ait.project.deutscheecke.cart.model;


import ait.project.deutscheecke.users.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "cart")
public class Cart {
    @Id
    private Long id;


    @OneToOne
    @JoinColumn(name = "users_id")
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartItem> items = new ArrayList<>();

}
