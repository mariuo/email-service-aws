package org.acme.infra;



import org.acme.adapters.EmailSenderGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.services.ses.SesAsyncClient;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class AmazonEmailSender implements EmailSenderGateway {
    private static final Logger LOG = LoggerFactory.getLogger(AmazonEmailSender.class);
    private final SesAsyncClient ses;
    @Inject
    public AmazonEmailSender(SesAsyncClient sesAsyncClient, SesAsyncClient ses) {
        this.ses = ses;
    }

    @Override
    public String sendEmail(String to, String subject, String body) {
            var ses = SesClient.builder().build();
            var to_dest = Destination.builder().toAddresses(to).build();
            var message = Message.builder()
                    .subject(Content.builder().data(subject).build())
                    .body(Body.builder().text(
                                    Content.builder().data(body).build()
                            ).build()
                    ).build();
            var sendEmailReq = SendEmailRequest.builder()
                    .source("mariocamelogomes@gmail.com")
                    .destination(to_dest)
                    .message(message)
                    .build();
            String sendEmailRespStr = "";
            LOG.info("Enviou: "+ sendEmailReq);
            this.ses.sendEmail(req -> req
                    .source("mariocamelogomes@gmail.com")
                    .destination(d -> d.toAddresses(to))
                    .message(msg -> msg
                            .subject(sub -> sub.data(subject))
                            .body(b -> b.text(txt -> txt.data(body)))));
            return "Enviou";
//        try{
////            return sendEmailRespStr = this.ses.sendEmail(sendEmailReq).toString();
//
//
//        }catch (Exception exception){
//            throw new EmailServiceException("Failure while sending email", exception);
//
//        }
//        return sendEmailRespStr;

    }
}
