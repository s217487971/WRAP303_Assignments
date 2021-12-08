package Networking.messages.client;

import Networking.messages.Message;

/**
 * The message received when a client wishes to create a new
 * chat group.
 */
public class AddTopic extends Message {
    private static final long serialVersionUID = 7L;

    // The name of the group to create.
    public String topicName;

    public AddTopic(String topicName) {
        this.topicName = topicName;
    }

    @Override
    public String toString() {
        return String.format("AddTopic('%s')", topicName);
    }

}
