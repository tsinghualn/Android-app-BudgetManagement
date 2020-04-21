package com.example.badgerbudget;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static com.example.badgerbudget.R.*;

public class ProfileSettingActivity extends AppCompatActivity {

    String name, birthday, password, securityquestion1, securityquestion2, securityquestion3;
    EditText nameText, birthdayText, a1Text, a2Text, a3Text, currentPasswordText, newPasswordText;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_profile_setting);

        nameText = (EditText) findViewById(id.nameText);
        birthdayText = (EditText) findViewById(id.birthdayText);

        // todo: get user data from db

        /*
        Bundle un = getIntent().getExtras();
        name = un.getString("name");
        nameText.setText(name, TextView.BufferType.EDITABLE);

        birthday = un.getString("birthday");
        birthdayText.setText(birthday, TextView.BufferType.EDITABLE);

        password = un.getString("password");
        securityquestion1 = un.getString("securityquestion1");
        securityquestion2 = un.getString("securityquestion2");
        securityquestion3 = un.getString("securityquestion3");
        */

        a1Text = (EditText) findViewById(id.a1Text);
        a2Text = (EditText) findViewById(id.a2Text);
        a3Text = (EditText) findViewById(id.a3Text);
        currentPasswordText = (EditText) findViewById(id.currentPasswordText);
        newPasswordText = (EditText) findViewById(id.newpasswordText);

        saveButton = (Button) findViewById(id.saveButton);

        // save button
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if new password is not entered
                if (newPasswordText.getText().toString().equals("")){
                    // if both new name and birthday is not entered
                    if(nameText.getText().toString().equals("") && birthdayText.getText().toString().equals("")){
                        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileSettingActivity.this);
                        builder.setMessage("Nothing to update")
                                .setPositiveButton("okay", null)
                                .create()
                                .show();
                    }
                    // if only new name is entered
                    else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileSettingActivity.this);
                        builder.setMessage("updated")
                                .setPositiveButton("okay", null)
                                .create()
                                .show();
                        // todo: update name and birthday on DB
                    }

                }
                else{
                    // if password entered match
                    if (currentPasswordText.getText().toString().equals(password)){
                        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileSettingActivity.this);
                        builder.setMessage("updated")
                                .setPositiveButton("okay", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                        startActivity(getIntent());
                                    }
                                })
                                .create()
                                .show();
                        // todo: update data
                    }
                    // if all security answers match
                    else if (a1Text.getText().toString().equals(securityquestion1)
                    && a2Text.getText().toString().equals(securityquestion2)
                    && a3Text.getText().toString().equals(securityquestion3)){
                        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileSettingActivity.this);
                        builder.setMessage("updated")
                                .setPositiveButton("okay", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                        startActivity(getIntent());
                                    }
                                })
                                .create()
                                .show();
                        // todo: update data
                    }
                    else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileSettingActivity.this);
                        builder.setMessage("check password or answers for security question")
                                .setPositiveButton("okay", null)
                                .create()
                                .show();
                    }
                }
            }
        });
    }
}
