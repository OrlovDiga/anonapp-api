package anonapp.email.sender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * The class provides a service for sending messages to mail.
 *
 * @author Orlov Diga
 */
@Service("emailService")
public class EmailServiceImpl implements EmailService  {

    @Value("${spring.mail.username}")
    private String noreplyAddress;

    private final JavaMailSender sender;
    //private final SimpleMailMessage template;

    @Autowired
    public EmailServiceImpl(JavaMailSender sender) {
        this.sender = sender;
        //this.template = template;
    }


    /**
     * This method sends a simple text message to mail.
     *
     * @param to - email address to whom the message will be sent.
     * @param subject - the main idea of ​​the message.
     * @param text - message payload.
     */
    @Override
    public void sendSimpleMessage(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(noreplyAddress);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            sender.send(message);
        } catch (MailException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void simpleMessageUsingTemplate(String to, String subject, String... templateModel) {
        /*String text = String.format(template.getText(), templateModel);
        sendSimpleMessage(to, subject, text);*/
    }
}
