package Networking.messages.client;

import Networking.SubscriberClient;
import Networking.messages.Message;

/**
 * The message received when a client wishes to know the names
 * of the topics that currently exist.
 */
public class ListedTopics extends Message<SubscriberClient> {
    private static final long serialVersionUID = 3L;

    @Override
    public String toString() {
        return "ListTopics()";
    }
}
