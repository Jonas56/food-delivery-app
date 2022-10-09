package com.jonas.userservice.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserPasswordRequestDto {
    @NotEmpty(message = "old password cannot be empty!")
    private String oldPassword;
    @NotEmpty(message = "password cannot be empty!")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=(.*[a-z])+)(?=(.*[\\d])+)(?=(.*[\\W])+)(?!.*\\s).{8,}$",
            message = "New password must contain minimum eight characters. \n " +
                    "At least: one uppercase letter. \n " +
                    "One lowercase letter. \n " +
                    "One number and one special character"
    )
    private String newPassword;
}
