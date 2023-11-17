package br.com.solutis.squad1.identityservice.service;

import br.com.solutis.squad1.identityservice.dto.user.UserPutDto;
import br.com.solutis.squad1.identityservice.dto.user.UserRegisterDto;
import br.com.solutis.squad1.identityservice.dto.user.UserResponseDetailedDto;
import br.com.solutis.squad1.identityservice.dto.user.UserResponseDto;
import br.com.solutis.squad1.identityservice.exception.BadRequestException;
import br.com.solutis.squad1.identityservice.mapper.AddressMapper;
import br.com.solutis.squad1.identityservice.mapper.UserMapper;
import br.com.solutis.squad1.identityservice.model.entity.Address;
import br.com.solutis.squad1.identityservice.model.entity.user.Role;
import br.com.solutis.squad1.identityservice.model.entity.user.User;
import br.com.solutis.squad1.identityservice.model.repository.AddressRepository;
import br.com.solutis.squad1.identityservice.model.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final AddressMapper addressMapper;

    /**
     * Find all users
     * @param pageable
     * @return Page<UserResponseDto>
     */
    public Page<UserResponseDto> findAll(Pageable pageable) {
        log.info("Finding all users");
        return userRepository.findAll(pageable).map(userMapper::toResponseDto);
    }

    /**
     * Find user by id
     * @param id
     * @return UserResponseDto
     */
    public UserResponseDetailedDto findByName(String name) {
        log.info("Finding user by name {}", name);
        User user = userRepository.findWithAddressByUsernameAndDeletedFalse(name);
        return userMapper.toResponseDetailedDto(user);
    }

    /**
     * Save user
     * @param userRegisterDto
     * @return UserResponseDto
     */
    public UserResponseDto save(UserRegisterDto userRegisterDto) {
        log.info("Saving user {}", userRegisterDto.username());
        User user = userRepository.findUserByUsername(userRegisterDto.username());

        if (user != null) {
            throw new BadRequestException("Invalid username");
        }

        user = userMapper.registerDtoToEntity(userRegisterDto);
        user.setPassword(passwordEncoder.encode(userRegisterDto.password()));
        user.setRole(Role.USER);

        return userMapper.toResponseDto(userRepository.save(user));
    }

    /**
     * Update user
     * @param userPutDto
     * @return UserResponseDetailedDto
     */
    public UserResponseDetailedDto updateByName(String name, UserPutDto userPutDto) {
        log.info("Updating user {}", name);
        User user = userRepository.getReferenceById(userRepository.findWithAddressByUsernameAndDeletedFalse(name).getId());

        if (user.getAddress() == null && userPutDto.address() != null) {
            Address address = addressRepository.save(addressMapper.putDtoToEntity(userPutDto.address()));
            user.setAddress(address);
        }

        user.update(userMapper.putDtoToEntity(userPutDto));
        return userMapper.toResponseDetailedDto(userRepository.save(user));
    }

    /**
     * Update user confirmed status
     * @param username
     * @param confirmed
     * @return UserResponseDto
     */
    public UserResponseDto updateConfirmedByName(String username, boolean confirmed) {
        log.info("Updating user {} confirmed status to {}", username, confirmed);
        User user = userRepository.getReferenceById(userRepository.findUserByUsernameAndDeletedFalse(username).getId());
        user.setConfirmed(confirmed);

        return userMapper.toResponseDto(user);
    }

    /**
     * Update user enabled status
     * @param username
     * @param enabled
     * @return UserResponseDto
     */
    public UserResponseDto demoteRoleByName(String username) {
        log.info("Demoting user {} role", username);
        User user = userRepository.getReferenceById(userRepository.findUserByUsernameAndDeletedFalse(username).getId());
        if (user.getRole() == Role.CLIENT) {
            user.setRole(Role.USER);
        } else if (user.getRole() == Role.SELLER_CLIENT) {
            user.setRole(Role.SELLER);
        }

        return userMapper.toResponseDto(user);
    }

    /**
     * Update user enabled status
     * @param username
     * @param enabled
     * @return UserResponseDto
     */
    public UserResponseDto promoteRoleByName(String username) {
        log.info("Promoting user {} role", username);
        User user = userRepository.getReferenceById(userRepository.findUserByUsernameAndDeletedFalse(username).getId());
        if (user.getRole() == Role.USER) {
            user.setRole(Role.CLIENT);
        } else if (user.getRole() == Role.SELLER) {
            user.setRole(Role.SELLER_CLIENT);
        }

        return userMapper.toResponseDto(user);
    }

    /**
     * Delete user by id
     * @param id
     */
    public void deleteByName(String name) {
        log.info("Deleting user {}", name);
        User user = userRepository.getReferenceById(userRepository.findUserByUsernameAndDeletedFalse(name).getId());
        user.delete();
    }
}
