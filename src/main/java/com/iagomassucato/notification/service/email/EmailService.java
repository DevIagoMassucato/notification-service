package com.iagomassucato.notification.service.email;

import com.iagomassucato.exception.core.ApiException;
import com.iagomassucato.exception.core.ErrorEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final EmailRepository emailRepository;
    private final EmailSender emailSender;

    public EmailResponse sendEmail(EmailRequest emailRequest) {
        EmailEntity emailEntity = createAndSave(emailRequest);
        sendAndUpdateStatus(emailEntity);
        return EmailResponse.fromEntity(emailEntity);
    }

    private EmailEntity createAndSave(EmailRequest emailRequest){
        EmailEntity emailEntity = EmailEntity.builder()
                .emailAddress(emailRequest.emailAddress())
                .subject(emailRequest.subject())
                .body(emailRequest.body())
                .build();
        return emailRepository.save(emailEntity);
    }

    private void sendAndUpdateStatus(EmailEntity emailEntity) {
        try {
            emailSender.send(
                    emailEntity.getEmailAddress(),
                    emailEntity.getSubject(),
                    emailEntity.getBody()
            );

            emailEntity.updateStatus(StatusEnum.SENT);
        } catch (Exception exception) {
            emailEntity.updateStatus(StatusEnum.ERROR);
            log.error("Error sending email", exception);
        }
        emailRepository.save(emailEntity);
    }

    public List<EmailResponse> listAll() {
        List<EmailEntity> emailEntityList = emailRepository.findAll();
        return emailEntityList.stream()
                .map(EmailResponse::fromEntity)
                .toList();
    }

    public EmailResponse findById(long id) {
        EmailEntity emailEntity = emailRepository.findById(id)
                .orElseThrow(() -> new ApiException(
                        ErrorEnum.NOT_FOUND,
                        Map.of(
                                "id", id,
                                "message", "notification with id " + id + " not found"
                        )
                ));
        return EmailResponse.fromEntity(emailEntity);
    }

    public List<EmailResponse> findByEmail(String emailAddress) {
        List<EmailEntity> emailEntityList = emailRepository.findByEmailAddress(emailAddress);
        return emailEntityList.stream()
                .map(EmailResponse::fromEntity)
                .toList();
    }
}