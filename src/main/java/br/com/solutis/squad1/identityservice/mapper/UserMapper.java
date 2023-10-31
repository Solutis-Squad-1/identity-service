package br.com.solutis.squad1.identityservice.mapper;

import br.com.solutis.squad1.identityservice.dto.user.UserLoginDto;
import br.com.solutis.squad1.identityservice.dto.user.UserPutDto;
import br.com.solutis.squad1.identityservice.dto.user.UserRegisterDto;
import br.com.solutis.squad1.identityservice.dto.user.UserResponseDto;
import br.com.solutis.squad1.identityservice.model.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDto toResponseDto(User user);

    List<UserResponseDto> toResponseDto(List<User> users);

    @Mapping(target = "id", ignore = true)
    User responseDtoToEntity(UserResponseDto userResponseDto);

    @Mapping(target = "id", ignore = true)
    User registerDtoToEntity(UserRegisterDto userRegisterDto);

    @Mapping(target = "id", ignore = true)
    User loginDtoToEntity(UserLoginDto userLoginDto);

    @Mapping(target = "id", ignore = true)
    User putDtoToEntity(UserPutDto userPutDto);
}