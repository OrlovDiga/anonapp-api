package anonapp.api.dto;

import java.util.Date;

/**
 * @author Orlov Diga
 */
public class OutputMessage {

    private String from;
    private String message;
    private boolean myMsg;

    public OutputMessage(String from, String message, boolean myMsg) {
        this.from = from;
        this.message = message;
        this.myMsg = myMsg;
    }

    public OutputMessage() {
    }

    public String getFrom() {
        return from;
    }

    public String getMessage() {
        return message;
    }

    public boolean isMyMsg() {
        return myMsg;
    }
}
