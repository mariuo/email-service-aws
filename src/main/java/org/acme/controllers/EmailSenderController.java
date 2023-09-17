package org.acme.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import javax.inject.Inject;
import org.acme.application.EmailSenderService;
import org.acme.core.MyResponse;
import org.acme.core.exceptions.EmailServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("/api/email")
public class EmailSenderController {
    private static final Logger LOG = LoggerFactory.getLogger(EmailSenderController.class);

    private final EmailSenderService emailSenderService;
    @Inject
    public EmailSenderController(EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }


    @GET
    @Produces(TEXT_PLAIN)
    public String hello() {
        return "Hello from RESTEasy Reactive";
    }
    @POST
    @Path("/msg")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response exemplo(MyResponse request) {
        // Aqui você pode manipular o objeto JSON recebido na solicitação
        String messageFromRequest = request.getMessage();

        // Crie uma instância do objeto de resposta
        MyResponse response = new MyResponse("Resposta: " + messageFromRequest);

        // Retorne a resposta com um status 200
        return Response.ok(response).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response sendEmail(JsonNode jsonNode){
        LOG.info("Passou!");
        if (jsonNode == null) throw new IllegalArgumentException();
        var to = jsonNode.get("to").textValue();
        var subject = jsonNode.get("subject").textValue();
        var body = jsonNode.get("body").textValue();

        if (! isValid(to, subject, body))
            throw new IllegalArgumentException();

        try{
            this.emailSenderService.sendEmail(to, subject, body);
//            return RestResponse.ok("Email sent successfuly");
            return Response.ok("Email sent successfuly").build();

        }catch (EmailServiceException e){
            return Response.serverError().build();
        }
    }

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
