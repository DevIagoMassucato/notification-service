package com.iagomassucato.notification.service.email;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/emails")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping
    public ResponseEntity< EmailResponse> sendEmail(@RequestBody @Valid EmailRequest emailRequest) {
        EmailResponse emailResponse = emailService.sendEmail(emailRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(emailResponse);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<EmailResponse> findById(@PathVariable long id){
        return ResponseEntity.ok(emailService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<EmailResponse>> find(@RequestParam(required = false) String email) {
        if (email != null) {
            return ResponseEntity.ok(emailService.findByEmail(email));
        }
        return ResponseEntity.ok(emailService.listAll());
    }
}
