package Networking;



import Networking.messages.Message;
import Networking.messages.invoic;
import Networking.messages.product;
import Networking.messages.server.ChatMessageReceived;
import Networking.messages.server.Subscribed;
import Networking.messages.server.Unsubscribed;
import Networking.messages.server.collectOrder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class is responsible for managing chat customers and sending messages to
 * clients in specific customers.
 */
public class Topics {


    // Lock to prevent multiple threads manipulating the customers data
    // structure while busy with an operation.
    private static final ReentrantLock lock = new ReentrantLock();

    // [Customer ID] -> {client}
    public static final Map<String, SubscriberClient> customers = new HashMap<>();

    /**
     * Product Maps Product Codes to Product Objects
     */
    public static final Map<String, product> products = new HashMap<>();

    /**
     * Orders maps customerID to their order Invoice
     */
    public static Map<String, String> orders = new HashMap<>();

    static volatile ArrayList<invoic> collections = new ArrayList<>();


    /**
     * Subscribe a new group. Before subscribeing a new group, the client will leave
     * any customers it is currently in. All other clients in the group are
     * notified of the client subscribeing.
     * @param customerID The new subscriber's Id.
     * @param client The client subscribing.
     */
    public static void subscribe(String customerID, SubscriberClient client) {
        // If already in a group, leave it.
        //leave(client);

        // If no such group, create it.
        if(!customers.containsKey(customerID))
            addCustomer(customerID, client);

        // Now subscribe the new group.
        lock.lock();

            // Add client to group.
            client.topicName = customerID;

            // Tell all clients that client subscribed group.
            client.send(new Subscribed(customerID, client.handle));
        lock.unlock();
    }

    /**
     * The client leaves all customers currently a member of. All other clients
     * are sent a notification of this fact.
     * @param client The client leaving.
     */
    public static void leave(SubscriberClient client) {
        lock.lock();
            // Get customers to which client belongs.
            // Remove client from these customers and notify other clients of this.
                // Remove client from group.
                customers.remove(client);
                orders.remove(client);
                client.topicName = "";
                // Send message to other clients in group.
                Unsubscribed msg = new Unsubscribed( client.handle);
                client.send(msg);
        lock.unlock();
    }

    public static void addOrder(SubscriberClient client, int[] orderSequence, String date, String time, String telephone)
    {
        int q = 1;
        if(orderSequence.length>0)
        {
            long count1 = 0;
            String directory = System.getProperty("user.dir");
            directory = directory + "\\Invoices";
            try (Stream<Path> files = Files.list(Paths.get(directory))) {
                count1 = files.count();
                count1 = orders.size() + count1;
            }
            catch (Exception e)
            {
                System.out.println("Error Ouptut:"+e.toString());
            }
            double cost = 0;
            String OrderID = "Order "+String.valueOf(count1);
            ChatMessageReceived inv1 = new ChatMessageReceived(OrderID, client.handle, OrderID);
            client.send(inv1);
            if(time.equals(""))
            {
                LocalTime time2=  LocalTime.now();
                time = time2.toString();
                time = time.substring(0, 4);
            }
            if(date.equals(""))
            {
                LocalDate date2 = LocalDate.now();
                date = date2.toString();
            }
            StringBuilder invoice = new StringBuilder(OrderID);
            invoice.append("\n");
            TableGenerator orderDetails = new TableGenerator();
            List<String> headersFirst = new ArrayList<>();
            headersFirst.add("Customer ID");
            headersFirst.add("PhoneNumber");
            headersFirst.add("Order Number");
            headersFirst.add("Date");
            headersFirst.add("time");
            List<String> rowsList1 = new ArrayList<>();
            rowsList1.add(client.handle);
            rowsList1.add(telephone);
            rowsList1.add(OrderID);
            rowsList1.add(date);
            rowsList1.add(time);
            List<List<String> >customerDetails = new ArrayList<>();
            customerDetails.add(rowsList1);
            String toprow = orderDetails.generateTable(headersFirst, customerDetails);
            invoice.append(toprow);
            invoice.append("\n");
            TableGenerator generatorInvoice = new TableGenerator();
            List<String> headersList = new ArrayList<>();
            headersList.add("Product");
            headersList.add("Quantity");
            headersList.add("Price");
            List<List<String>> rowsList = new ArrayList<>();


            /**
            invoice.append("\n");
            invoice.append("=========================");
            invoice.append("\n");
            invoice.append("Product  Qunatity  Price");
            invoice.append("\n");
            invoice.append("=========================");
            invoice.append("\n");
             */
            int count = 0;
            int limit  = orderSequence.length;
            for (product currentproduct:products.values()
                 ) {
                if(count<limit )
                {
                    int current = orderSequence[count];
                    if(current>0) {
                        /**invoice.append(currentproduct.getProductName());
                        invoice.append("   ");
                        invoice.append(current);
                        invoice.append("   ");
                        invoice.append(currentproduct.getProductPrice());
                        invoice.append("\n");
                         */
                        cost = cost + current * currentproduct.getProductPrice();
                        count++;
                        List<String> row = new ArrayList<>();
                        row.add(currentproduct.getProductName());
                        row.add(String.valueOf(current));
                        row.add(String.valueOf(currentproduct.getProductPrice()));
                        rowsList.add(row);
                    }
                }
            }
            cost = Math.round(cost*100.0)/100.0;
            invoice.append(generatorInvoice.generateTable(headersList,rowsList));
            //invoice.append("=========================");
            invoice.append("\n");
            invoice.append("Total : R ");
            invoice.append(cost);
            orders.put(OrderID, invoice.toString());
            collectOrder Invoice = new collectOrder(OrderID,invoice.toString(), client.handle);
            client.send(Invoice);
            collections.add(new invoic(client.handle, Invoice.toString()));

            String filename = directory + "\\" +String.valueOf(OrderID) + ".txt";
            FileOutputStream fos = null;
            try {
                File f = new File(directory);
                if(!(f.exists()))
                {
                    f.mkdir();
                }
                fos = new FileOutputStream(filename);
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
                bw.write(invoice.toString());
                bw.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void collection()
    {
        Thread SendNotifications = new Thread(new Runnable() {
            @Override
            public void run() {
                while (collections.size()>0)
                {
                    for (invoic ss:collections
                         ) {

                    }
                }
            }
        });
        SendNotifications.start();
    }

    /**
     * A new (empty) group is added.
     * @param productID The new product's ID.
     */
    public static void addProduct(String productID, product product) {
        lock.lock();
            products.put(productID, product);
        lock.unlock();
    }


    public static void addCustomer(String customerID, SubscriberClient client)
    {
        lock.lock();
        customers.put(customerID, client);
        lock.unlock();
    }

    /**
     * A message is sent to all clients in the named group.
     * @param CustomerID The group's name.
     * @param message The message to be sent.
     */
    public static void send(String CustomerID, ChatMessageReceived message) {
        lock.lock();
            // Is there a group with the given name? If not, exit.
            if(!customers.containsKey(CustomerID)) return;

            // Get client in group.
            SubscriberClient client = customers.get(CustomerID);

            // Send message to each client.
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
            // customers.values().stream() returns a collection of SETS of
            // chat clients, i.e. a stream of (sets of chat clients).
            // The flatmap method flattens this into a stream of chat clients.
        for (SubscriberClient customer:customers.values()
             ) {
            customer.send(message);
        }
        lock.unlock();
    }

}
