package br.com.solutis.squad1.identityservice.controller;

import br.com.solutis.squad1.identityservice.dto.user.UserPutDto;
import br.com.solutis.squad1.identityservice.dto.user.UserResponseDetailedDto;
import br.com.solutis.squad1.identityservice.dto.user.UserResponseDto;
import br.com.solutis.squad1.identityservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Find all users")
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
    @Operation(summary = "Find user by id")
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
    @Operation(summary = "Update user")
    @PutMapping
    @PreAuthorize("hasAuthority('user:update')")
    public UserResponseDetailedDto update(Principal principal, @RequestBody UserPutDto userPutDto) {
        return userService.updateByName(principal.getName(), userPutDto);
    }

    /**
     * Delete user
     */
    @Operation(summary = "Delete user")
    @DeleteMapping
    @PreAuthorize("hasAuthority('user:delete')")
    public void delete(Principal principal) {
        userService.deleteByName(principal.getName());
    }
}
