package Networking.messages.client;


import Networking.SubscriberClient;
import Networking.Topics;
import Networking.messages.Message;

/**
 * The message received when a client wishes to subscribe a different
 * chat group.
 */
public class Subscribe extends Message<SubscriberClient> {
    private static final long serialVersionUID = 1L;
    public String topicName;

    public Subscribe(String topicName) {

        this.topicName = topicName;
    }

    @Override
    public String toString() {
        return String.format("Subscribe('%s')", topicName);

}
    @Override
    public void apply(SubscriberClient subscriberClient) {
            Topics.subscribe(topicName, subscriberClient);
    }
}
