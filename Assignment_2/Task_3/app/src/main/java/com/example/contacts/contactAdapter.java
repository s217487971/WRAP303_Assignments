package com.example.contacts;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Parcelable;
import android.telecom.Call;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import java.io.File;
import java.util.ArrayList;
import java.util.List;


// RecyclerView Adapter for use with the list of Contacts
public class contactAdapter extends RecyclerView.Adapter<contactAdapter.contactViewHolder> {

    private final ArrayList<contact> contacts; // the list of contacts
    private static Context context = null; // reference to the activity context
    private int curPosition = -1; // current position selected in the list
    //private Object contact;

    // Initialisation
    public contactAdapter(Context context, ArrayList<contact> contacts) {
        this.contacts = contacts;
        this.context = context;
    }



    @NonNull
    @Override
    public contactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview, parent, false);
        return new contactViewHolder(view);
    }

    // Binds details and event handlers to each viewholder
    @Override
    public void onBindViewHolder(@NonNull contactViewHolder holder, int position) {
        // Setting of contact in holder
        contact journal = contacts.get(position);
        holder.setContact((com.example.contacts.contact) journal);
        holder.setlist((ArrayList<contact>) contacts);
        // Find references to floating buttons on this specific contact view in the recyclerview
        View v = holder.itemView;

    }

    // Returns the size of the contact list
    @Override
    public int getItemCount() {
        return contacts.size();
    }

    // Adds a new contact to the end of the list
    public void add(contact contact) {
        contacts.add(contact);
        notifyItemChanged(contacts.size() - 1);
    }

    // Removes the contact at the specific position
    public void remove(int position) {
        contacts.remove(position);
        curPosition = -1;
        notifyDataSetChanged();
    }

    // Returns the list of contacts
    public List<contact> getList() {
        return contacts;
    }


    // View holder for recyclerview to recycler contacts into
    public static class contactViewHolder extends RecyclerView.ViewHolder {
        // References to views
        TextView lblID;
        TextView lblLastText;
        ImageView imageTemp;
        Dialog dialog;
        LinearLayout layout;
        ImageButton call;
        ImageButton messga;
        ImageButton info;
        String number;
        List<contact> list;
        Matrix matrix;



        public contactViewHolder(@NonNull View itemView) {
            super(itemView);
            lblID = itemView.findViewById(R.id.textView);
            lblLastText = itemView.findViewById(R.id.textView2);
            imageTemp = itemView.findViewById(R.id.imageView);
            layout = itemView.findViewById(R.id.linearLayout);
            call = itemView.findViewById(R.id.floatingActionButton5);
            messga = itemView.findViewById(R.id.floatingActionButton4);
            info = itemView.findViewById(R.id.floatingActionButton3);

        }
        public void setlist(ArrayList<contact> list)
        {
            this.list = list;
        }

        // Set views to current contact's values
        void setContact(final contact contact) {
            lblID.setText(contact.getName());
            lblLastText.setText(contact.getNumber());
            layout.setVisibility(View.GONE);
            number = contact.getNumber();
            View.OnClickListener OpenChats = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(layout.getVisibility()==View.VISIBLE)
                    {
                        layout.setVisibility(View.GONE);
                    }
                    else if(layout.getVisibility()==View.GONE)
                    {
                        layout.setVisibility(View.VISIBLE);
                    }
                }
            };

            View.OnClickListener CallNumber = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callPhone();
                    layout.setVisibility(View.GONE);
                }
            };

            View.OnClickListener Message = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    layout.setVisibility(View.GONE);
                   sendSMSMessage(number);
                }
            };

            View.OnClickListener details = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,contact_card.class);
                    intent.putExtra("contact", (Parcelable) contact);
                    intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) list);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    layout.setVisibility(View.GONE);
                    context.startActivity(intent);
                }
            };

            lblID.setOnClickListener(OpenChats);
            lblLastText.setOnClickListener(OpenChats);
            imageTemp.setOnClickListener(OpenChats);
            call.setOnClickListener(CallNumber);
            messga.setOnClickListener(Message);
            info.setOnClickListener(details);
           // lblID.setText(contact.getID());
            //lblLastText.setText(contact.getLastText());

            //Get the dimensions of the View
            int targetW = itemView.getWidth();
            int targetH = itemView.getHeight();

            // Get the dimensions of the bitmap
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;


            // Decode the image file into a Bitmap sized to fill the View
            bmOptions.inJustDecodeBounds = false;
           // bmOptions.inSampleSize = scaleFactor;
            bmOptions.inPurgeable = true;
            if(contact.getImgResource()!=0)
            {
                //Bitmap icon = BitmapFactory.decodeResource(context.getResources(),R.drawable.avatar_01);
                Bitmap originalImage = BitmapFactory.decodeResource(context.getResources(), contact.getImgResource());
                //imageTemp.setImageBitmap(icon);

                //image Dimensions
               int width = originalImage.getWidth();
               int height = originalImage.getHeight();

               //View Dimensions
                int scaleWidth = reduce(width);
                int scaleHeight = reduce(height);

                Bitmap resizedBitmap = Bitmap.createScaledBitmap(originalImage,width,scaleHeight,true);
                imageTemp.setImageBitmap(resizedBitmap);

            }

        }

        public String getSafeSubstring(String s, int maxLength) {
            if (!TextUtils.isEmpty(s)) {
                if (s.length() >= maxLength) {
                    return s.substring(0, maxLength);
                }
            }
            return s;
        }

        public static Bitmap drawableToBitmap(Drawable drawable) {
            Bitmap bitmap = null;

            if (drawable instanceof BitmapDrawable) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                if (bitmapDrawable.getBitmap() != null) {
                    return bitmapDrawable.getBitmap();
                }
            }

            if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
                bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
            } else {
                bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        }
        protected void callPhone()
        {
           /** Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", number, null));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);*/
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse("tel:" + number));
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(intent);
            }
        }
        protected void sendSMSMessage(String number) {
            //SmsManager smsManager = SmsManager.getDefault();
           // smsManager.sendTextMessage(number, null, null, null, null);
            Uri uri = Uri.parse("smsto:"+number);
            Intent it = new Intent(Intent.ACTION_SENDTO, uri);
            it.putExtra("sms_body", "");
            //Intent smsIntent = new Intent(Intent.ACTION_VIEW);
            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(it);
            /**smsIntent.setData(Uri.parse("smsto:"));
            smsIntent.setType("vnd.android-dir/mms-sms");
            smsIntent.putExtra("address", number);
            smsIntent.putExtra("sms_body", "Test ");

            try {
                context.startActivity(smsIntent);
                Log.i("Finished sending SMS...", "");
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(context,
                        "SMS faild, please try again later.", Toast.LENGTH_SHORT).show();
            }*/
        }
        protected int reduce(int large)
        {
            int scale = large/4;
            large = scale*3;
            return large;
        }
    }


}

