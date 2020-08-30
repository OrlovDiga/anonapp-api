package anonapp.api.dto;

import java.io.Serializable;

/**
 *  * This class represents a java object that will be deserialized from json.
 *
 * @author Orlov Diga
 */
public class ChatMessageDTO implements Serializable {

    private String id;
    private String text;
    private ChatUserDTO user;
    private String image;
    private String video;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ChatUserDTO getUser() {
        return user;
    }

    public void setUser(ChatUserDTO user) {
        this.user = user;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}
