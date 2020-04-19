package com.example.badgerbudget;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.badgerbudget.data.model.Client;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CategoryPageActivity extends AppCompatActivity {
    TableLayout categoryTable;
    TextView salary;
    TextView category1;
    Button addCategory;
    Button deleteCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_page);

        salary = findViewById(R.id.curSalaryTextView);
        categoryTable = findViewById(R.id.categoryTable);
        category1 = findViewById(R.id.FirstCategory);
        addCategory = findViewById(R.id.addCategoryBtn);
        deleteCategory = findViewById(R.id.deleteCateogryBtn);

        navigation();

        //Make new client instance to get the user categories from the database
        Client client = new Client(6868, "10.0.2.2");
        String result = client.sendMessage("getcategories;andy_boho");
        String[] parsed = result.split(";");
        salary.setText("$10000000");
        category1.setText(parsed[0]);
        addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                //Take to the delete category page.
                toastMessage("Add Category Clicked");

            }
        });

        deleteCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                //Take to the delete category page.
                toastMessage("Delete Category Clicked");

            }
        });
    }

    private void toastMessage(String message) {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();

    }

    private void navigation() {

        // navigation bar
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        Intent a = new Intent(CategoryPageActivity.this, MainPageActivity.class);
                        startActivity(a);
                        break;
                    case R.id.nav_report:
                        Intent b = new Intent(CategoryPageActivity.this, report.class);
                        startActivity(b);
                        break;
                    case R.id.nav_setting:
                        Intent c = new Intent(CategoryPageActivity.this,SettingActivity.class);
                        startActivity(c);
                        break;
                    case R.id.nav_goal:
                        // Intent d = new Intent(MainPageActivity.this, GoalsActivity.class);
                        // startActivity(d)
                        break;
                    case R.id.nav_category:
                        //Intent e = new Intent(CategoryPageActivity.this, CategoryPageActivity.class);
                        //startActivity(e);
                        break;
                }
                return false;
            }
        });

    }
}
