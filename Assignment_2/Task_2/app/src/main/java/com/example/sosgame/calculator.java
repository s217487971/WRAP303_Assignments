package com.example.sosgame;

import java.util.Map;

public interface calculator {
    void onPublished(Publisher publisher, String topic, Map<String, Object> params);

    public class Publisher
    {
        String publisher;
        String message;


        public Publisher(String publisher, String message)
        {
            this.publisher = publisher;
            this.message = message;

        }
        public String getMessage()
        {
            return message;
        }
        public String getPublisher()
        {
            return publisher;
        }
    }
}
