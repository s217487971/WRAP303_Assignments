package Networking.messages.client;

import Networking.SubscriberClient;
import Networking.Topics;
import Networking.messages.Message;
import Networking.messages.server.ChatMessageReceived;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.List;

public class SendOrder extends Message<SubscriberClient> {
    private static final long serialVersionUID = 78L;

    int[] quantities;
    String telephone;
    String handle;
    String date;
    String time;

    public SendOrder( int[] products, String telephone, String handle, String date, String time)
    {
        this.date =date;
        this.time = time;
        this.telephone = telephone;
        this.handle = handle;
        this.quantities = products;
    }
    @Override
    public String toString() {

        return String.format("SendChatMessage(Send Order)");
    }

    @Override
    public void apply(SubscriberClient subscriberClient) {
        // Get group name of client.
        String topicName = subscriberClient.handle;
        // If not in a group, don't proceed.
        if(topicName.length() == 0) return;
        Topics.addOrder(subscriberClient,quantities, date, time, telephone);
        // Send message to all clients in same group as client.

    }
}
