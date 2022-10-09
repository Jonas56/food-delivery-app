package com.jonas.userservice;


import com.jonas.userservice.dto.UserPasswordRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class UserServiceImp implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImp(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(User user) {
        boolean userExists = userRepository.existsByUsernameOrEmail(user.getUsername(), user.getEmail());
        if (userExists) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User getUserProfile(HttpServletRequest request) {
        String username = JwtUtil.extractUsernameFromRequest(request);
        return userRepository.findByUsername(username)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
                );
    }

    @Override
    public User editUser(HttpServletRequest request, HttpServletResponse response, User user) {
        String username = JwtUtil.extractUsernameFromRequest(request);
        User userUpdated = userRepository.findByUsername(username)
                .map(u -> {
                    u.setName(user.getName());
                    u.setUsername(user.getUsername());
                    u.setEmail(user.getEmail());
                    return userRepository.save(u);
                })
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with username " + username + " not found")
                );

        // refresh token if username changed
        if (!username.equals(userUpdated.getUsername())) {
            JwtUtil.refreshToken(response, userUpdated.getUsername());
        }
        return userUpdated;
    }

    @Override
    public String editPassword(HttpServletRequest request, HttpServletResponse response, UserPasswordRequestDto user) {
        String username = JwtUtil.extractUsernameFromRequest(request);
        if (!passwordEncoder.matches(user.getOldPassword(), userRepository.findByUsername(username).get().getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Old password is incorrect");
        }
        if (user.getNewPassword().equals(user.getOldPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "New password must be different from old password");
        }
        User updatedUser = userRepository.findByUsername(username)
                .map(u -> {
                    u.setPassword(passwordEncoder.encode(user.getNewPassword()));
                    return userRepository.save(u);
                })
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with username " + username + " not found")
                );
        return "Password updated successfully";
    }
}
