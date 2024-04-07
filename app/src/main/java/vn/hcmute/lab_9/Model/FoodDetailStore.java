package vn.hcmute.lab_9.Model;

public class FoodDetailStore {
    private String id;
    private String name;
    private String imageUrl;
    private int quantity;
    private double price;

    public FoodDetailStore() {
    }

    public FoodDetailStore(String id, String name, String imageUrl, int quantity, double price) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
