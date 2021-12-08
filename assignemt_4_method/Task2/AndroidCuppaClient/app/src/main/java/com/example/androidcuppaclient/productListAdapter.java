package com.example.androidcuppaclient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import Networking.messages.product;

public class productListAdapter extends RecyclerView.Adapter<productListAdapter.productViewHolder> {
    // The list of messages displayed by the recycler view.
    private ArrayList<product> products = new ArrayList<>();
    private int[] quantities;
    int screenWidth;
    int buttownWidth;
    public String filePath;

    public productListAdapter(ArrayList<product> list, int[] quantities, int screenWidth , String filePath) {
        products = list;
        this.quantities = quantities;
        this.screenWidth = screenWidth;
        buttownWidth = screenWidth/7;
        this.filePath = filePath;
    }

    @NonNull
    @Override
    public productViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.productview,
                        parent, false);
        productViewHolder mvh = new productViewHolder(view);
        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull productViewHolder holder, int position) {
        product Product = products.get(position);
        holder.setProduct(Product, screenWidth, quantities[position]);
    }
    @Override
    public int getItemCount() {

        return products.size();
    }

    public int[] getQuantities()
    {
        return quantities;
    }

    public class productViewHolder extends RecyclerView.ViewHolder {
        public ImageView productPic;
        public TextView productName;
        public TextView productPrice;
        public TextView productDescr;
        public TextView productQuantity;
        ImageButton less;
        ImageButton more;
        product currentProduct;

        public productViewHolder(@NonNull View itemView) {
            super(itemView);
            productPic = itemView.findViewById(R.id.imageView);
            productName = itemView.findViewById(R.id.textViewProductname);
            productPrice = itemView.findViewById(R.id.textviewProductPrice);
            productDescr = itemView.findViewById(R.id.textViewProductDescr);
            productQuantity = itemView.findViewById(R.id.textViewProductQuantity);
            less = itemView.findViewById(R.id.imageButtonLess);
            more = itemView.findViewById(R.id.imageButtonMore);

        }
        public void setProduct(product Product, int dimension, int quantity) {


            currentProduct  = Product;
            productName.setText(currentProduct.getProductName());
            productDescr.setText(currentProduct.getProductDescription());
            productQuantity.setText(String.valueOf(quantity));
            productPrice.setText("R "+String.format("%.2f",currentProduct.getProductPrice()));

            Bitmap icon = BitmapFactory.decodeResource(itemView.getContext().getResources(),
                    R.drawable.logo);
            productPic.setVisibility(View.GONE);
            if(currentProduct.getImageResource()!=0) {
                icon = BitmapFactory.decodeResource(itemView.getContext().getResources(),
                        currentProduct.getImageResource());
                productPic.setVisibility(View.VISIBLE);
                /**File sd = Environment.getExternalStorageDirectory();
                File image = new File(sd+filePath, S);
                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                icon = BitmapFactory.decodeFile(image.getAbsolutePath(),bmOptions);
                icon = Bitmap.createScaledBitmap(icon,dimension,dimension,true);
                 */
            }
            productPic.setImageBitmap(icon);

            ViewGroup.LayoutParams params1 = less.getLayoutParams();
            params1.width = dimension/7;
            params1.height = dimension/7;
            less.setLayoutParams(params1);

            ViewGroup.LayoutParams params2 = more.getLayoutParams();
            params2.width = dimension/7;
            params2.height = dimension/7;
            more.setLayoutParams(params2);

            more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = products.indexOf(currentProduct);
                    quantities[pos] = quantity+1;
                    productQuantity.setText(String.valueOf(quantities[pos]));
                    notifyItemChanged(pos);
                }
            });
            less.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = products.indexOf(currentProduct);
                    if(quantity>0) {
                        quantities[pos] = quantity -1;
                        productQuantity.setText(String.valueOf(quantities[pos]));
                        notifyItemChanged(pos);
                    }
                }
            });
        }

    }

}
