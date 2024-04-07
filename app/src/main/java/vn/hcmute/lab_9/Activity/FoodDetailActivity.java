package vn.hcmute.lab_9.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.hcmute.lab_9.DatabaseHelper;
import vn.hcmute.lab_9.Model.FoodDetail;
import vn.hcmute.lab_9.Model.FoodDetailResponse;
import vn.hcmute.lab_9.Model.FoodDetailStore;
import vn.hcmute.lab_9.R;
import vn.hcmute.lab_9.RetrofitClient;
import vn.hcmute.lab_9.Service.APIService;

public class FoodDetailActivity extends BaseActivity {

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
        super.setContentView(R.layout.activity_food_detail);
        initializeBottomBar();
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

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FoodDetailStore foodDetailStore = new FoodDetailStore(
                        food.getId(),
                        food.getMeal(),
                        food.getStrmealthumb(),
                        Integer.parseInt(quantity.getText().toString()),
                        Double.parseDouble(food.getPrice())
                );

                addToCart(foodDetailStore);
                Log.d("SQLite", "SQLite Insert: Inserted food to cart: " + foodDetailStore.getName());
            }
        });
    }

    public void addToCart(FoodDetailStore food) {
        // Kiểm tra xem sản phẩm đã tồn tại trong giỏ hàng hay chưa
        FoodDetailStore existingFood = findFoodInCart(food.getId());
        if (existingFood != null) {
            // Nếu sản phẩm đã tồn tại, tăng số lượng lên
            int currentQuantity = food.getQuantity();
            existingFood.setQuantity(existingFood.getQuantity() + currentQuantity);
            updateFoodQuantityInDatabase(existingFood);
        } else {
            // Nếu sản phẩm chưa tồn tại, thêm vào giỏ hàng
            insertFoodIntoDatabase(food);
        }
        Toast.makeText(this, "Thêm sản phẩm thành công!", Toast.LENGTH_SHORT).show();
    }

    private FoodDetailStore findFoodInCart(String foodId) {
        SQLiteDatabase db = new DatabaseHelper(this).getReadableDatabase();
        Cursor cursor = db.query("FoodStore", null, "id = ?", new String[]{foodId}, null, null, null);
        if (cursor.moveToFirst()) {
            // Tạo một đối tượng FoodDetailStore từ dữ liệu trong cơ sở dữ liệu
            FoodDetailStore food = new FoodDetailStore();
            if (cursor.getString(0) != null) {
                food.setId(cursor.getString(0));
                food.setName(cursor.getString(1));
                food.setImageUrl(cursor.getString(2));
                food.setQuantity(cursor.getInt(3));
                food.setPrice(cursor.getDouble(4));
            }
            cursor.close();
            db.close();
            return food;
        } else {
            cursor.close();
            db.close();
            return null;
        }
    }

    private void updateFoodQuantityInDatabase(FoodDetailStore food) {
        SQLiteDatabase db = new DatabaseHelper(this).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("quantity", food.getQuantity());
        db.update("FoodStore", values, "id = ?", new String[]{food.getId()});
        db.close();
    }

    private void insertFoodIntoDatabase(FoodDetailStore food) {
        SQLiteDatabase db = new DatabaseHelper(getApplicationContext()).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", food.getId());
        values.put("name", food.getName());
        values.put("imageUrl", food.getImageUrl());
        values.put("quantity", food.getQuantity());
        values.put("price", food.getPrice());
        db.insert("FoodStore", null, values);
        db.close();
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