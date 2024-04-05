package vn.hcmute.lab_9.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.hcmute.lab_9.Adapter.CategoryAdapter;
import vn.hcmute.lab_9.Model.Category;
import vn.hcmute.lab_9.R;
import vn.hcmute.lab_9.RecyclerView.Decoration.SpacesItemDecoration;
import vn.hcmute.lab_9.RetrofitClient;
import vn.hcmute.lab_9.Service.APIService;

public class MainActivity extends AppCompatActivity {

    RecyclerView rcCate;
    CategoryAdapter categoryAdapter;
    APIService apiService;
    List<Category> categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mappingView();
        categoryList = new ArrayList<>();

        rcCate.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rcCate.setLayoutManager(layoutManager);
        int spaceInPixels = getResources().getDimensionPixelSize(R.dimen.margin_15);
        rcCate.addItemDecoration(new SpacesItemDecoration(spaceInPixels));

        GetCategory();
    }

    private void GetCategory() {
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getCategoryAll().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful()) {
                    categoryList = response.body();
                    categoryAdapter = new CategoryAdapter(MainActivity.this, categoryList);
                    rcCate.setAdapter(categoryAdapter);
                    categoryAdapter.notifyDataSetChanged();
                } else {
                    int statusCode = response.code();
                }
            }
            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }

    private void mappingView() {
        rcCate = findViewById(R.id.recyclerView);
    }
}