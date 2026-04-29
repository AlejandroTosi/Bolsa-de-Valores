package com.Alejandro.BolsaDeValores.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    // Use constructor injection for all dependencies
    public UserService(UserRepository userRepository, TokenService tokenService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto.AuthResponseDto login(UserDto.LoginDto dto) {
        UserModel user = userRepository.findByEmail(dto.email())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(dto.password(), user.getPassword_hash())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = tokenService.generateToken(user.getEmail());

        // Match the record structure: String, long, UserResponseDto
        return new UserDto.AuthResponseDto(
                token,
                86400000, // 24 hours in millis
                mapToUserResponseDto(user)
        );
    }

    @Transactional
    public UserDto.UserResponseDto register(UserDto.RegisterDto dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new RuntimeException("Email already in use: " + dto.email());
        }

        UserModel newUser = new UserModel();
        newUser.setName(dto.username());
        newUser.setEmail(dto.email());
        newUser.setPassword_hash(passwordEncoder.encode(dto.password()));

        return mapToUserResponseDto(userRepository.save(newUser));
    }

    @Transactional
    public UserDto.UserResponseDto updateUser(Long id, UserDto.UpdateUserDto dto) {
        UserModel existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!existingUser.getEmail().equalsIgnoreCase(dto.email()) &&
                userRepository.existsByEmail(dto.email())) {
            throw new RuntimeException("Email already in use");
        }

        existingUser.setName(dto.username());
        existingUser.setEmail(dto.email());
        return mapToUserResponseDto(userRepository.save(existingUser));
    }

    @Transactional
    public void changePassword(Long id, UserDto.ChangePasswordDto dto) {
        UserModel user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Logic fix: Encode the new password before saving!
        user.setPassword_hash(passwordEncoder.encode(dto.new_password()));
        userRepository.save(user);
    }

    @Transactional
    public UserDto.UserResponseDto addDiscordId(Long id, UserDto.AddDiscordIdDto dto) {
        UserModel user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setDiscord(dto.discord());
        return mapToUserResponseDto(userRepository.save(user));
    }

    private UserDto.UserResponseDto mapToUserResponseDto(UserModel user) {
        return new UserDto.UserResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getDiscord()
        );
    }
}