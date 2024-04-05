package vn.hcmute.lab_9.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class FoodDetailResponse implements Serializable {
    @SerializedName("success")
    private boolean success;

    @SerializedName("massage")
    private String message;

    @SerializedName("result")
    private List<FoodDetail> result;

    public FoodDetailResponse() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<FoodDetail> getResult() {
        return result;
    }

    public void setResult(List<FoodDetail> result) {
        this.result = result;
    }
}
