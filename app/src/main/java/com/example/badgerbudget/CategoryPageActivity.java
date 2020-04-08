package com.example.badgerbudget;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CategoryPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_page);


        // navigation bar
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        Intent a = new Intent(CategoryPageActivity.this, MainPageActivity.class);
                        startActivity(a);
                        break;
                    case R.id.nav_report:
                        Intent b = new Intent(CategoryPageActivity.this, report.class);
                        startActivity(b);
                        break;
                    case R.id.nav_setting:
                        Intent c = new Intent(CategoryPageActivity.this,SettingActivity.class);
                        startActivity(c);
                        break;
                    case R.id.nav_goal:
                        // Intent d = new Intent(MainPageActivity.this, GoalsActivity.class);
                        // startActivity(d)
                        break;
                    case R.id.nav_category:
                        //Intent e = new Intent(CategoryPageActivity.this, CategoryPageActivity.class);
                        //startActivity(e);
                        break;
                }
                return false;
            }
        });
    }
}
