package com.example.badgerbudget;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.badgerbudget.data.model.Client;
import com.example.badgerbudget.ui.login.LoginActivity;

import static com.example.badgerbudget.R.*;

public class ProfileSettingActivity extends AppCompatActivity {
    String passable;
    String name, birthday, password, securityquestion1, securityquestion2, securityquestion3;
    EditText nameText, birthdayText, a1Text, a2Text, a3Text, currentPasswordText, newPasswordText;
    Button saveButton;
    Client client = new Client(6868, "10.0.2.2");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle un = getIntent().getExtras();
        System.out.println("Bundle that shouldn't be null: " + un);
        String username = un.getString("username");
        passable = username;
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_profile_setting);

        nameText = (EditText) findViewById(id.nameText);
        birthdayText = (EditText) findViewById(id.birthdayText);



        a1Text = (EditText) findViewById(id.a1Text);
        a2Text = (EditText) findViewById(id.a2Text);
        a3Text = (EditText) findViewById(id.a3Text);
        currentPasswordText = (EditText) findViewById(id.currentPasswordText);
        newPasswordText = (EditText) findViewById(id.newpasswordText);

        saveButton = (Button) findViewById(id.saveButton);
        String response = client.sendMessage("getuserinfo;" + passable);
        System.out.println("Response: " + response);
        String[] userInfo = response.split(" ");
        nameText.setText(userInfo[6]);
        System.out.println("nameText: " + nameText.getText().toString());
        birthdayText.setText(userInfo[5]);
        System.out.println("birthday: " + birthdayText.getText().toString());
        a1Text.setText(userInfo[2]);
        System.out.println("answer1: " + a1Text.getText().toString());
        a2Text.setText(userInfo[3]);
        System.out.println("answer2: " + a2Text.getText().toString());
        a3Text.setText(userInfo[4]);
        // save button
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if new password is not entered
                if (newPasswordText.getText().toString().equals("")){
                    // if both new name and birthday is not entered
                    if(nameText.getText().toString().equals(name) && birthdayText.getText().toString().equals(birthday)){
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
                                .create();
//                                .show();
                        String response = client.sendMessage("changeuserinfo;" + passable + " " +
                                nameText.getText() + " " + a1Text.getText() + " " +
                                a2Text.getText() + " " + a3Text.getText() + " " +
                                birthdayText.getText());
                        if (response != null) {
                            finish();
                            startActivity(getIntent());
                        }
                    }

                }
                else{
                    // if password entered match

                    if (!newPasswordText.getText().toString().equals("") && !currentPasswordText.getText().toString().equals("")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileSettingActivity.this);
                        builder.setMessage("updated")
                                .setPositiveButton("okay", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                        startActivity(getIntent());
                                    }
                                })
                                .create();
//                                .show();
                        String message = client.sendMessage("passwordcheck;" + passable + " " + currentPasswordText.getText().toString());
                        if (message.equals("true")) {
                            String response = client.sendMessage("changepassword;" + passable + " " + newPasswordText.getText().toString());
                            if (response.equals("Password Changed Successfully")) {
                                toastMessage("Password Has Been Changed");
                                finish();
                                startActivity(getIntent());
                            }
                        } else {
                            toastMessage("Current Password Invalid");
                        }
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

    private void toastMessage(String message) {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();

    }
}
