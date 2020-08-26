package anonapp.data.service.impl;

import anonapp.api.dto.MessageType;
import anonapp.api.dto.SocketMessage;
import anonapp.util.WebSocketSessionStorage;
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

    private WebSocketSessionStorage socketSessionStore;
    private static final Logger LOG = LoggerFactory.getLogger(ChatWebSocketConnectService.class);

    @Autowired
    public ChatWebSocketConnectService(WebSocketSessionStorage socketSessionStore) {
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

            SocketMessage msg = new SocketMessage();
            msg.setType(MessageType.CONNECT);
            session1.sendMessage(new TextMessage(msg.toJson()));
            session2.sendMessage(new TextMessage(msg.toJson()));
        }
    }
}
