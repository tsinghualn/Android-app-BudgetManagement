package com.example.badgerbudget;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

//import com.example.badgerbudget.data.model.Client;

public class GoalsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_goals);
        //Client client = new Client(5000, "localhost");
        //send message from client to server for eacg goals max spending and current spending
        //ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        //ProgressBar progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);
        TextView textView = (TextView) findViewById(R.id.textView);
        TextView textView2 = (TextView) findViewById(R.id.textView2);
        TextView textView3 = (TextView) findViewById(R.id.textView3);
        TextView textView4 = (TextView) findViewById(R.id.textView4);
        TextView textView5 = (TextView) findViewById(R.id.textView5);
        textView3.setText("Goal 1");
        textView.setText("$50.65 / $75.00");
       // progressBar.setProgress(50/75);
        textView4.setText("Set a New Goal!");
        //progressBar.setProgress(0);
        textView5.setText("Budget Goals");


    }

}
