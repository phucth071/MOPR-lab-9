package vn.hcmute.lab_9.Service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import vn.hcmute.lab_9.Model.Category;
import vn.hcmute.lab_9.Model.FoodDetail;
import vn.hcmute.lab_9.Model.FoodDetailResponse;
import vn.hcmute.lab_9.Model.FoodReview;

public interface APIService {
    @GET("categories.php")
    Call<List<Category>> getCategoryAll();

    @FormUrlEncoded
    @POST("getcategory.php")
    Call<List<FoodReview>> getFoodCategory(@Field("idcategory") int idcategory);

    @FormUrlEncoded
    @POST("newmealdetail.php")
    Call<FoodDetailResponse> getFoodDetail(@Field("id") int id);
}
