package br.com.solutis.squad1.identityservice.mapper;

import br.com.solutis.squad1.identityservice.dto.user.UserLoginDto;
import br.com.solutis.squad1.identityservice.dto.user.UserRegisterDto;
import br.com.solutis.squad1.identityservice.dto.user.UserResponseDto;
import br.com.solutis.squad1.identityservice.model.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDto toResponseDto(User user);

    @Mapping(target = "id", ignore = true)
    User responseDtoToEntity(UserResponseDto userResponseDto);

    @Mapping(target = "id", ignore = true)
    User registerDtoToEntity(UserRegisterDto userRegisterDto);

    @Mapping(target = "id", ignore = true)
    User loginDtoToEntity(UserLoginDto userLoginDto);
}