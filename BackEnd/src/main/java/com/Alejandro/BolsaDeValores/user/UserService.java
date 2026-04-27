package com.Alejandro.BolsaDeValores.user;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserService {
    
    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }


    public UserDto.UserResponseDto login(UserDto.LoginDto dto) {

        UserModel user = userRepository.findByEmail(dto.email())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        return mapToUserResponseDto(user);
    }

    @Transactional
    public UserDto.UserResponseDto createUser(UserDto.CreateUserDto dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new RuntimeException("Email already in use: " + dto.email());
        }
        UserModel newUser = new UserModel();
        newUser.setName(dto.username());
        newUser.setEmail(dto.email());
        UserModel savedUser = userRepository.save(newUser);
        return mapToUserResponseDto(savedUser);
            }

    @Transactional
    public UserDto.UserResponseDto updateUser(Long id, UserDto.UpdateUserDto dto) {

        UserModel existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));

        // Check if new email already exists (and is different)
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
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getDiscord()
        );
    }
}


