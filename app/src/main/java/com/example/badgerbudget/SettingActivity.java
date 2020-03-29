package com.example.badgerbudget;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        final Button profileButton = (Button) findViewById(R.id.profileButton);
        Button downloadButton = (Button) findViewById(R.id.downloadButton);
        Button notificationButton = (Button) findViewById(R.id.notificationButton);
        Button userGuideButton = (Button) findViewById(R.id.userGuideButton);
        Button appInfoButton = (Button) findViewById(R.id.appInfoButton);

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileIntent = new Intent(SettingActivity.this, ProfileSettingActivity.class);
                SettingActivity.this.startActivity(profileIntent);
            }
        });


    }
}
