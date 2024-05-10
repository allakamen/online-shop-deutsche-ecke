package ait.project.deutscheecke.users.service;

import ait.project.deutscheecke.users.dto.RolesDto;
import ait.project.deutscheecke.users.dto.UserDto;
import ait.project.deutscheecke.users.dto.UserSignupDto;
import ait.project.deutscheecke.users.dto.UserUpdateDto;

public interface UserService {

    UserDto signupUser(UserSignupDto userSignupDto);

    UserDto findUserById(Long id);

    UserDto updateUser(Long id, UserUpdateDto userUpdateDto);

    UserDto deleteUser(Long id);

    Iterable<UserDto> findUsersByCountry(String country);

    RolesDto changeRole(Long id, String role, boolean isAddRole);

    void changePassword(Long id, String newPassword);


}
