package anonapp.api.controller;

import anonapp.api.dto.ChatMessage;
import anonapp.api.dto.OutputMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

/**
 * @author Orlov Diga
 */
@Profile("dev")
@Controller
@RequestMapping(value = "/api")
public class ChatController {

    private static final Logger LOG = LoggerFactory.getLogger(ChatController.class);
    private SimpMessagingTemplate webSocket;

    @Autowired
    public ChatController(SimpMessagingTemplate webSocket) {
        this.webSocket = webSocket;
    }

    @MessageMapping("/chat")
    public void sendMessage(Message<ChatMessage> message, @Payload ChatMessage chatMessage) {
        Principal principal = message.getHeaders().get(SimpMessageHeaderAccessor.USER_HEADER, Principal.class);

        if (principal == null) {
            LOG.error("Principal is null.");
            return;
        }

        String authenticatedSender = principal.getName();

        if (!authenticatedSender.equals(chatMessage.getRecipient())) {
            webSocket.convertAndSendToUser(chatMessage.getFrom(),
                    "/queue/messages",
                    new OutputMessage(chatMessage.getFrom(), chatMessage.getText(), true));
        }

        webSocket.convertAndSendToUser(chatMessage.getRecipient(),
                "/queue/messages",
                new OutputMessage(chatMessage.getFrom(), chatMessage.getText(), false));
    }

  /*  @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage message, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", message.getSender());
        return message;
    }

    @MessageMapping("/chat/{topic}")
    @SendTo("/topic/messages")
    public OutputMessage send(@DestinationVariable("topic") String topic, Message message) {
        return new OutputMessage(message.getFrom(), message.getText(), topic);
    }*/

}
