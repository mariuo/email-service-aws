package org.acme.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import javax.inject.Inject;
import org.acme.application.EmailSenderService;
import org.acme.core.exceptions.EmailServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * The type Email sender controller.
 */
@Path("/api/email")
public class EmailSenderController {
    private static final Logger LOG = LoggerFactory.getLogger(EmailSenderController.class);

    private final EmailSenderService emailSenderService;

    /**
     * Instantiates a new Email sender controller.
     *
     * @param emailSenderService the email sender service
     */
    @Inject
    public EmailSenderController(EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }

    /**
     * Send email response.
     *
     * @param jsonNode the json node
     * @return the response
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response sendEmail(JsonNode jsonNode){
        if (jsonNode == null) throw new IllegalArgumentException();
        var to = jsonNode.get("to").textValue();
        var subject = jsonNode.get("subject").textValue();
        var body = jsonNode.get("body").textValue();

        if (! isValid(to, subject, body))
            throw new IllegalArgumentException();

        try{
            this.emailSenderService.sendEmail(to, subject, body);
            return Response.ok("Email sent successfuly").build();

        }catch (EmailServiceException e){
            return Response.serverError().build();
        }
    }


    /**
     * Method to check if the email is valid.
     *
     * @param to to
     * @param subject subject
     * @param body body
     * @return {@link boolean}
     */
    private boolean isValid(String to, String subject, String body) {
        System.out.println("-------");
        System.out.println(to);
        System.out.println(subject);
        System.out.println(body);
        System.out.println("-------");
        return to != null
                && subject != null
                && body != null;
    }

}
