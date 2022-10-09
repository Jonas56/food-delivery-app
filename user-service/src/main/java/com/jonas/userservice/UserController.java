package com.jonas.userservice;

import com.jonas.userservice.dto.*;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1", produces = "application/json")
public class UserController {

    private final IUserService userService;
    private final ModelMapper modelMapper;

    public UserController(IUserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/register")
    public UserResponseDto registerNewUser(@RequestBody @Valid UserRequestDto userRequestDto) {
        User user = modelMapper.map(userRequestDto, User.class);
        return modelMapper.map(userService.register(user), UserResponseDto.class);
    }

    @GetMapping("/users/me")
    public UserProfileResponseDto getUserProfile(HttpServletRequest request) {
        return modelMapper.map(userService.getUserProfile(request), UserProfileResponseDto.class);
    }

    @PutMapping("/user/edit")
    public UserResponseDto editUser(HttpServletRequest request, HttpServletResponse response, @RequestBody @Valid UserRequestForProfileDto userRequestDto) {
        User user = modelMapper.map(userRequestDto, User.class);
        return modelMapper.map(userService.editUser(request, response, user), UserResponseDto.class);
    }

    @PutMapping("/user/edit-password")
    public String editPassword(HttpServletRequest request, HttpServletResponse response, @RequestBody @Valid UserPasswordRequestDto userRequestDto) {
        return userService.editPassword(request, response, userRequestDto);
    }
}
