package com.example.badgerbudget;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Toast;

public class CategoryPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_page);
        //we have to query the database to find all teh categoreis that already exist
        //use the client to do so, and then fill in the table.
    }

    public void addCategory(View view){
        //check to make sure category doesn't already exist
        //if the category already does exist in the database
        //Toast.makeText(getApplicationContext(),"Category already exists!", Toast.LENGTH_LONG).show();
    }

    public void deleteCategory(View view){

    }
}
