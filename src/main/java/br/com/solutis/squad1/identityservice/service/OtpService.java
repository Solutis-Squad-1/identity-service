package br.com.solutis.squad1.identityservice.service;

import br.com.solutis.squad1.identityservice.dto.OtpDto;
import br.com.solutis.squad1.identityservice.dto.user.UserResponseDto;
import br.com.solutis.squad1.identityservice.exception.BadRequestException;
import br.com.solutis.squad1.identityservice.mapper.OtpMapper;
import br.com.solutis.squad1.identityservice.model.entity.Otp;
import br.com.solutis.squad1.identityservice.model.repository.OtpRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OtpService {
    private final OtpRepository otpRepository;
    private final OtpMapper otpMapper;

    public String create(UserResponseDto userResponseDto) {
        log.info("Creating OTP for user {}", userResponseDto);

        otpRepository.deleteAllByUsername(userResponseDto.username());

        String otpCode = generateOtpCode();
        OtpDto otpDto = new OtpDto(userResponseDto, otpCode);

        otpRepository.save(otpMapper.dtoToEntity(otpDto));
        return otpCode;
    }

    private String generateOtpCode() {
        return new DecimalFormat("000000").format(Math.random() * 999999);
    }

    public void verifyOtp(String code, String username) {
        log.info("Verifying OTP {} for user {}", code, username);

        Otp otp = otpRepository.findByCodeAndUsername(code, username);

        if (otp == null) {
            throw new BadRequestException("Invalid OTP");
        }

        if (otp.isExpired()) {
            throw new BadRequestException("OTP expired");
        }

        otp.delete();
    }
}
