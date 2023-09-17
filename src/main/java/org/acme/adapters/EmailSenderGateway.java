package org.acme.adapters;

public interface EmailSenderGateway {
    String sendEmail(String to, String subject, String body);
}
