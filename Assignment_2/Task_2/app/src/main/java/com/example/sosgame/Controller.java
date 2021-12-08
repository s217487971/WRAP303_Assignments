package com.example.sosgame;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Controller {
    // Collection of subscribers. Maps topic onto set of subscribers.
    private Map<String, Set<calculator>> subscribers;

    // Singleton instance of the broker.
    static private Controller instance = null;

    // Made private so only can be called by self.
    private Controller() {
        subscribers = new HashMap<>();
    }

    // Provides access to a single instance of a broker.
    static public Controller getInstance() {
        if(instance == null)
            instance = new Controller();

        return instance;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void subscribe(String topic, calculator subscriber) {
        // Get set of subscribers listening to topic. If none, create a new set.
        Set<calculator> subscriberSet;
        subscriberSet = subscribers.get(topic);
        if(subscriberSet==null)
        {
            subscriberSet = new HashSet<>();

        }
        // Add subscriber to the set.
        subscriberSet.add(subscriber);
        subscribers.put(topic,subscriberSet);
    }

    public void unsubscribe(String topic, calculator subscriber) {
        // Get set of subscribers listening to the topic.
        Set<calculator> subscriberSet;
        subscriberSet = subscribers.get(topic);

        // If no-one listening, stop.
        if(subscriberSet == null)
            return;

        // Remove from set.
        subscriberSet.remove(subscriber);

        // Empty set? If so, remove the set.
        if(subscriberSet.size() == 0)
            subscribers.remove(topic);
    }

    public void unsubscribe(calculator subscriber) {
        // Getting topics, but copying to another structure since the
        // process of unsubscribing could remove a subscriber set, hence
        // modify the keySet while iterating through it - i.e. a problem.
        List<String> topics = new ArrayList<>();
        topics.addAll(subscribers.keySet());

        for (String topic : topics) {
            unsubscribe(topic, subscriber);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void publish(calculator.Publisher publisher, String topic, Map<String, Object> params) {
        Set<calculator> subscriberSet;
        subscriberSet = subscribers.get(topic);

        // If no subscribers for the topic, done!
        if(subscriberSet == null)
            return;

        // Notify all subscribers of the publishing of the message.
        for (calculator x: subscriberSet
             ) {
            x.onPublished(publisher,topic, params);
        }
    }
}
