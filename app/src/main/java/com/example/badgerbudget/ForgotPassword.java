package com.example.badgerbudget;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.badgerbudget.data.model.Client;
import com.example.badgerbudget.ui.login.LoginActivity;

public class ForgotPassword extends AppCompatActivity {
    Button reset;
    EditText username;
    EditText answer1;
    EditText answer2;
    EditText newPassword;
    TextView question1;
    TextView question2;
    Client client = new Client(6868, "10.0.2.2");
    String expected1;
    String actual1;
    String expected2;
    String actual2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        reset = findViewById(R.id.resetBtn);
        username = findViewById(R.id.userNameeditText);
        answer1 = findViewById(R.id.answer1editText);
        answer2 = findViewById(R.id.answer2editText);
        newPassword = findViewById(R.id.newPasseditText);
        question1 = findViewById(R.id.sq1TextView);
        question2 = findViewById(R.id.sq2TextView);

        question1.setText("What is Your Favorite Color?");
        question2.setText("What is Your Mom's Name?");

        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                String user = username.getText().toString();
                if (s.length() > 0) {
                    String response = client.sendMessage("getquestions;" + user);
                    if (!response.equals("")) {
                        String[] parsed = response.split(";");
                        expected1 = parsed[0];
                        expected2 = parsed[1];
                    }

                }
            }
        });
        answer1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                actual1 = String.valueOf(s);
            }
        });
        answer2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                actual2 = String.valueOf(s);
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!newPassword.getText().toString().equals("") && expected1.equals(actual1) && expected2.equals(actual2)) {
                    if (newPassword.getText().toString().length() >5) {
                        String answer = client.sendMessage("changepassword;" + username.getText().toString() + " " + newPassword.getText().toString());
                        if (answer.equals("Password Changed Successfully")) {
                            toastMessage("Password Reset");
                            switchToLoginPage(v);
                        }
                    } else {
                        toastMessage("Password Must Be More Than 5 Characters");

                    }
                } else {
                    toastMessage("Answers Don't Match");
                }
            }
        });
    }
    private void toastMessage(String message) {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();

    }
    public void switchToLoginPage(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
