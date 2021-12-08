package Networking.messages;

public class invoic {

    String id;
    String text;

    public invoic(String id, String text) {
        this.id = id;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }
}
