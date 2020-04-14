package com.example.badgerbudget;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.badgerbudget.ui.login.LoginActivity;
import com.example.badgerbudget.R;

public class CreateAccountActivity extends AppCompatActivity {

    EditText usernameBox;
    EditText passwordBox ;
    EditText nameBox;
    EditText birthday;
    TextView s1q;
    TextView s2q;
    TextView s3q;
    EditText s1a;
    EditText s2a;
    EditText s3a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        usernameBox = (EditText) findViewById(R.id.usernameBox);
        passwordBox = (EditText) findViewById(R.id.passwordBox);
        nameBox = (EditText) findViewById(R.id.nameBox);
        birthday = (EditText) findViewById(R.id.birthdayBox);
        s1q = (EditText) findViewById(R.id.s1QuestionText);
        s2q = (EditText) findViewById(R.id.s2QuestionText);
        s3q = (EditText) findViewById(R.id.s3QuestionText);
        s1a = (EditText) findViewById(R.id.s1AnswerText);
        s2a = (EditText) findViewById(R.id.s2answerTest);
        s3a = (EditText) findViewById(R.id.s3AnswerText);
    }

    public void createAccount(View view){
        if(usernameBox.length() == 0 || passwordBox.length() == 0 || nameBox.length() == 0 || birthday.length() == 0 || s1q.length() == 0 || s2q.length() == 0 || s2a.length() == 0 || s3q.length()== 0 || s3a.length() ==0)
        {
            Toast.makeText(getApplicationContext(), "All fields must be filled before creating account!", Toast.LENGTH_LONG).show();
        }

    }
}
