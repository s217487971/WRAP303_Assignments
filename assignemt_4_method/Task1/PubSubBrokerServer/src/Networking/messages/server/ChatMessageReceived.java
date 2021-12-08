package Networking.messages.server;





import Networking.messages.Message;

import java.util.Date;

/**
 * Message sent from server to clients in a particular group in response
 * to a client in the group sending a SendChatMessage.
 */
public class ChatMessageReceived extends Message {
    private static final long serialVersionUID = 100L;

    public Date  timeStamp;
    public String topicName;
    public String handle;
    public String chatMessage;

    public ChatMessageReceived(String topicName, String handle, String chatMessage) {
        this.topicName = topicName;
        this.handle = handle;
        this.chatMessage = chatMessage;
        timeStamp = new Date();
    }

    @Override
    public String toString() {
        return String.format("ChatMessageReceived(%s, '%s', '%s', '%s')",
                timeStamp, topicName, handle, chatMessage);
    }
}
