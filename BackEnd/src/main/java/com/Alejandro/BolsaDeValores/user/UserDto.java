package com.Alejandro.BolsaDeValores.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserDto {
    
    public record CreateUserDto(
            @NotBlank(message = "Username cannot be blank")
            @Size(min = 3, max = 100, message = "Username must be between 3 and 100 characters")
            String username,
            
            @NotBlank(message = "Email cannot be blank")
            @Email(message = "Email should be valid")
            String email,
            
            @NotBlank(message = "Password cannot be blank")
            @Size(min = 6, max = 255, message = "Password must be between 6 and 255 characters")
            String password
    ){}

    public record UpdateUserDto(
            @NotBlank(message = "Username cannot be blank")
            @Size(min = 3, max = 100, message = "Username must be between 3 and 100 characters")
            String username,
            
            @NotBlank(message = "Email cannot be blank")
            @Email(message = "Email should be valid")
            String email
    ){}

    public record ChangePasswordDto(
            @NotBlank(message = "Current password cannot be blank")
            String current_password,
            
            @NotBlank(message = "New password cannot be blank")
            @Size(min = 6, max = 255, message = "Password must be between 6 and 255 characters")
            String new_password
    ){}

    public record AddDiscordIdDto(
            @NotBlank(message = "Discord ID cannot be blank")
            String discord
    ){}

    public record UserResponseDto(
            Long id,
            String name,
            String email,
            String discord
    ){}

    public record LoginDto(
            @NotBlank(message = "Email cannot be blank")
            @Email(message = "Email should be valid")
            String email,
            
            @NotBlank(message = "Password cannot be blank")
            String password
    ){}

    public record AuthResponseDto(
            String access_token,
            String refresh_token,
            String token_type,
            long expires_in,
            UserResponseDto user
    ){}
}


