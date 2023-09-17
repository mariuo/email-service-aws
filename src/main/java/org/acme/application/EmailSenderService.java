package org.acme.application;


import org.acme.adapters.EmailSenderGateway;
import org.acme.core.EmailSenderUseCase;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class EmailSenderService implements EmailSenderUseCase {

    private final EmailSenderGateway emailSenderGateway;

    @Inject
    public EmailSenderService(EmailSenderGateway emailSenderGateway){
        this.emailSenderGateway = emailSenderGateway;
    }

    @Override
    public void sendEmail(String to, String subject, String body) {
            this.emailSenderGateway.sendEmail(to, subject, body);
    }
}
