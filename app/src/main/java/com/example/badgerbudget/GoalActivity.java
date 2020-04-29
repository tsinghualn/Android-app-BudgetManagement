package com.example.badgerbudget;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.badgerbudget.data.model.Client;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class GoalActivity extends AppCompatActivity {
    String passable;
    Client client = new Client(6868, "10.0.2.2");
    int cat1Budget;
    int cat2Budget;
    int cat3Budget;
    int cat4Budget;
    int foodBudget;
    int clothesBudget;
    int groceriesBudget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);
        Bundle un = getIntent().getExtras();
        String username = un.getString("username");
        passable = username;
        //Default
        TextView food = findViewById(R.id.foodTextView);
        TextView clothes = findViewById(R.id.clothesTextView);
        TextView groceries = findViewById(R.id.groceriesTextView);



        //User Categories
        TextView cat1 = findViewById(R.id.cat1TextView);
        TextView cat2 = findViewById(R.id.cat2TextView);
        TextView cat3 = findViewById(R.id.cat3TextView);
        TextView cat4 = findViewById(R.id.cat4TextView);

        String catResponse = client.sendMessage("getcategories;" + passable);
        String[] categoriesMessage = catResponse.split(";");
        System.out.println(categoriesMessage[0]);
        String[][] categories = new String[categoriesMessage.length][2];

        for (int i = 0; i < categoriesMessage.length; i++) {
            categories[i] = categoriesMessage[i].split(" ");
        }

        //Set the default categories for the user
        food.setText(categories[1][0]);
        foodBudget = Integer.parseInt(categories[1][1]);

        clothes.setText(categories[0][0]);
        clothesBudget = Integer.parseInt(categories[0][1]);

        groceries.setText(categories[2][0]);
        groceriesBudget = Integer.parseInt(categories[2][1]);


        if (categoriesMessage.length > 3) {
            cat1.setText(categories[3][0]);
            cat1Budget = Integer.parseInt(categories[3][1]);


        } else {
            cat1.setText("");
            cat1Budget = 0;
            cat1.setVisibility(View.INVISIBLE);


        }
        if (categoriesMessage.length > 4) {
            cat2.setText(categories[4][0]);
            cat2Budget = Integer.parseInt(categories[4][1]);

        } else {
            cat2.setText("");
            cat2Budget = 0;
            cat2.setVisibility(View.INVISIBLE);


        }
        if (categoriesMessage.length > 5) {
            cat3.setText(categories[5][0]);
            cat3Budget = Integer.parseInt(categories[5][1]);

        } else {
            cat3.setText("");
            cat3Budget = 0;
            cat3.setVisibility(View.INVISIBLE);


        }
        if (categoriesMessage.length > 6) {
            cat4.setText(categories[6][0]);
            cat4Budget = Integer.parseInt(categories[6][1]);

        } else {
            cat4.setText("Category");
            cat4Budget = 0;
            cat4.setVisibility(View.INVISIBLE);

        }
        String response = client.sendMessage("gettransactions;" + passable);
        String[] transactions = response.split(";");

        String[][] transAmount = new String[transactions.length][5];
        for (int i = 0; i < transactions.length; i++) {
            transAmount[i] = transactions[i].split(" ");
        }
        int foodTotal = 0;
        int clothesTotal = 0;
        int groceriesTotal = 0;
        int cat1Total = 0;
        int cat2Total = 0;
        int cat3Total = 0;
        int cat4Total = 0;
        for (int i = 0; i < transactions.length; i++) {
            if (!response.equals("")) {
                if (transAmount[i][6].equals(food.getText().toString())) {
                    int trans = Integer.parseInt(transAmount[i][2]);
                    foodTotal += trans;
                }
                if (transAmount[i][6].equals(clothes.getText().toString())) {
                    int trans = Integer.parseInt(transAmount[i][2]);
                    clothesTotal += trans;
                }
                if (transAmount[i][6].equals(groceries.getText().toString())) {
                    int trans = Integer.parseInt(transAmount[i][2]);
                    groceriesTotal += trans;
                }
                if (transAmount[i][6].equals(cat1.getText().toString())) {
                    int trans = Integer.parseInt(transAmount[i][2]);
                    cat1Total += trans;
                }
                if (transAmount[i][6].equals(cat2.getText().toString())) {
                    int trans = Integer.parseInt(transAmount[i][2]);
                    cat2Total += trans;
                }
                if (transAmount[i][6].equals(cat3.getText().toString())) {
                    int trans = Integer.parseInt(transAmount[i][2]);
                    cat3Total += trans;
                }
                if (transAmount[i][6].equals(cat4.getText().toString())) {
                    int trans = Integer.parseInt(transAmount[i][2]);
                    cat4Total += trans;
                }
            }
        }

        ProgressBar foodPB = findViewById(R.id.foodProgressBar);
        ProgressBar groceriesPB = findViewById(R.id.clothesProgressBar);
        ProgressBar clothesPB = findViewById(R.id.groceryProgressBar);

        foodPB.setMax(100);
        float foodprogress = ((float) foodTotal / foodBudget ) * 100;
        foodPB.setProgress((int )foodprogress);
        TextView foodProg = findViewById(R.id.foodProgress);
        foodProg.setText((String.valueOf((float) foodTotal) + "/" +String.valueOf((float)foodBudget)));

        clothesPB.setMax(100);
        float clothesprogress = ((float) clothesTotal / clothesBudget ) * 100;
        clothesPB.setProgress((int )clothesprogress);
        TextView clothesProg = findViewById(R.id.clothesProgress);
        clothesProg.setText((String.valueOf((float) clothesTotal) + "/" +String.valueOf((float)clothesBudget)));

        groceriesPB.setMax(100);
        float groceriesprogress = ((float) groceriesTotal / groceriesBudget ) * 100;
        groceriesPB.setProgress((int )groceriesprogress);
        TextView groceriesProg = findViewById(R.id.groceriesProgress);
        groceriesProg.setText((String.valueOf((float) groceriesTotal) + "/" +String.valueOf((float)groceriesBudget)));


        ProgressBar cat1PB = findViewById(R.id.cat1ProgressBar);
        cat1PB.setMax(100);
        float cat1progress = ((float) cat1Total / cat1Budget ) * 100;
        cat1PB.setProgress((int )cat1progress);
        TextView cat1Prog = findViewById(R.id.cat1Progress);
        cat1Prog.setText((String.valueOf((float) cat1Total) + "/" +String.valueOf((float)cat1Budget)));


        ProgressBar cat2PB = findViewById(R.id.cat2progressBar);
        cat2PB.setMax(100);
        float cat2progress = ((float) cat2Total / cat2Budget ) * 100;
        cat2PB.setProgress((int) cat2progress);
        TextView cat2Prog = findViewById(R.id.cat2Progress);
        cat2Prog.setText((String.valueOf((float) cat2Total) + "/" +String.valueOf((float)cat2Budget)));


        ProgressBar cat3PB = findViewById(R.id.cat3progressBar);
        cat3PB.setMax(100);
        float cat3progress = ((float) cat3Total / cat3Budget ) * 100;
        cat3PB.setProgress((int) cat3progress);
        TextView cat3Prog = findViewById(R.id.cat3progress);
        cat3Prog.setText((String.valueOf((float) cat3Total) + "/" +String.valueOf((float)cat3Budget)));


        ProgressBar cat4PB = findViewById(R.id.cat4ProgressBar);
        cat4PB.setMax(100);
        float cat4progress = ((float) cat4Total / cat4Budget ) * 100;
        cat4PB.setProgress((int) cat4progress);
        TextView cat4Prog = findViewById(R.id.cat4Progress);
        cat4Prog.setText((String.valueOf((float) cat4Total) + "/" +String.valueOf((float)cat4Budget)));

        navigation();
    }



    private void navigation() {
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        Intent a = new Intent(GoalActivity.this, MainPageActivity.class);
                        a.putExtra("username", passable);
                        startActivity(a);
                        break;
                    case R.id.nav_report:
                        Intent b = new Intent(GoalActivity.this, Report.class);
                        b.putExtra("username", passable);
                        startActivity(b);
                        break;
                    case R.id.nav_setting:
                        Intent c = new Intent(GoalActivity.this, SettingActivity.class);
                        c.putExtra("username", passable);
                        startActivity(c);
                        break;
                    case R.id.nav_goal:
                        break;
                    case R.id.nav_category:
                        Intent e = new Intent(GoalActivity.this, CategoryPageActivity.class);
                        e.putExtra("username", passable);
                        startActivity(e);
                        break;
                }
                return false;
            }
        });
    }

}

