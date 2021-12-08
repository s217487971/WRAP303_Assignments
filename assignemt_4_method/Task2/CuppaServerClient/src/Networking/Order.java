package Networking;

public class Order {
    String productID;
    int Qty;

    public Order(String productID, int qty) {
        this.productID = productID;
        Qty = qty;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public int getQty() {
        return Qty;
    }

    public void setQty(int qty) {
        Qty = qty;
    }
}
