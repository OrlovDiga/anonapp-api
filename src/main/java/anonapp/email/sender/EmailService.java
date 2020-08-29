package anonapp.email.sender;

/**
 * @author Orlov Diga
 */
public interface EmailService {

    void sendSimpleMessage(String to, String subject, String text);

    void simpleMessageUsingTemplate(String to, String subject, String ...templateModel);

}
