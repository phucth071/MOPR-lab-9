package vn.hcmute.lab_9.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.hcmute.lab_9.Adapter.CartAdapter;
import vn.hcmute.lab_9.DatabaseHelper;
import vn.hcmute.lab_9.Model.FoodDetailStore;
import vn.hcmute.lab_9.R;
import vn.hcmute.lab_9.RecyclerView.Decoration.SpacesItemDecoration;

public class CartDetailActivity extends BaseActivity {

    CartAdapter cartAdapter;
    List<FoodDetailStore> foodDetailStoreList;
    RecyclerView rcCart;
    TextView txtTotalItems, txtTotalPrice, txtTotalPriceOne;
    ImageView btnMinus, btnPlus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_detail);

        initializeBottomBar();
        int selectedMenuItemId = getIntent().getIntExtra("SELECTED_MENU_ITEM_ID", R.id.home);
        bottomNavigationView.getMenu().findItem(selectedMenuItemId).setChecked(true);

        MappingView();
        GetCartDetail();

        rcCart.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rcCart.setLayoutManager(layoutManager);
        int spaceInPixels = getResources().getDimensionPixelSize(R.dimen.margin_15);
        rcCart.addItemDecoration(new SpacesItemDecoration(spaceInPixels));
        cartAdapter = new CartAdapter(this, foodDetailStoreList);
        cartAdapter.notifyDataSetChanged();
        rcCart.setAdapter(cartAdapter);

        calculateTotalPrice();

    }


    private void MappingView() {
        rcCart = findViewById(R.id.recyclerViewCartDetail);
        txtTotalItems = findViewById(R.id.cart_detail_total_items_num);
        txtTotalPrice = findViewById(R.id.cart_detail_total_price_num);
        txtTotalPriceOne = findViewById(R.id.food_one_total_price);
        btnMinus = findViewById(R.id.btn_cart_minus);
        btnPlus = findViewById(R.id.btn_cart_plus);
    }

    private void GetCartDetail() {
        foodDetailStoreList = new ArrayList<>();

        SQLiteDatabase db = new DatabaseHelper(this).getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM FoodStore", null);

        FoodDetailStore foodDetailStore;

        if (cursor.moveToFirst()) {
            do {
                foodDetailStore = new FoodDetailStore();
                foodDetailStore.setId(cursor.getString(0));
                foodDetailStore.setName(cursor.getString(1));
                foodDetailStore.setImageUrl(cursor.getString(2));
                foodDetailStore.setQuantity(cursor.getInt(3));
                foodDetailStore.setPrice(cursor.getDouble(4));
                foodDetailStoreList.add(foodDetailStore);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
    }

    private void calculateTotalPrice() {
        double totalPrice = 0;
        int totalItems = 0;
        for (FoodDetailStore food : foodDetailStoreList) {
            totalPrice += food.getPrice() * food.getQuantity();
            totalItems += food.getQuantity();
        }
        txtTotalPrice.setText(String.format("$%s", totalPrice));
        txtTotalItems.setText(String.valueOf(totalItems));
    }
}