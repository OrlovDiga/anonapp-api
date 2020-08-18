package anonapp.util;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Orlov Diga
 */
@Component
public class UserConnectSocketService {

    private Map<String, String> chatUsers = new ConcurrentHashMap<>();
    private Set<String> searchUsers = new HashSet<>();

    public void addChatUsers(String sender, String receiver) {
        this.chatUsers.put(sender, receiver);
    }

    public void addSearchUser(String user) {
        this.searchUsers.add(user);
    }

    public void removeSearchUser(String user) {
        this.searchUsers.remove(user);
    }

    public void removeChatUsers(String sender) {
        this.chatUsers.remove(sender);
    }

    public Map<String, String> getChatUsers() {
        return chatUsers;
    }

    public Set<String> getSearchUsers() {
        return searchUsers;
    }
}
