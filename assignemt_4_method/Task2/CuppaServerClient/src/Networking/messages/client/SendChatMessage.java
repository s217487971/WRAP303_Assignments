package Networking.messages.client;


import Networking.SubscriberClient;
import Networking.Topics;
import Networking.messages.Message;
import Networking.messages.server.ChatMessageReceived;

/**
 * The message received from a client when they wish to send a text
 * message to all the clients in the same group as them.
 */
public class SendChatMessage extends Message<SubscriberClient> {
    private static final long serialVersionUID = 5L;

    // The text message to be sent to all clients in the same group.
    public String chatMessage;
    public String group;

    public SendChatMessage(String chatMessag, String group) {
        this.chatMessage = chatMessage;
        this.group = group;
    }

    @Override
    public String toString() {
        return String.format("SendChatMessage('%s', '%s')", chatMessage, group);
    }

    @Override
    public void apply(SubscriberClient subscriberClient) {
        // Get group name of client.
        String topicName = subscriberClient.topicName;
        // If not in a group, don't proceed.
        if(topicName.length() == 0) return;
        // Send message to all clients in same group as client.
        Topics.send(group,
                new ChatMessageReceived(group, subscriberClient.handle, chatMessage));
    }
}
