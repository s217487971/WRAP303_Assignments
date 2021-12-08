package Networking.messages;

import java.io.Serializable;

public class product implements Serializable {
    private static final long serialVersionUID = 94554L;

    private String ProductID;
    private String ProductName;
    private String ProductDescription;
    private double ProductPrice;
    private int imageResource;

    public product(String productID, String productName, String productDescription, double productPrice, int imageResource) {
        ProductID = productID;
        ProductName = productName;
        ProductDescription = productDescription;
        ProductPrice = productPrice;
        this.imageResource = imageResource;
    }

    public product(String productID, String productName, String productDescription, double productPrice) {
        ProductID = productID;
        ProductName = productName;
        ProductDescription = productDescription;
        ProductPrice = productPrice;
    }

    public String getProductID() {
        return ProductID;
    }


    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductDescription() {
        return ProductDescription;
    }

    public void setProductDescription(String productDescription) {
        ProductDescription = productDescription;
    }

    public double getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(double productPrice) {
        ProductPrice = productPrice;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }
}
