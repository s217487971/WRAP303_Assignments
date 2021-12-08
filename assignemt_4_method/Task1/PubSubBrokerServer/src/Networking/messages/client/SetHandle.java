package Networking.messages.client;




import Networking.SubscriberClient;
import Networking.Topics;
import Networking.messages.Message;
import Networking.messages.server.HandleSet;

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

    @Override
    public void apply(SubscriberClient subscriberClient) {
        // Check if the handle is already being used. If is, append client number.
        long count = Topics.topics.values()
                .stream()
                .flatMap(Collection::stream)
                .distinct()
                .filter(client -> client.handle.equalsIgnoreCase(handle))
                .count();
        if(count > 0) handle = String.format("%s#%d", handle, subscriberClient.clientNum);

        // Set the handle.
        subscriberClient.handle = handle;

        // Tell the client about the handle that was decided upon.
        subscriberClient.send(new HandleSet(handle));
    }
}
