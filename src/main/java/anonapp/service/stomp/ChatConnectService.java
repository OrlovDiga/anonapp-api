package anonapp.service.stomp;

import anonapp.util.UserConnectSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author Orlov Diga
 */
@Profile("dev")
@Service
public class ChatConnectService {

    private SimpMessagingTemplate webSocket;
    private UserConnectSocketService userConnectSocketService;

    @Autowired
    public ChatConnectService(SimpMessagingTemplate messagingTemplate, UserConnectSocketService userConnectSocketService) {
        this.webSocket = messagingTemplate;
        this.userConnectSocketService = userConnectSocketService;
    }

    @Async
    public void getConnectPair() {
        if (userConnectSocketService.getSearchUsers().size() > 1) {
            String user1 = userConnectSocketService.getSearchUsers().iterator().next();
            String user2 = userConnectSocketService.getSearchUsers().iterator().next();

            userConnectSocketService.addChatUsers(user1, user2);
            userConnectSocketService.addChatUsers(user2, user1);
            userConnectSocketService.removeSearchUser(user1);
            userConnectSocketService.removeSearchUser(user2);

            webSocket.convertAndSendToUser(user1, "/queue/messages", "200");
            webSocket.convertAndSendToUser(user2, "/queue/messages", "200");
        }
    }
}
