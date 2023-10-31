package br.com.solutis.squad1.identityservice.service;

import br.com.solutis.squad1.identityservice.dto.user.UserPutDto;
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

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public List<UserResponseDto> findAll() {
        return userMapper.toResponseDto(userRepository.findAllByDeletedFalse());
    }

    public UserResponseDto findByName(String name) {
        return userMapper.toResponseDto(userRepository.findUserByUsername(name));
    }

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

    public UserResponseDto updateByName(String name, UserPutDto userPutDto) {
        User user = userRepository.getReferenceById(userRepository.findUserByUsername(name).getId());
        user.update(userMapper.putDtoToEntity(userPutDto));
        return userMapper.toResponseDto(userRepository.save(user));
    }

    public void updateConfirmedByName(String username, boolean confirmed) {
        User user = userRepository.getReferenceById(userRepository.findUserByUsername(username).getId());
        user.setConfirmed(confirmed);
    }

    public void demoteRoleByName(String username) {
        User user = userRepository.getReferenceById(userRepository.findUserByUsername(username).getId());
        if (user.getRole() == Role.CLIENT) {
            user.setRole(Role.USER);
        } else if (user.getRole() == Role.SELLER_CLIENT) {
            user.setRole(Role.SELLER);
        }
    }

    public void promoteRoleByName(String username) {
        User user = userRepository.getReferenceById(userRepository.findUserByUsername(username).getId());
        if (user.getRole() == Role.USER) {
            user.setRole(Role.CLIENT);
        } else if (user.getRole() == Role.SELLER) {
            user.setRole(Role.SELLER_CLIENT);
        }
    }

    public void deleteByName(String name) {
        User user = userRepository.getReferenceById(userRepository.findUserByUsername(name).getId());
        user.delete();
    }
}
