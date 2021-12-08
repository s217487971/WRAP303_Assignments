package Networking.messages.client;


import Networking.SubscriberClient;
import Networking.Topics;
import Networking.messages.Message;


/**
 * The message received when a client wishes to leave the
 * current chat group that they are in.
 */
public class Unsubscribe extends Message<SubscriberClient> {
    private static final long serialVersionUID = 9L;

    String topicName;

    public Unsubscribe(String topic)
    {
        this.topicName = topic;
    }


    @Override
    public String toString() {
        return String.format("Unsubscribe('%s')", topicName);
    }

    @Override
    public void apply(SubscriberClient subscriberClient) {
        // Client leaves the group they are currently in.
        //Topics.LeaveTopic(subscriberClient.topicName, subscriberClient);

    }
}
