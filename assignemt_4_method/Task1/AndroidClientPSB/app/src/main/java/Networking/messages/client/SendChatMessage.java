package Networking.messages.client;


import Networking.SubscriberClient;
import Networking.messages.Message;


/**
 * The message received from a client when they wish to send a text
 * message to all the clients in the same group as them.
 */
public class SendChatMessage extends Message<SubscriberClient> {
    private static final long serialVersionUID = 5L;

    // The text message to be sent to all clients in the same group.
    public String chatMessage;
    public String group;

    public SendChatMessage(String chatMessage, String group) {

        this.chatMessage = chatMessage;
        this.group = group;
    }

    @Override
    public String toString() {
        return String.format("SendChatMessage('%s', '%s')", chatMessage,group);
    }

}
