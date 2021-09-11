package banana.duo.common;

public class Message {
    private ActionType actionType;
    private String content;

    private Message(ActionType actionType, String content) {
        this.actionType = actionType;
        this.content = content;
    }

    public static Message parseString(String string) {
        String stringMessageType = string.substring(string.indexOf("<") + 1, string.indexOf(">"));
        String stringContent = string.substring(string.indexOf(">") + 1);
        return new Message(ActionType.valueOf(stringMessageType), stringContent);
    }

    public static ActionType parseMessageType(String string) {
        String stringMessageType = string.substring(string.indexOf("<") + 1);
        stringMessageType = string.substring(0, string.indexOf(">"));
        return ActionType.valueOf(stringMessageType);
    }


    public String toString() {
        return "<" + actionType.toString() + ">" + content;
    }


    public ActionType getMessageType() {
        return actionType;
    }

    public String getContent() {
        return content;
    }

}
