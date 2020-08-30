package anonapp.api.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * This class represents a java object that will be deserialized from json.
 *
 * @author Orlov Diga
 */
public class SocketMessageDTO {

    private MessageType type;
    private ChatMessageDTO chatMessage;

    public static SocketMessageDTO fromJson(String socketMsg) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(socketMsg, SocketMessageDTO.class);
    }

    public String toJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public ChatMessageDTO getChatMessage() {
        return chatMessage;
    }

    public void setChatMessage(ChatMessageDTO chatMessage) {
        this.chatMessage = chatMessage;
    }
}
