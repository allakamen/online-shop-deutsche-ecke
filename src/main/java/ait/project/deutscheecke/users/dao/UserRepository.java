package ait.project.deutscheecke.users.dao;

import ait.project.deutscheecke.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.stream.Stream;

public interface UserRepository extends JpaRepository<User, Long> {

   Optional<User> findByEmail(String email);
   Stream<User> findByCountry(String country);

}
