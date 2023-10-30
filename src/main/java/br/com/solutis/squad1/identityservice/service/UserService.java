package br.com.solutis.squad1.identityservice.service;

import br.com.solutis.squad1.identityservice.dto.user.UserRegisterDto;
import br.com.solutis.squad1.identityservice.dto.user.UserResponseDto;
import br.com.solutis.squad1.identityservice.exception.BadRequestException;
import br.com.solutis.squad1.identityservice.mapper.UserMapper;
import br.com.solutis.squad1.identityservice.model.entity.user.Role;
import br.com.solutis.squad1.identityservice.model.entity.user.User;
import br.com.solutis.squad1.identityservice.model.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserResponseDto save(UserRegisterDto userRegisterDto) {
        User user = userRepository.findUserByUsername(userRegisterDto.username());

        if (user != null) {
            throw new BadRequestException("Invalid username");
        }

        user = userMapper.registerDtoToEntity(userRegisterDto);
        user.setPassword(passwordEncoder.encode(userRegisterDto.password()));
        user.setRole(Role.USER);

        return userMapper.toResponseDto(userRepository.save(user));
    }
}
