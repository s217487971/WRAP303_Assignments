package Networking;


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

        // Create the initial Topics to which clients can pick from

        Topics.addTopic("Racing");
        Topics.addTopic("Tech");

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
