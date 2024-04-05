package vn.hcmute.lab_9.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.hcmute.lab_9.Adapter.FoodListAdapter;
import vn.hcmute.lab_9.Model.FoodReview;
import vn.hcmute.lab_9.R;
import vn.hcmute.lab_9.RecyclerView.Decoration.SpacesItemDecoration;
import vn.hcmute.lab_9.RetrofitClient;
import vn.hcmute.lab_9.Service.APIService;

public class FoodListActivity extends AppCompatActivity {

    RecyclerView rcFoodList;
    List<FoodReview> foodList;
    APIService apiService;
    FoodListAdapter foodListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        rcFoodList = findViewById(R.id.rc_food_list);

        foodList = new ArrayList<>();

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        rcFoodList.setLayoutManager(layoutManager);
        int spaceInPixels = getResources().getDimensionPixelSize(R.dimen.margin_15);
        rcFoodList.addItemDecoration(new SpacesItemDecoration(spaceInPixels));
        rcFoodList.setHasFixedSize(true);
        foodListAdapter = new FoodListAdapter(FoodListActivity.this, foodList);
        rcFoodList.setAdapter(foodListAdapter);
        GetFoodList();

    }

    private void GetFoodList() {
        int categoryId = getIntent().getIntExtra("CATEGORY_ID", 0);
        Log.d("CateID", "Category ID: " + categoryId);
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getFoodCategory(categoryId).enqueue(new Callback<List<FoodReview>>() {
            @Override
            public void onResponse(Call<List<FoodReview>> call, Response<List<FoodReview>> response) {
                if (response.isSuccessful()) {
                    foodList = response.body();
                    foodListAdapter.updateData(foodList);
                } else {
                    int statusCode = response.code();
                }
            }

            @Override
            public void onFailure(Call<List<FoodReview>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}