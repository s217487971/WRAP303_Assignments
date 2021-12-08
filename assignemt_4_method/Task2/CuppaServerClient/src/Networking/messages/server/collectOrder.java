package Networking.messages.server;

import Networking.SubscriberClient;
import Networking.messages.Message;


public class collectOrder extends Message {
    private static final long serialVersionUID = 750L;


    public String orderId;
    public String handle;
    public String Invoice;

    public collectOrder(String orderId, String Invoice, String handle) {
        this.Invoice = Invoice;
        this.handle = handle;
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return String.format("CollectOrder(%s, '%s', '%s')",
                 orderId, handle, Invoice);
    }

}
