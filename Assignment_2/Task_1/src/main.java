import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class main {


    public static void main(String[] args) {

        //Create the Single Instance of A Broker
        PubSubBroker broker = PubSubBroker.getInstance();

        // Create a Sample of Topics
        String[] Topiscs =  new String[]{"Sports","Art","Tech","News","Culture","Trending","Weather","Fashion","Travel","Covid-19"};


        Map<String, Object> publications = new HashMap<>();

        Map<String, Subscriber> allSubscribers = new HashMap<>();
        // Assign Topics to Publishers


        Subscriber subscriber = new Subscriber() {
            @Override
            public void onPublished(Object publisher, String topic, Map<String, Object> params) {
                params.put(topic,(Subscriber.Publisher)publisher);
                Subscriber.Publisher pub = (Subscriber.Publisher)publisher;
                System.out.println(topic+": "+ pub.getMessage() +" by "+pub.getPublisher()+"\n");
            }
        };
        Subscriber subscriber1 = new Subscriber() {
            @Override
            public void onPublished(Object publisher, String topic, Map<String, Object> params) {
                params.put(topic,(Subscriber.Publisher)publisher);
                Subscriber.Publisher pub = (Subscriber.Publisher)publisher;
                System.out.println(topic+": "+ pub.getMessage() +" by "+pub.getPublisher()+"\n");
            }
        };
        Subscriber subscriber2 = new Subscriber() {
            @Override
            public void onPublished(Object publisher, String topic, Map<String, Object> params) {
                params.put(topic,(Subscriber.Publisher)publisher);
                Subscriber.Publisher pub = (Subscriber.Publisher)publisher;
                System.out.println(topic+": "+ pub.getMessage() +" by "+pub.getPublisher()+"\n");
            }
        };
        Subscriber subscriber3 = new Subscriber() {
            @Override
            public void onPublished(Object publisher, String topic, Map<String, Object> params) {
                params.put(topic,(Subscriber.Publisher)publisher);
                Subscriber.Publisher pub = (Subscriber.Publisher)publisher;
                System.out.println(topic+": "+ pub.getMessage() +" by "+pub.getPublisher()+"\n");
            }
        };
        Subscriber subscriber4 = new Subscriber() {
            @Override
            public void onPublished(Object publisher, String topic, Map<String, Object> params) {
                params.put(topic,(Subscriber.Publisher)publisher);
                Subscriber.Publisher pub = (Subscriber.Publisher)publisher;
                System.out.println(topic+": "+ pub.getMessage() +" by "+pub.getPublisher()+"\n");
            }
        };
        Subscriber subscriber5 = new Subscriber() {
            @Override
            public void onPublished(Object publisher, String topic, Map<String, Object> params) {
                params.put(topic,(Subscriber.Publisher)publisher);
                Subscriber.Publisher pub = (Subscriber.Publisher)publisher;
                System.out.println(topic+": "+ pub.getMessage() +" by "+pub.getPublisher()+"\n");
            }
        };

        // Assign Subscribers to Publishers
        broker.subscribe(Topiscs[0],subscriber);
        broker.subscribe(Topiscs[1],subscriber2);
        broker.subscribe(Topiscs[2],subscriber3);
        broker.subscribe(Topiscs[3],subscriber3);
        broker.subscribe(Topiscs[4],subscriber4);
        broker.subscribe(Topiscs[5],subscriber5);
        broker.subscribe(Topiscs[6],subscriber1);
        broker.subscribe(Topiscs[7],subscriber2);
        broker.subscribe(Topiscs[8],subscriber4);
        broker.subscribe(Topiscs[9],subscriber5);
        broker.subscribe(Topiscs[0],subscriber4);
        broker.subscribe(Topiscs[1],subscriber5);
        broker.subscribe(Topiscs[2],subscriber4);
        broker.subscribe(Topiscs[3],subscriber3);
        broker.subscribe(Topiscs[4],subscriber2);
        broker.subscribe(Topiscs[5],subscriber);
        broker.subscribe(Topiscs[6],subscriber4);
        broker.subscribe(Topiscs[7],subscriber1);
        broker.subscribe(Topiscs[8],subscriber3);
        broker.subscribe(Topiscs[9],subscriber);

        //Publishers publish messages to their subscribers
        broker.publish(new Subscriber.Publisher("Emanuell","Sundowns beats Chelsea "),Topiscs[0],publications);
        //
        boolean done = false;
        Scanner read = new Scanner(System.in);
        String response = "";
        while (!done)
        {
            System.out.println("=======Menu=====\n1.New Subscriber\n2.Select Subscriber\n3.Publish\n4.Exit\n");
            response = read.nextLine();

            switch (response)
            {
                case "1":{
                    System.out.print("===Select Topic===\n");
                    for (int i = 0; i < 10; i++) {
                        System.out.println((i+1)+"."+Topiscs[i]);
                    }
                    response = read.nextLine();
                    Subscriber subscriber6 = new Subscriber() {
                        @Override
                        public void onPublished(Object publisher, String topic, Map<String, Object> params) {
                            params.put(topic,(Subscriber.Publisher)publisher);
                            Subscriber.Publisher pub = (Subscriber.Publisher)publisher;
                            System.out.println(topic+": "+ pub.getMessage() +" by "+pub.getPublisher()+"\n");
                        }
                    };
                    broker.subscribe(Topiscs[Integer.valueOf(response)-1],subscriber6);
                    allSubscribers.put("Subscriber"+allSubscribers.size(),subscriber6);
                } break;
                case "2":{
                    System.out.println("======Select Subscriber====");
                    for (int i = 0; i < allSubscribers.size(); i++) {
                        System.out.println((i+1)+".Subscriber"+(i+1));
                    }
                    response = read.nextLine();
                    Subscriber subscriber6 = allSubscribers.get("Subscriber"+(Integer.valueOf(response)-1));
                    System.out.println("==Option==\n1.Subscribe\n2.Unsubscribe");
                    response = read.nextLine();
                    int val = Integer.valueOf(response);
                    if(val==1)
                    {
                        System.out.println("====Select Topic====");
                        for (int i = 0; i <10 ; i++) {
                            System.out.println((i+1)+"."+Topiscs[i]);
                        }
                        response = read.nextLine();
                        val = Integer.valueOf(response);
                        broker.subscribe(Topiscs[val-1],subscriber6);
                    }
                    if(val==2)
                    {
                        System.out.println("====Select Topic====");
                        for (int i = 0; i <10 ; i++) {
                            System.out.println((i+1)+"."+Topiscs[i]);
                        }
                        System.out.println("11.All");
                        response = read.nextLine();
                        val = Integer.valueOf(response);
                        if(val==11)
                        {
                            broker.unsubscribe(subscriber6);
                        }
                        else
                        {
                        broker.unsubscribe(Topiscs[val-1],subscriber6);}
                    }
                } break;
                case "3":{
                    System.out.println("====Select Topic====");
                    for (int i = 0; i <10 ; i++) {
                            System.out.println((i+1)+"."+Topiscs[i]);
                    }
                    System.out.println("11.All");
                    response = read.nextLine();
                    int value = Integer.valueOf(response)-1;
                    System.out.println("Enter Publisher Name ?");
                    String response1 = read.nextLine();
                    System.out.println("Enter Message ?");
                    response = read.nextLine();
                    if(value==10)
                    {

                    }
                    else
                    {
                        broker.publish(new Subscriber.Publisher(response1,response),Topiscs[value],publications);
                    }
                } break;
                case "4":{
                    done = true;
                } break;
                default:{

                } break;
            }
        }
    }
}
