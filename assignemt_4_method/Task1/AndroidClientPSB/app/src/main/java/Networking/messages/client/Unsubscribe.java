package Networking.messages.client;


import Networking.messages.Message;

/**
 * The message received when a client wishes to leave the
 * current chat group that they are in.
 */
public class Unsubscribe extends Message {
    private static final long serialVersionUID = 9L;

    String topic;

    public Unsubscribe(String topic)
    {
        this.topic = topic;
    }
    @Override
    public String toString() {
        return String.format("Subscribe('%s')", topic);
    }

}
