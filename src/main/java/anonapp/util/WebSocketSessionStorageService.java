package anonapp.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class that provides a service for storing sessions of active web socket sessions.
 *
 * @author Orlov Diga
 */
@Component
public class WebSocketSessionStorageService {

    private static final Logger LOG = LoggerFactory.getLogger(WebSocketSessionStorageService.class);
    private Map<String, WebSocketSession> chatSessions = new ConcurrentHashMap<>();
    private Set<WebSocketSession> searchSessions = new LinkedHashSet<>();

    public void addSearchSession(WebSocketSession session) {
        this.searchSessions.add(session);
    }

    /**
     * This method is used when the web socket session is disconnected
     * while (searching for a buddy) while in {@link searchSessions} set.
     * This method provides removal of web socket connections from {@link searchSessions} set.
     *
     * @param session - user web socket session to delete.
     */
    public boolean removeElementFromSearchSessions(WebSocketSession session) {
        return this.searchSessions.remove(session);
    }

    /**
     * This method return last web socket session and remove this contained in the set {@link searchSessions}
     * and removes it from there.
     *
     * @return {@link WebSocketSession} session
     */
    public WebSocketSession getFirstSearchSession() {
        Iterator<WebSocketSession> iterator = this.searchSessions.iterator();
        WebSocketSession session = null;

        if (iterator.hasNext()) {
            session = iterator.next();
            iterator.remove();
        }

        return session;
    }

    /**
     * This method add {@link WebSocketSession} session to {@link chatSessions} map.
     * Key - session id, value - the session itself.
     *
     * @param sessionId - id by {@link WebSocketSession}.
     *
     * @param session - active user session.
     */
    public void addWebSocketSessionToChat(String sessionId, WebSocketSession session) {
        this.chatSessions.put(sessionId, session);
    }


    /**
     * This method is used when the user closes the web socket session.
     * This method provides removal of two connected objects from {@link chatSessions} map
     * when one of the sessions has disconnected.
     * And adding the remaining session to {@link searchSessons} set.
     *
     * @param session user web socket session that disconnected.
     */
    public void disconnectSessionFromChat(WebSocketSession session) {
        WebSocketSession livingSession = chatSessions.get(session.getId());

        chatSessions.remove(session.getId());
        chatSessions.remove(livingSession.getId());

        searchSessions.add(livingSession);
    }

    public Map<String, WebSocketSession> getChatSessions() {
        return chatSessions;
    }

    public Set<WebSocketSession> getSearchSessions() {
        return searchSessions;
    }
}
