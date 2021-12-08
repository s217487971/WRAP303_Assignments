package Networking.messages.server;



import java.util.ArrayList;
import java.util.List;

import Networking.messages.Message;

/**
 * Message sent to a client listing all the topics that the server
 * has defined.
 */
public class TopicsListed extends Message {
    private static final long serialVersionUID = 101L;

    public List<String> topicNames;


    @Override
    public String toString() {
        return String.format("TopicsListed(%s)", topicNames.toString());
    }
}
