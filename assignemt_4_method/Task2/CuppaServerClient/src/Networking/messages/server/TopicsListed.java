package Networking.messages.server;




import Networking.Topics;
import Networking.messages.Message;
import Networking.messages.product;

import java.util.ArrayList;
import java.util.List;

/**
 * Message sent to a client listing all the customers that the server
 * has defined.
 */
public class TopicsListed extends Message {
    private static final long serialVersionUID = 101L;

    public List<product> topicNames;

    public TopicsListed() {
        topicNames = new ArrayList<>(Topics.products.values());
    }

    @Override
    public String toString() {
        return String.format("TopicsListed(%s)", topicNames.toString());
    }
}
