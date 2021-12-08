import java.util.Map;
import java.util.Stack;

//Class was given by Dr Vogts
@FunctionalInterface
public interface Subscriber {

    void onPublished(Object publisher, String topic,Map<String, Object> params);

    public class Publisher
    {
        String publisher;
        String message;
        Stack<String> messages;

        public Publisher(String publisher, String message)
        {
            this.publisher = publisher;
            this.message = message;
            messages = new Stack<>();
            messages.push(message);
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
