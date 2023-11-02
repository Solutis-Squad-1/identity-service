package br.com.solutis.squad1.identityservice.mapper;

import br.com.solutis.squad1.identityservice.dto.OtpDto;
import br.com.solutis.squad1.identityservice.model.entity.Otp;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OtpMapper {
    Otp dtoToEntity(OtpDto otpDto);
}