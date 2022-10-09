package com.jonas.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
public class UserResponseDto {
    private String name;
    private String username;
    private String email;
    private String userAvatar;
}
