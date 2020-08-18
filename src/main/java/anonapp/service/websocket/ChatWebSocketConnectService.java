package anonapp.service.websocket;

import anonapp.util.WebSocketSessionStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

/**
 * @author Orlov Diga
 */
@Service
public class ChatWebSocketConnectService {

    private WebSocketSessionStore socketSessionStore;
    private static final Logger LOG = LoggerFactory.getLogger(ChatWebSocketConnectService.class);

    @Autowired
    public ChatWebSocketConnectService(WebSocketSessionStore socketSessionStore) {
        this.socketSessionStore = socketSessionStore;
    }

    //@Async
    public void getConnectPair() throws IOException {
        LOG.info("Now active users in search: " + socketSessionStore.getSearchSessions().size());
        if (socketSessionStore.getSearchSessions().size() > 1) {

            WebSocketSession session1 = socketSessionStore.getFirstSearchSession();
            WebSocketSession session2 = socketSessionStore.getFirstSearchSession();

            LOG.info("Found pair to chat. This:" + session1.getPrincipal().getName() + " and " + session2.getPrincipal().getName());

            socketSessionStore.addWebSocketSessionToChat(session1.getId(), session2);
            socketSessionStore.addWebSocketSessionToChat(session2.getId(), session1);

            session1.sendMessage(new TextMessage("This is your interlocutor: " + session2.getPrincipal().getName()));
            session2.sendMessage(new TextMessage("This is your interlocutor: " + session1.getPrincipal().getName()));
        }
    }
}
