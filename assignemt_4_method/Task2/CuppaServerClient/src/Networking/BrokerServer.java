package Networking;


import Networking.messages.product;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class BrokerServer {
    private final Object TopicsListed;

    public static void main(String[] args) throws Exception {
        new BrokerServer();
    }

    // The server socket that listens to port 5050 for connection requests.
    private ServerSocket server;

    private int clientNum = 0;

    public BrokerServer() throws Exception {
        // Start new server socket on port 5050.
        server = new ServerSocket(5050);
        System.out.printf("Broker server started on: %s \nPort :5050\n",
                InetAddress.getLocalHost().getHostAddress());

        // Create the initial Products to which clients can pick from
        Topics.addProduct("COF213", new product("COF213","Coffee","Coffee is a brewed drink prepared from roasted coffee beans.",9.99));
        Topics.addProduct("ARO203", new product("ARO203","Arabica","black coffee, Arabica beans have a sweeter, more complex flavor",16.99));
        Topics.addProduct("ROB213", new product("ROB213","Robusta","While Arabica is the most popular, Robusta is cheaper and stronger",19.99));
        Topics.addProduct("LAT213", new product("LAT213","Latte","latte is comprised of a shot of espresso and steamed milk with just a touch of foam",14.99));
        Topics.addProduct("CPP213", new product("CPP213","Cappuccino","Cappuccino is a latte made with more foam than steamed milk, often with a sprinkle of cocoa powder or cinnamon on top",24.99));
        Topics.addProduct("AMR213", new product("AMR213","Americano","the americano consists of an espresso shot diluted in hot water",23.99));
        Topics.addProduct("ESP213", new product("ESP213","Espresso","An espresso shot can be served solo or used as the foundation of most coffee drinks, like lattes and macchiatos",22.99));
        Topics.addProduct("DOP213", new product("DOP213","Doppio","A double shot of espresso, the doppio is perfect for putting extra pep in your step.",18.99));
        Topics.addProduct("MAC213", new product("MAC213","Macchiato","2The macchiato is another espresso-based drink that has a small amount of foam on top",25.99));
        Topics.addProduct("MOC213", new product("MOC213","Mocha","The mocha is a chocolate espresso drink with steamed milk and foam",17.99));
        Topics.addProduct("RST213", new product("RST213","Ristretto"," It uses less hot water which creates a sweeter flavor compared to the bitter taste of a traditional shot of espresso",9.99));

        while(true) {
            // Accept connection requests.
            Socket client = server.accept();
            System.out.printf("Connection request received: %s\n", client.getInetAddress().getHostAddress());
            // Increment number of clients encountered.
            clientNum++;

            // Create new client connection object to manage.
            SubscriberClient subscriberClient = new SubscriberClient(client, clientNum);
        }
    }

}
