package anonapp.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author Orlov Diga
 */
@Component
public class WebSocketSessionStore {

    private static final Logger LOG = LoggerFactory.getLogger(WebSocketSessionStore.class);
    private Map<String, WebSocketSession> chatSessions = new ConcurrentHashMap<>();
    private Set<WebSocketSession> searchSessions = new LinkedHashSet<>();

    public void addSearchSession(WebSocketSession session) {
        this.searchSessions.add(session);
    }

    public boolean removeElementFromSearchSessions(WebSocketSession session) {
        return this.searchSessions.remove(session);
    }

    public WebSocketSession getFirstSearchSession() {
        Iterator<WebSocketSession> iterator = this.searchSessions.iterator();
        WebSocketSession session = null;

        if (iterator.hasNext()) {
            session = iterator.next();
            iterator.remove();
        }

        return session;
    }

    public void addWebSocketSessionToChat(String sessionId, WebSocketSession session) {
        this.chatSessions.put(sessionId, session);
    }

    public void disconnectSessionFromChat(WebSocketSession session) {
        WebSocketSession livingSession = chatSessions.get(session.getId());

        chatSessions.remove(session.getId());
        chatSessions.remove(livingSession.getId());

        searchSessions.add(session);
    }

    public void removeChatSession(String sessionId) {
        this.chatSessions.remove(sessionId);
    }

    public Map<String, WebSocketSession> getChatSessions() {
        return chatSessions;
    }

    public Set<WebSocketSession> getSearchSessions() {
        return searchSessions;
    }
}
