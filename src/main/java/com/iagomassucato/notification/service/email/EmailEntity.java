package com.iagomassucato.notification.service.email;

import com.iagomassucato.notification.service.shared.AbstractEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
@Table(name = "emails")
@NoArgsConstructor
@Getter
public class EmailEntity extends AbstractEntity {

    @Column(nullable = false)
    private String emailAddress;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false)
    private String body;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusEnum statusEnum;

    @Column(nullable = false)
    private OffsetDateTime createdAt;

    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    @Builder
    public EmailEntity(
            String emailAddress,
            String subject,
            String body,
            StatusEnum status
    ){
        this.emailAddress = Objects.requireNonNull(emailAddress);
        this.subject = Objects.requireNonNull(subject);
        this.body = Objects.requireNonNull(body);
        this.statusEnum = Objects.requireNonNullElse(status, StatusEnum.PENDING);
        this.createdAt = OffsetDateTime.now();
        this.updatedAt = this.createdAt;
    }

    public void updateStatus(StatusEnum statusEnum) {
        this.statusEnum = Objects.requireNonNull(statusEnum);
        this.updatedAt = OffsetDateTime.now();
    }
}
