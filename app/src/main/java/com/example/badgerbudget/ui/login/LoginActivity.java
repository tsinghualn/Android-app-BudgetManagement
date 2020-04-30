package com.example.badgerbudget.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.badgerbudget.CreateAccountActivity;
import com.example.badgerbudget.ForgotPassword;
import com.example.badgerbudget.data.model.*;
import com.example.badgerbudget.MainPageActivity;
import com.example.badgerbudget.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final Button forgotPassword = findViewById(R.id.forgotPwButton);


        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_DONE) {
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 loadingProgressBar.setVisibility(View.VISIBLE);
                 loginViewModel.login(usernameEditText.getText().toString(),
                 passwordEditText.getText().toString());
                 */
                //Start client instance here
                //get username and password from text boxes
                //make query to see if there's a username and password account combo from the database
                //if we have one, we switch over to home page
                //else, we say that there is no such account created in the database.
                String u = usernameEditText.getText().toString();
                String p = passwordEditText.getText().toString();
                if (u.length() > 0 && p.length() > 0) {
                    Client client = new Client(6868, "10.0.2.2");
                    String loginAttempt = client.sendMessage("login;" + u + " " + p);
                    if (loginAttempt.equals("true")) {
                        switchToMainPage(v, u);
                    } else {
                        //we have not found success with the login, so we are going to just return a failed login
                        String failedLogin = "Invalid Username/Password";
                        Toast.makeText(getApplicationContext(), failedLogin, Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    String message = "Please enter a username and password!";
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                forgotPassword(v,username);
            }
        });
    }


    public void createAccount(View view){
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
        Intent returnIntent = getIntent();
    }

    public void switchToMainPage(View view, String username){
        Intent intent = new Intent(this, MainPageActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }
    public void forgotPassword(View view, String username){
        Intent intent = new Intent(this, ForgotPassword.class);
        startActivity(intent);
    }
}
