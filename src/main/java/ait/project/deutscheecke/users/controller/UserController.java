package ait.project.deutscheecke.users.controller;

import ait.project.deutscheecke.users.dto.RolesDto;
import ait.project.deutscheecke.users.dto.UserDto;
import ait.project.deutscheecke.users.dto.UserSignupDto;
import ait.project.deutscheecke.users.dto.UserUpdateDto;
import ait.project.deutscheecke.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    final UserService userService;

    @PostMapping("/signup")
    public UserDto signupUser(@RequestBody UserSignupDto userSignupDto) {
        return userService.signupUser(userSignupDto);
    }

    @PostMapping("/login")
    public UserDto login() {
       //TODO: implement Authorization
        return null;
    }

    @GetMapping("/user/{id}")
    public UserDto findUserById(@PathVariable Integer id) {
        return userService.findUserById(id);
    }

    @PutMapping("/user/{id}")
    public UserDto updateUser(@PathVariable Integer id, @RequestBody UserUpdateDto userUpdateDto) {
        return userService.updateUser(id, userUpdateDto);
    }

    @DeleteMapping("/user/{id}")
    public UserDto deleteUser(@PathVariable Integer id) {
        return userService.deleteUser(id);
    }

    @GetMapping("/country/{country}")
    public Iterable<UserDto> findUsersByCountry(@PathVariable String country) {
        return userService.findUsersByCountry(country);
    }

    @PutMapping("/user/{id}/role/{role}")
    public RolesDto addRole(@PathVariable Integer id, @PathVariable String role) {
        return userService.changeRole(id, role, true);
    }

    @DeleteMapping("/user/{id}/role/{role}")
    public RolesDto deleteRole(@PathVariable Integer id, @PathVariable String role) {
        return userService.changeRole(id, role, false);
    }

    @PutMapping("/password")
    //TODO: Authorization
    public void changePassword(Integer id, @RequestHeader("X-Password") String newPassword) {

    }
}
