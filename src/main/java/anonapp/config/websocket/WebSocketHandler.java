package anonapp.config.websocket;

import anonapp.api.dto.MessageType;
import anonapp.api.dto.SocketMessageDTO;
import anonapp.util.WebSocketSessionStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.IOException;

/**
 * This class provides basic web socket connection handler.
 *
 * @author Orlov Diga
 */
@Component
public class WebSocketHandler extends AbstractWebSocketHandler {

    private static final Logger LOG = LoggerFactory.getLogger(WebSocketHandler.class);

    @Autowired
    private WebSocketSessionStorageService webSocketStore;

    /**
     * This method is used to process the message from {@link WebSocketSession} the web socket session.
     *
     * @param session from which the message came.
     * @param message - payload that came from the session.
     */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        WebSocketSession receiver = webSocketStore.getChatSessions().get(session.getId());
        SocketMessageDTO msg = SocketMessageDTO.fromJson(message.getPayload().toString());

        if (msg.getType() == MessageType.NEXT) {
            webSocketStore.getChatSessions().remove(session.getId());
            webSocketStore.getChatSessions().remove(receiver.getId());
            webSocketStore.addSearchSession(receiver);
            webSocketStore.addSearchSession(session);
        }
        receiver.sendMessage(new TextMessage(msg.toJson()));

        LOG.info("New message received: " + message.getPayload()
                + "From: " + session.getPrincipal().getName()
                + ". To: " + receiver.getPrincipal().getName());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        WebSocketSession receiver = webSocketStore.getChatSessions().get(session.getId());

        LOG.info("New text message received: " + message.getPayload()
                + "From: " + session.getPrincipal().getName()
                + ". To: " + receiver.getPrincipal().getName());
        receiver.sendMessage(new TextMessage("got this for you: "+message.getPayload()));
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws IOException {
        System.out.println("New Binary Message Received");
        session.sendMessage(message);
    }


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        LOG.info("Received a new web socket connection. Username: {}", session.getPrincipal().getName());
        webSocketStore.addSearchSession(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        if (webSocketStore.getChatSessions().containsKey(session.getId())) {
            LOG.info("Disconnect session from chat. Username: " + session.getPrincipal().getName());
            webSocketStore.disconnectSessionFromChat(session);
        } else {
            LOG.info("Disconnect session from search. Username: " + session.getPrincipal().getName());
            webSocketStore.removeElementFromSearchSessions(session);
        }
    }
}