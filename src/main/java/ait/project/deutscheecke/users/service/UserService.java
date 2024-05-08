package ait.project.deutscheecke.users.service;

import ait.project.deutscheecke.users.dto.RolesDto;
import ait.project.deutscheecke.users.dto.UserDto;
import ait.project.deutscheecke.users.dto.UserSignupDto;
import ait.project.deutscheecke.users.dto.UserUpdateDto;

public interface UserService {

    UserDto signupUser(UserSignupDto userSignupDto);

    UserDto findUserById(Integer id);

    UserDto updateUser(Integer id, UserUpdateDto userUpdateDto);

    UserDto deleteUser(Integer id);

    Iterable<UserDto> findUsersByCountry(String country);

    RolesDto changeRole(Integer id, String role, boolean isAddRole);

    void changePassword(Integer id, String newPassword);


}
