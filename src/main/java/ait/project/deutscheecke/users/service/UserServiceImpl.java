package ait.project.deutscheecke.users.service;

import ait.project.deutscheecke.users.dao.UserRepository;
import ait.project.deutscheecke.users.dto.RolesDto;
import ait.project.deutscheecke.users.dto.UserDto;
import ait.project.deutscheecke.users.dto.UserSignupDto;
import ait.project.deutscheecke.users.dto.UserUpdateDto;
import ait.project.deutscheecke.users.model.User;
import ait.project.deutscheecke.exceptions.PersonNotFoundException;
import ait.project.deutscheecke.exceptions.UserExistsException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class UserServiceImpl implements UserService {

    final UserRepository userRepository;
    final ModelMapper modelMapper;

    @Transactional
    @Override
    public UserDto signupUser(UserSignupDto userSignupDto) {
        if (userRepository.findByEmail(userSignupDto.getEmail()).isPresent()) {
            throw new UserExistsException();
        }
        User user = modelMapper.map(userSignupDto, User.class);
        user.setRole(User.Role.CLIENT);
        userRepository.save(user);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto findUserById(Integer id) {
        User user = userRepository.findById(id).orElseThrow(PersonNotFoundException::new);
        return modelMapper.map(user, UserDto.class);
    }

    @Transactional
    @Override
    public UserDto updateUser(Integer id, UserUpdateDto userUpdateDto) {
        User user = userRepository.findById(id).orElseThrow(PersonNotFoundException::new);
        if (!userUpdateDto.getFirstName().isEmpty()) {
            user.setFirstName(userUpdateDto.getFirstName());
        }
        if (!userUpdateDto.getLastName().isEmpty()) {
            user.setLastName(userUpdateDto.getLastName());
        }
        if (!userUpdateDto.getEmail().isEmpty()) {
            user.setEmail(userUpdateDto.getEmail());
        }
        if (!userUpdateDto.getCountry().isEmpty()) {
            user.setCountry(userUpdateDto.getCountry());
        }
        if (!userUpdateDto.getLogin().isEmpty()) {
            user.setLogin(userUpdateDto.getLogin());
        }
        return modelMapper.map(user, UserDto.class);
    }

    @Transactional
    @Override
    public UserDto deleteUser(Integer id) {
        User user = userRepository.findById(id).orElseThrow(PersonNotFoundException::new);
        userRepository.delete(user);
        return modelMapper.map(user, UserDto.class);

    }

    @Transactional(readOnly = true)
    @Override
    public Iterable<UserDto> findUsersByCountry(String country) {
        return userRepository.findByCountry(country)
                .map(u -> modelMapper.map(u, UserDto.class))
                .collect(Collectors.toSet());
    }

    @Override
    public RolesDto changeRole(Integer id, String role, boolean isAddRole) {
        //TODO:
        return null;
    }

    @Override
    public void changePassword(Integer id, String newPassword) {
        //TODO: AAA
    }
}
