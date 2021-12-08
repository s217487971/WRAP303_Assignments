package Networking.messages.client;


import Networking.SubscriberClient;
import Networking.Topics;
import Networking.messages.Message;

/**
 * The message received from a client when they no longer wish
 * to communicate with the server, i.e. log out.
 */
public class Quit extends Message<SubscriberClient> {
    private static final long serialVersionUID = 4L;

    @Override
    public String toString() {
        return "Quit()";
    }

    @Override
    public void apply(SubscriberClient subscriberClient) {
        Topics.leave(subscriberClient);
    }
}
