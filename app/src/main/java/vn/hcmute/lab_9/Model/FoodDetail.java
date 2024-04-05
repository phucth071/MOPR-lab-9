package vn.hcmute.lab_9.Model;

import com.google.gson.annotations.SerializedName;

public class FoodDetail {
    @SerializedName("id")
    private String id;

    @SerializedName("meal")
    private String meal;

    @SerializedName("area")
    private String area;

    @SerializedName("category")
    private String category;

    @SerializedName("instructions")
    private String instructions;

    @SerializedName("strmealthumb")
    private String strmealthumb;

    @SerializedName("price")
    private String price;

    public FoodDetail() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getStrmealthumb() {
        return strmealthumb;
    }

    public void setStrmealthumb(String strmealthumb) {
        this.strmealthumb = strmealthumb;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}