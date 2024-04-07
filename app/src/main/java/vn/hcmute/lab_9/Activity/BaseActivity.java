package vn.hcmute.lab_9.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import vn.hcmute.lab_9.R;

public class BaseActivity extends AppCompatActivity {

    public BottomNavigationView bottomNavigationView;


    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.activity_base);
        FrameLayout container = findViewById(R.id.frame_layout);
        getLayoutInflater().inflate(layoutResID, container, true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        Log.d("Activity", "BaseActivity: onCreate: started.");

    }

    protected void initializeBottomBar() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setClickable(true);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == bottomNavigationView.getSelectedItemId()) {
                    return true;
                }
                Log.d("Navbar", "onNavigationItemSelected: ID = " + id );
                if (id == R.id.home) {
                    Intent intent = new Intent(BaseActivity.this , MainActivity.class);
                    intent.putExtra("SELECTED_MENU_ITEM_ID", id);
                    startActivity(intent);
                } else if (id == R.id.profile) {
                    // Handle profile click
                } else if (id == R.id.cart) {
                    Intent intent = new Intent(BaseActivity.this , CartDetailActivity.class);
                    intent.putExtra("SELECTED_MENU_ITEM_ID", id);
                    startActivity(intent);
                } else if (id == R.id.help) {
                    // Handle help click
                } else if (id == R.id.setting) {
                    // Handle setting click
                }

                return true;
            }

        });
    }
}