package anonapp.config.stomp;

import anonapp.util.UserConnectSocketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

/**
 * @author Orlov Diga
 */
@Profile("dev")
@Component
public class WebSocketEventListener {

    private static final Logger LOG = LoggerFactory.getLogger(WebSocketEventListener.class);

    private SimpMessageSendingOperations messageSendingOperations;
    private UserConnectSocketService userService;

    @Autowired
    public WebSocketEventListener(SimpMessageSendingOperations messageSendingOperations,
                                  UserConnectSocketService userConnectSocketService) {
        this.messageSendingOperations = messageSendingOperations;
        this.userService = userConnectSocketService;
    }

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        userService.addSearchUser(event.getUser().getName());
        LOG.info("Received a new web socket connection. Username: {}", event.getUser().getName());
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if(username != null) {
            LOG.info("User Disconnected : " + username);

        }
    }
}
