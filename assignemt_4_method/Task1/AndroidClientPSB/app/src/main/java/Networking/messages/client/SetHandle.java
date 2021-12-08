package Networking.messages.client;

import Networking.SubscriberClient;
import Networking.messages.Message;


import java.util.Collection;

/**
 * The message received from a client, typically once logged on initially,
 * to set the handle that will be used when communicating.
 */
public class SetHandle extends Message<SubscriberClient> {
    private static final long serialVersionUID = 6L;

    public String handle;

    public SetHandle(String handle) {
        this.handle = handle;
    }

    @Override
    public String toString() {
        return String.format("SetHandle('%s')", handle);
    }


}
