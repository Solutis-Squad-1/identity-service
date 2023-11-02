package br.com.solutis.squad1.identityservice.mapper;

import br.com.solutis.squad1.identityservice.dto.user.*;
import br.com.solutis.squad1.identityservice.model.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDto toResponseDto(User user);

    List<UserResponseDto> toResponseDto(List<User> users);

    UserResponseDetailedDto toResponseDetailedDto(User user);

    @Mapping(target = "id", ignore = true)
    User responseDtoToEntity(UserResponseDto userResponseDto);

    @Mapping(target = "id", ignore = true)
    User registerDtoToEntity(UserRegisterDto userRegisterDto);

    @Mapping(target = "id", ignore = true)
    User loginDtoToEntity(UserLoginDto userLoginDto);

    User putDtoToEntity(UserPutDto userPutDto);
}