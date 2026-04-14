package com.iagomassucato.notification.service.email;

import java.time.OffsetDateTime;

public record EmailResponse(
        Long id,
        StatusEnum status,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
    public static EmailResponse fromEntity(EmailEntity emailEntity){
        return new EmailResponse(
                emailEntity.getId(),
                emailEntity.getStatusEnum(),
                emailEntity.getCreatedAt(),
                emailEntity.getUpdatedAt()
        );
    }
}
