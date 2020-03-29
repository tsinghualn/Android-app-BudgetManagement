package com.example.badgerbudget;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import static com.example.badgerbudget.R.*;

public class ProfileSettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_profile_setting);

        EditText firstNameText = (EditText) findViewById(id.firstNameText);
        EditText lastNameText = (EditText) findViewById(id.lastNameText);
        EditText birthdayText = (EditText) findViewById(id.birthdayText);
        EditText phoneText = (EditText) findViewById(id.phoneText);
        EditText emailText = (EditText) findViewById(id.emailText);
        EditText address1Text = (EditText) findViewById(id.address1Text);
        EditText cityText = (EditText) findViewById(id.cityText);
        EditText stateText = (EditText) findViewById(id.stateText);
        EditText zipcodeText = (EditText) findViewById(id.zipcodeText);

        Button saveButton = (Button) findViewById(id.saveButton);

    }
}
