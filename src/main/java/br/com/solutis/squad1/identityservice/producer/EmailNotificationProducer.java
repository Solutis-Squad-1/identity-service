package br.com.solutis.squad1.identityservice.producer;

import br.com.solutis.squad1.identityservice.dto.EmailDto;
import br.com.solutis.squad1.identityservice.dto.user.UserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailNotificationProducer {
    private final RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.routing-key.notification}")
    private String notificationRoutingKey;

    @Value("${spring.rabbitmq.exchange.notification}")
    private String notificationExchange;

    @Value("${api.email}")
    private String apiEmail;

    public void sendOtp(UserResponseDto userResponseDto, String otp) {
        log.info("Sending OTP: {} to {}", otp, userResponseDto.email());

        String subject = getSubject();
        String message = getMessage(otp);

        rabbitTemplate.convertAndSend(
                notificationExchange,
                notificationRoutingKey,
                new EmailDto(
                        userResponseDto.username(),
                        apiEmail,
                        userResponseDto.email(),
                        subject,
                        message
                )
        );
    }

    private String getSubject() {
        return "Verify your account!";
    }

    private String getMessage(String otp) {
        return "Verify your account. Your otp code is:" + otp + ". This code will expire in 30 minutes.";
    }
}
