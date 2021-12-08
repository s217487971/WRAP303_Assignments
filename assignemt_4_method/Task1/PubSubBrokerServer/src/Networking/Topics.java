package Networking;



import Networking.messages.Message;
import Networking.messages.server.ChatMessageReceived;
import Networking.messages.server.Subscribed;
import Networking.messages.server.Unsubscribed;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * This class is responsible for managing chat topics and sending messages to
 * clients in specific topics.
 */
public class Topics {


    // Lock to prevent multiple threads manipulating the topics data
    // structure while busy with an operation.
    private static final ReentrantLock lock = new ReentrantLock();

    // [Group Name] -> {clients}
    public static final Map<String, Set<SubscriberClient>> topics = new HashMap<>();

    /**
     * Subscribe a new group. Before subscribeing a new group, the client will leave
     * any topics it is currently in. All other clients in the group are
     * notified of the client subscribeing.
     * @param topicName The new group's name.
     * @param client The client subscribeing.
     */
    public static void subscribe(String topicName, SubscriberClient client) {
        // If already in a group, leave it.
        //leave(client);

        // If no such group, create it.
        if(!topics.containsKey(topicName))
            addTopic(topicName);

        // Now subscribe the new group.
        lock.lock();
            // Add client to group.
            topics.get(topicName).add(client);
            client.topicName = topicName;

            // Tell all clients that client subscribed group.
            topics.get(topicName)
                    .forEach(subscriberClient -> subscriberClient.send(new Subscribed(topicName, client.handle)));
        lock.unlock();
    }

    /**
     * The client leaves all topics currently a member of. All other clients
     * are sent a notification of this fact.
     * @param client The client leaving.
     */
    public static void leave(SubscriberClient client) {
        lock.lock();
            // Get topics to which client belongs.
            List<String> topicsIn = topics.entrySet()
                    .stream()
                    .filter(entry -> entry.getValue().contains(client))
                    .map(entry -> entry.getKey())
                    .collect(Collectors.toList());

            // Remove client from these topics and notify other clients of this.
            topicsIn.forEach(topicName -> {
                // Get group.
                Set<SubscriberClient> group = topics.get(topicName);

                // Remove client from group.
                group.remove(client);
                client.topicName = "";

                // Send message to other clients in group.
                Unsubscribed msg = new Unsubscribed(topicName, client.handle);
                group.forEach(subscriberClient -> subscriberClient.send(msg));
            });
        lock.unlock();
    }

    public static void LeaveTopic(String topicName, SubscriberClient client)
    {
        lock.lock();
        if(topics.containsKey(topicName)) {
            Set<SubscriberClient> group = topics.get(topicName);
            group.remove(client);
            //CheckIfAnyOtherGroupHasClient
            // Send message to other clients in group.
            Unsubscribed msg = new Unsubscribed(topicName, client.handle);
            group.forEach(subscriberClient -> subscriberClient.send(msg));
        }
        lock.unlock();
    }

    /**
     * A new (empty) group is added.
     * @param topicName The new group's name.
     */
    public static void addTopic(String topicName) {
        lock.lock();
            topics.put(topicName, new HashSet<>());
        lock.unlock();
    }

    /**
     * A message is sent to all clients in the named group.
     * @param topicName The group's name.
     * @param message The message to be sent.
     */
    public static void send(String topicName, ChatMessageReceived message) {
        lock.lock();
            // Is there a group with the given name? If not, exit.
            if(!topics.containsKey(topicName)) return;

            // Get clients in group.
            Set<SubscriberClient> clients = topics.get(topicName);

            // Send message to each client.
            for(SubscriberClient client : clients)
                client.send(message);
        lock.unlock();
    }

    /**
     * Send a message to ALL clients, regardless of the group
     * they're in.
     * @param message The message being sent.
     */
    public static void sendAll(Message message) {
        lock.lock();
            // topics.values().stream() returns a collection of SETS of
            // chat clients, i.e. a stream of (sets of chat clients).
            // The flatmap method flattens this into a stream of chat clients.
            topics.values()
                    .stream()
                    .flatMap(Collection::stream)
                    .distinct()
                    .forEach(subscriberClient -> {
                        subscriberClient.send(message);
                        System.out.println("sendAll: " + subscriberClient.handle + ", " + message);
                    });
        lock.unlock();
    }

}
