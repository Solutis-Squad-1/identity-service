package br.com.solutis.squad1.identityservice.controller;

import br.com.solutis.squad1.identityservice.dto.user.UserPutDto;
import br.com.solutis.squad1.identityservice.dto.user.UserResponseDetailedDto;
import br.com.solutis.squad1.identityservice.dto.user.UserResponseDto;
import br.com.solutis.squad1.identityservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/identity/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    /**
     * Find all users
     * @param pageable
     * @return Page<UserResponseDto>
     */
    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Page<UserResponseDto> findAll(Pageable pageable) {
        return userService.findAll(pageable);
    }

    /**
     * Find user by id
     * @param id
     * @return UserResponseDto
     */
    @GetMapping("/details")
    @PreAuthorize("hasAuthority('user:read')")
    public UserResponseDetailedDto findById(Principal principal) {
        return userService.findByName(principal.getName());
    }

    /**
     * Update user
     * @param userPutDto
     * @return UserResponseDetailedDto
     */
    @PutMapping
    @PreAuthorize("hasAuthority('user:update')")
    public UserResponseDetailedDto update(Principal principal, @RequestBody UserPutDto userPutDto) {
        return userService.updateByName(principal.getName(), userPutDto);
    }

    /**
     * Delete user
     */
    @DeleteMapping
    @PreAuthorize("hasAuthority('user:delete')")
    public void delete(Principal principal) {
        userService.deleteByName(principal.getName());
    }

    // TODO Endpoint para atualizar a senha
}
