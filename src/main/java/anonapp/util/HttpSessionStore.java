package anonapp.util;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Orlov Diga
 */
/*@Profile("dev")
@Component*/
public class HttpSessionStore {

    private Map<String, HttpSession> chatSessions = new ConcurrentHashMap<>();
    private Set<HttpSession> searchSessions = new HashSet<>();

    public void addWebSocketSession(String sessionId, HttpSession session) {
        this.chatSessions.put(sessionId, session);
    }

    public void addSearchSession(HttpSession session) {
        this.searchSessions.add(session);
    }

    public void removeSearchSession(HttpSession session) {
        this.searchSessions.remove(session);
    }

    public void removeChatSession(String sessionId) {
        this.chatSessions.remove(sessionId);
    }

    public Map<String, HttpSession> getChatSessions() {
        return chatSessions;
    }

    public void setChatSessions(Map<String, HttpSession> chatSessions) {
        this.chatSessions = chatSessions;
    }

    public Set<HttpSession> getSearchSessions() {
        return searchSessions;
    }

    public void setSearchSessions(Set<HttpSession> searchSessions) {
        this.searchSessions = searchSessions;
    }
}
