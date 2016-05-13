public class Message {

    // TODO: create a property "text" to hold the text of a message
    private String messageText;

    //gives the message an ID
    public int messageId = 1;

    // TODO: create a constructor for Message that accepts one argument, the text of the message
    public Message(String messageText, int messageId) {

        this.messageText = messageText;
        this.messageId = messageId;
    }

    // TODO: create a getText method
    public String getMessageText() {

        return messageText;
    }

    // TODO: create a setText method
    public void setMessageText(String messageText) {

        this.messageText = messageText;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }
}
