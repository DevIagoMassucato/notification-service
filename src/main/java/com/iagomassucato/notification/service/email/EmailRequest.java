package com.iagomassucato.notification.service.email;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record EmailRequest(
        @Email(message = "invalid email")
        @NotBlank(message = "emailAddress is required")
        String emailAddress,
        @NotBlank(message = "subject is required")
        String subject,
        @NotBlank(message = "body is required")
        String body){
}
