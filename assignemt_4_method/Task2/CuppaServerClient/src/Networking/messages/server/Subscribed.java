package Networking.messages.server;


import Networking.messages.Message;

/**
 * Message sent to all clients in a group when a new client subscribes
 * a group.
 */
public class Subscribed extends Message {
    private static final long serialVersionUID = 102L;

    public String topicName;
    public String handle;

    public Subscribed(String topicName, String handle) {
        this.topicName = topicName;
        this.handle = handle;
    }

    @Override
    public String toString() {
        return String.format("Subscribed(%s, %s)", topicName, handle);
    }
}
