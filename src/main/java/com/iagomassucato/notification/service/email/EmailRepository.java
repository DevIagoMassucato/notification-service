package com.iagomassucato.notification.service.email;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EmailRepository extends JpaRepository<EmailEntity, Long> {

    List<EmailEntity> findByEmailAddress(String emailAddress);

}
