package com.jonas.userservice.dto;

import com.scs.nemo.order.dto.OrderResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
public class UserProfileResponseDto {
    private String name;
    private String username;
    private String email;
    private String userAvatar;
    private List<OrderResponseDto> orders;
}
