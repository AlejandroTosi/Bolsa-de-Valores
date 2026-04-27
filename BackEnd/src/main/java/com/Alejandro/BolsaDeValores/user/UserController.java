package com.Alejandro.BolsaDeValores.user;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public UserDto.UserResponseDto login(@RequestBody UserDto.LoginDto dto) {
        System.out.println("");
        System.out.println("");
        System.out.print(dto);
        System.out.println("");
        System.out.println("");

        return service.login(dto);
    }

    @PostMapping("/register")
    public UserDto.UserResponseDto register(@RequestBody UserDto.RegisterDto dto) {
        return service.register(dto);
    }

    @PutMapping("/{id}")
    public UserDto.UserResponseDto update(
            @PathVariable Long id,
            @RequestBody UserDto.UpdateUserDto dto) {

        return service.updateUser(id, dto);
    }

    @PutMapping("/{id}/password")
    public void changePassword(
            @PathVariable Long id,
            @RequestBody UserDto.ChangePasswordDto dto) {

        service.changePassword(id, dto);
    }

    @PutMapping("/{id}/discord")
    public UserDto.UserResponseDto addDiscord(
            @PathVariable Long id,
            @RequestBody UserDto.AddDiscordIdDto dto) {

        return service.addDiscordId(id, dto);
    }
}
