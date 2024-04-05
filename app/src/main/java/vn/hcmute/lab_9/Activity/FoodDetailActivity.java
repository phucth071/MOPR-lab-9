package vn.hcmute.lab_9.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.hcmute.lab_9.Model.FoodDetail;
import vn.hcmute.lab_9.Model.FoodDetailResponse;
import vn.hcmute.lab_9.R;
import vn.hcmute.lab_9.RetrofitClient;
import vn.hcmute.lab_9.Service.APIService;

public class FoodDetailActivity extends AppCompatActivity {

    TextView foodName, foodPrice, foodDescription;
    CircleImageView foodImage;
    ImageView btnMinus, btnPlus;
    TextView quantity;
    APIService apiService;
    FoodDetail food;
    Button btnAddToCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        MappingView();
        getFoodDetail();

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentQuantity = Integer.parseInt(quantity.getText().toString());
                if (currentQuantity > 1) {
                    currentQuantity--;
                    quantity.setText(String.valueOf(currentQuantity));
                }
            }
        });

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentQuantity = Integer.parseInt(quantity.getText().toString());
                currentQuantity++;
                quantity.setText(String.valueOf(currentQuantity));
            }
        });
    }

    private void getFoodDetail() {
        int foodId = getIntent().getIntExtra("FOOD_ID", 0);
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getFoodDetail(foodId).enqueue(new Callback<FoodDetailResponse>() {
            @Override
            public void onResponse(Call<FoodDetailResponse> call, Response<FoodDetailResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("API Response", "onResponse: " + response.body().isSuccess());
                    if (response.body().isSuccess()) {
                        Log.d("API Response", "onResponse: " + response.body().getResult().get(0).getId());
                        food = response.body().getResult().get(0);
                        foodName.setText(food.getMeal());
                        foodPrice.setText(String.format("$%s", food.getPrice()));
                        foodDescription.setText(food.getInstructions());
                        Glide.with(getApplicationContext()).load(food.getStrmealthumb()).into(foodImage);
                    } else {
                        Log.d("API Response", "onResponse: " + response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<FoodDetailResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void MappingView() {
        foodName = findViewById(R.id.food_detail_name);
        foodPrice = findViewById(R.id.food_detail_price);
        foodDescription = findViewById(R.id.food_detail_description);
        foodImage = findViewById(R.id.food_detail_image);
        btnMinus = findViewById(R.id.btn_minus);
        btnPlus = findViewById(R.id.btn_plus);
        quantity = findViewById(R.id.quantity);
        btnAddToCart = findViewById(R.id.btn_add_to_cart);
    }
}