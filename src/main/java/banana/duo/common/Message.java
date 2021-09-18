package banana.duo.common;

import com.google.gson.Gson;

import java.util.Map;

public class Message implements GsonUseable {
    private final ActionType actionType;
    private final Map<String, String> content;

    public Message(ActionType actionType, Map<String, String> content) {
        this.actionType = actionType;
        this.content = content;
    }

    public String toString() {
        return gson.toJson(this);
    }

    public ActionType getMessageType() {
        return actionType;
    }

    public Map<String, String> getContent() {
        return content;
    }

}
