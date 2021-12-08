package Networking.messages.client;


import Networking.SubscriberClient;
import Networking.messages.Message;

/**
 * The message received when a client wishes to know the names
 * of the customers that currently exist.
 */
public class ListedTopics extends Message<SubscriberClient> {
    private static final long serialVersionUID = 3L;

    @Override
    public String toString() {
        return "ListTopics()";
    }

    @Override
    public void apply(SubscriberClient subscriberClient) {
        // Return the list of group names to the client.
        subscriberClient.send(new ListedTopics());
    }
}
