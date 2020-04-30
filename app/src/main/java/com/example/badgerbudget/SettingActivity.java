package com.example.badgerbudget;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.badgerbudget.ui.login.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SettingActivity extends AppCompatActivity {
    String passable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Bundle un = getIntent().getExtras();
        String username = un.getString("username");
        passable = username;
        navigation();


        final Button profileButton = (Button) findViewById(R.id.profileButton);
        Button calculatorBtn = (Button) findViewById(R.id.calculatorButton);
        Button logoutBtn = (Button) findViewById(R.id.logoutBtn);
        Button userGuideButton = (Button) findViewById(R.id.userGuideButton);
        Button appInfoButton = (Button) findViewById(R.id.appInfoButton);

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileIntent = new Intent(SettingActivity.this, ProfileSettingActivity.class);
                profileIntent.putExtra("username", passable);
                SettingActivity.this.startActivity(profileIntent);
            }
        });

        userGuideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent guideIntent = new Intent(SettingActivity.this, GuideActivity.class);
                SettingActivity.this.startActivity(guideIntent);
            }
        });

        appInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent appInfoIntent = new Intent(SettingActivity.this, AppInfoActivity.class);
                SettingActivity.this.startActivity(appInfoIntent);
            }
        });

        calculatorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent calcIntent = new Intent(SettingActivity.this, Calculator.class);
                SettingActivity.this.startActivity(calcIntent);
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(SettingActivity.this, LoginActivity.class);
                SettingActivity.this.startActivity(loginIntent);
            }
        });


    }

    private void navigation(){

        // navigation bar
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        Intent a = new Intent(SettingActivity.this, MainPageActivity.class);
                        a.putExtra("username", passable);

                        startActivity(a);
                        break;
                    case R.id.nav_report:
                        Intent b = new Intent(SettingActivity.this, Report.class);
                        b.putExtra("username", passable);
                        startActivity(b);
                        break;
                    case R.id.nav_setting:
                        //Intent c = new Intent(SettingActivity.this,SettingActivity.class);
                        //startActivity(c);
                        break;
                    case R.id.nav_goal:
                         Intent d = new Intent(SettingActivity.this, GoalActivity.class);
                         d.putExtra("username", passable);
                         startActivity(d);
                        break;
                    case R.id.nav_category:
                        Intent e = new Intent(SettingActivity.this, CategoryPageActivity.class);
                        e.putExtra("username", passable);
                        startActivity(e);
                        break;
                }
                return false;
            }
        });
    }


    public void onClick_calc(View v){
        Intent calc = new Intent(SettingActivity.this, Calculator.class);
        startActivity(calc);
    }
}
