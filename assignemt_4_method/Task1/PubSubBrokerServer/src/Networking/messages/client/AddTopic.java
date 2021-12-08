package Networking.messages.client;

import Networking.SubscriberClient;
import Networking.Topics;
import Networking.messages.Message;
import Networking.messages.server.TopicsListed;

/**
 * The message received when a client wishes to create a new
 * chat group.
 */
public class AddTopic extends Message<SubscriberClient> {
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

    @Override
    public void apply(SubscriberClient subscribertClient) {
        // Add new chat group.
        Topics.addTopic(topicName);
        // Return the list of group names to all clients.
        Topics.sendAll(new TopicsListed());
        subscribertClient.send(new TopicsListed());
    }
}
