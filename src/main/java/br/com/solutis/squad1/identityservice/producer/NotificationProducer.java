package br.com.solutis.squad1.identityservice.producer;

import br.com.solutis.squad1.identityservice.dto.NotificationOtpMessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationProducer {
    private final RabbitTemplate rabbitTemplate;

    @Value("${api.url}")
    private String apiUrl;

    @Value("${otp.url}")
    private String otpUrl;

    @Value("${spring.rabbitmq.routing-key.notification}")
    private String notificationRoutingKey;

    @Value("${spring.rabbitmq.exchange.notification}")
    private String notificationExchange;

    public void sendOtp(String otp, String email) {
        log.info("Sending OTP: {}", otp);

        String message = getMessage(otp);

        rabbitTemplate.convertAndSend(
                notificationExchange,
                notificationRoutingKey,
                new NotificationOtpMessageDto(message, email)
        );
    }

    private String getMessage(String otp) {
        return "Verify your account. Access the link: " + apiUrl + otpUrl + otp + ". This link will expire in 30 minutes.";
    }
}
