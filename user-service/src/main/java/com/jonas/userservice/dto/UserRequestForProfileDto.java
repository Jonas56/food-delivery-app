package com.jonas.userservice.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
@Setter
@Getter
public class UserRequestForProfileDto {

    @NotEmpty(message = "Name cannot be empty!")
    private String name;
    @NotEmpty(message = "username cannot be empty!")
    private String username;
    @Email(message = "Please provide a valid email")
    @NotEmpty(message = "email cannot be empty!")
    private String email;
}
