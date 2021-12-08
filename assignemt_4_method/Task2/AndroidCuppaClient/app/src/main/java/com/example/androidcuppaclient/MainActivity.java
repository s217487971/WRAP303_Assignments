package com.example.androidcuppaclient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import Networking.SubscriberClient;
import Networking.messages.Message;
import Networking.messages.client.SendOrder;
import Networking.messages.client.SetHandle;
import Networking.messages.product;
import Networking.messages.server.ChatMessageReceived;
import Networking.messages.server.HandleSet;
import Networking.messages.server.TopicsListed;
import Networking.messages.server.collectOrder;

public class MainActivity extends AppCompatActivity {

    SubscriberClient client;
    ArrayList<product> allproducts;
    ArrayList<String> invoices;
    ArrayList<String> collected;
    int[] quantities;
    private final int Internet_Requst = 32;
    private final int storage_request = 31;
    private final int read_request = 30;
    private final int MakeOrder = 33;
    private final int sendOrderThrough = 34;
    private final int savedetails = 23;
    ImageButton sendOrder;
    TextView price;
    private  String telephone = "";
    private  String handle = "";
    int firstTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int demension = width/3;


        ImageView logo = findViewById(R.id.cuppaLogo);
        ImageButton createOrder = findViewById(R.id.imageButton3);
        sendOrder = findViewById(R.id.imageButton2);
        price = findViewById(R.id.textViewTotal);
        price.setVisibility(View.GONE);
        sendOrder.setVisibility(View.GONE);

        /**
         * We need to Request Permission for saving pictures,
         */
        if(checkSelfPermission(Manifest.permission.INTERNET)!= PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.INTERNET, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, Internet_Requst);
        }
        ReadDataFromStorage();

        ViewGroup.LayoutParams layoutParams = createOrder.getLayoutParams();
        layoutParams.width = demension;
        layoutParams.height = demension;
        createOrder.setLayoutParams(layoutParams);


        ViewGroup.LayoutParams layoutParams1 = sendOrder.getLayoutParams();
        layoutParams1.width = demension;
        layoutParams1.height = demension;
        sendOrder.setLayoutParams(layoutParams1);

        ViewGroup.LayoutParams params2 = logo.getLayoutParams();
        params2.width = demension*2;
        params2.height =demension*2;
        logo.setLayoutParams(params2);

        allproducts = new ArrayList();
        /**selectedProducts = new ArrayList<>();
        allproducts.add( new product("COF213","Coffee","200ml Cup of Recoffee, Sweetner and Creamer",9.99, R.drawable.coffee));
        allproducts.add(new product("COF214","Coffee L","350ml Cup of Recoffee, Sweetner and Creamer",15.99));
        allproducts.add( new product("ARO203","Arabica","black coffee, Arabica beans have a sweeter, more complex flavor",16.99, R.drawable.arabica));
        allproducts.add( new product("COF213","Robusta","While Arabica is the most popular, Robusta is cheaper and stronger",19.99, R.drawable.robusta));
        allproducts.add( new product("COF213","Latte","latte is comprised of a shot of espresso and steamed milk with just a touch of foam",14.99));
        allproducts.add( new product("COF213","Cappuccino","Cappuccino is a latte made with more foam than steamed milk, often with a sprinkle of cocoa powder or cinnamon on top",24.99));
        allproducts.add( new product("COF213","Americano","the americano consists of an espresso shot diluted in hot water",23.99));
        allproducts.add( new product("COF213","Espresso","An espresso shot can be served solo or used as the foundation of most coffee drinks, like lattes and macchiatos",22.99, R.drawable.espresso));
        allproducts.add( new product("COF213","Doppio","A double shot of espresso, the doppio is perfect for putting extra pep in your step.",18.99));
        allproducts.add( new product("COF213","Macchiato","2The macchiato is another espresso-based drink that has a small amount of foam on top",25.99));
        allproducts.add( new product("COF213","Mocha","The mocha is a chocolate espresso drink with steamed milk and foam",17.99));
        allproducts.add(new product("COF213","Ristretto"," It uses less hot water which creates a sweeter flavor compared to the bitter taste of a traditional shot of espresso",9.99));*/

        client = new SubscriberClient(
                message -> runOnUiThread(
                        () -> messageReceived(message)
                ));
        /**
         * When Marking Please change this server address to the one that will be displayed when you run your server app
         */
        client.connect("192.168.43.178",handle);
        createOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), menu.class);
                intent.putParcelableArrayListExtra("products", allproducts);
                startActivityForResult(intent,MakeOrder);
            }
        });
        /**
         * The Send Order Button sends a list of number of products to the server, in their order, the server will return an Order Number
         */
        sendOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tm ="";
                String dt ="";
                SendOrder msg = new SendOrder(quantities, telephone, handle, dt, tm);
               client.SendOrder(msg);
            }
        });
    }

    private void messageReceived(Message message) {
        /**
         * The Server will send a list of products it has, the pictures are stored in the drawables folder
         * Upon recieving the list, then the list is cached into an arrayList
         */
        if(message instanceof TopicsListed)
        {
            String d = message.toString();
            allproducts.addAll(((TopicsListed) message).topicNames);
            allproducts.get(2).setImageResource(R.drawable.arabica);
            allproducts.get(3).setImageResource(R.drawable.robusta);
            allproducts.get(0).setImageResource(R.drawable.coffee);
            allproducts.get(6).setImageResource(R.drawable.espresso);
        }
        /**
         * The Chat Message is an OrderNumber, it notifies us that the order was recieved however it is not yet complete.
         *
         */
        else if(message instanceof ChatMessageReceived)
        {
            String invoice = ((ChatMessageReceived) message).chatMessage;
            sendOrder.setVisibility(View.GONE);
            if(invoices==null)
            {
                invoices = new ArrayList<>();
            }
            invoices.add(invoice);
            UpdatePriceText();

        }
        /**
         * The Customer Does not Add their name, the server creates one for them. Then sends it back
         * The unique Customer ID generated by the server is then saved has a handle
         * The Customer needs to have a Telephone since according to the assignment requirements it is needed
         * If the Telephone cannot be read from the text file in internal storage then the activity will capture
         */
        else if(message instanceof HandleSet)
        {
            if(handle.equals(""))
            {
                handle  = ((HandleSet) message).handle;
                Intent datab = new Intent(getApplicationContext(), details.class);
                datab.putExtra("handle", handle);
                startActivityForResult(datab,savedetails);
            }
        }
        /**
         * A message is sent to notify the server that the order has been collected
         */
        else if (message instanceof collectOrder)
        {
            if(firstTime==0) {
                String invoiceCode = ((collectOrder) message).orderId;
                String invoice = ((collectOrder) message).Invoice;
                Intent intent = new Intent(getApplicationContext(), collection.class);
                intent.putExtra("invoice", invoice);
                startActivityForResult(intent, sendOrderThrough);
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Collect Order", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * The Activity for result approach has been used so that we do not lose connection to the server.
     * One activity collects the list of Order items and returns them to the Main Activiy
     * The Second activity Dispalys order items and returns to main when the order is collected
     * the final activty captures the Details of the Subscriber first time the use the app and save them to a file
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MakeOrder) {
            if(resultCode == Activity.RESULT_OK){
               quantities = data.getIntArrayExtra("quantities");
                    sendOrder.setVisibility(View.VISIBLE);
                    double costs = 0;
                    for (int i = 0; i < allproducts.size(); i++) {
                        int qantitiy = quantities[i];
                        product product = allproducts.get(i);
                        costs = costs + (product.getProductPrice()*qantitiy);
                    }
                price.setText("Total R "+ String.format("%.2f",costs));
                    price.setVisibility(View.VISIBLE);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                // Write your code if there's no result
                sendOrder.setVisibility(View.GONE);
            }
        }
        else if(requestCode == sendOrderThrough)
        {
            if(resultCode == Activity.RESULT_OK){
                String inv = data.getStringExtra("code");

                if(collected==null)
                {
                    collected = new ArrayList<>();
                }
                if(invoices.size()>0) {
                    collected.add(invoices.get(invoices.size() - 1));
                    invoices.remove(invoices.size() - 1);
                }
                sendOrder.setVisibility(View.VISIBLE);
                client.sendChatMessage("Order Collected", "customers");
                UpdatePriceText();
                Toast.makeText(getApplicationContext(),"Order Collected Successfully", Toast.LENGTH_LONG).show();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                // Write your code if there's no result
            }
        }
        else if(requestCode == savedetails)
        {
            if(resultCode == Activity.RESULT_OK){
                telephone = data.getStringExtra("telephone");
                }
        }
    }

    /**
     * This method reads user data that has been saved from first use, it might not work for higher API levels
     */
    public void ReadDataFromStorage()
    {
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getApplicationContext().getPackageName()
                + "/Files");
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                return;
            }
        }
        File ListFile;
        String ListFileName="profile.txt";
        ListFile = new File(mediaStorageDir.getPath() + File.separator + ListFileName);

        if(ListFile.exists()) {
            BufferedReader reader;
            try {
                reader = new BufferedReader(new FileReader(ListFile));
                String line = reader.readLine();
                if (line != null) {
                    String string = line;
                    String[] parts = string.split(":");
                    handle = parts[0];
                    telephone = parts[1];
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * THIS Updates the textsView about the states of orders.
     */
    public void UpdatePriceText()
    {
        String Text = "";
        if(invoices.size()>0) {
            for (String inv : invoices
            ) {
                Text = Text +" "+ inv + " In Progress\n";
            }
        }
        if(collected!=null)
        {
            if(collected.size()>0) {
                for (String inv : collected
                ) {
                    Text = Text + " " + inv + " Collected\n";
                }
            }
        }
        price.setText(Text);
        price.setVisibility(View.VISIBLE);
    }
}