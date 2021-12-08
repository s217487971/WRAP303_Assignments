package Networking.messages.server;


import Networking.messages.Message;

/**
 * Message sent to all clients in a group when a client leaves the
 * group.
 */
public class Unsubscribed extends Message {
    private static final long serialVersionUID = 103L;

    public String topicName;
    public String handle;

    public Unsubscribed(String topicName, String handle) {
        this.topicName = topicName;
        this.handle = handle;
    }

    @Override
    public String toString() {
        return String.format("Unsubscribed('%s', '%s')", topicName, handle);
    }
}
