package com.Alejandro.BolsaDeValores.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.tokenService = new TokenService();
        this.passwordEncoder = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();

    }


    public UserDto.UserResponseDto login(UserDto.LoginDto dto) {

        UserModel user = userRepository.findByEmail(dto.email())
                .orElseThrow(() ->
                        new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(
                dto.password(),
                user.getPassword_hash()
        )) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = tokenService.generateToken(user.getEmail());

        return new UserDto.UserResponseDto(
                token,
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getDiscord()
        );
    }

    @Transactional
    public UserDto.UserResponseDto updateUser(Long id, UserDto.UpdateUserDto dto) {

        UserModel existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));

        if (!existingUser.getEmail().equalsIgnoreCase(dto.email()) &&
                userRepository.existsByEmail(dto.email())) {
            throw new RuntimeException("Email already in use: " + dto.email());
        }

        existingUser.setName(dto.username());
        existingUser.setEmail(dto.email());

        UserModel updatedUser = userRepository.save(existingUser);
        return mapToUserResponseDto(updatedUser);
    }

    @Transactional
    public void changePassword(Long id, UserDto.ChangePasswordDto dto) {

        UserModel user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword_hash(dto.new_password());

        userRepository.save(user);
    }

    @Transactional
    public UserDto.UserResponseDto addDiscordId(Long id, UserDto.AddDiscordIdDto dto) {

        UserModel user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));

        user.setDiscord(dto.discord());
        UserModel updatedUser = userRepository.save(user);
        return mapToUserResponseDto(updatedUser);
    }


    private UserDto.UserResponseDto mapToUserResponseDto(UserModel user) {
        return new UserDto.UserResponseDto(
                null,
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getDiscord()
        );
    }

    @Autowired
    private PasswordEncoder encoder;

    public UserDto.UserResponseDto register(UserDto.RegisterDto dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new RuntimeException("Email already in use: " + dto.email());

        }
        UserModel newUser = new UserModel();
        newUser.setName(dto.username());
        newUser.setEmail(dto.email());
        newUser.setPassword_hash(
                encoder.encode(dto.password())
        );
        newUser = userRepository.save(newUser);
        return mapToUserResponseDto(newUser);
    }
}


