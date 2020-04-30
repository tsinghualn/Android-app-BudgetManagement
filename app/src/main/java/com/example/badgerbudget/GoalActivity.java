package com.example.badgerbudget;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
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

import java.util.Calendar;
import java.util.Date;

public class GoalActivity extends AppCompatActivity {
    String passable;
    Client client = new Client(6868, "10.0.2.2");
    double cat1Budget;
    double cat2Budget;
    double cat3Budget;
    double cat4Budget;
    double foodBudget;
    double clothesBudget;
    double groceriesBudget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);
        Bundle un = getIntent().getExtras();
        System.out.println("Date: " + getMonth() + " " + getYear());
        String username = un.getString("username");
        passable = username;
        //Default
        TextView food = findViewById(R.id.foodTextView);
        TextView clothes = findViewById(R.id.clothesTextView);
        TextView groceries = findViewById(R.id.groceriesTextView);

        ProgressBar foodPB = findViewById(R.id.foodProgressBar);
        ProgressBar groceriesPB = findViewById(R.id.clothesProgressBar);
        ProgressBar clothesPB = findViewById(R.id.groceryProgressBar);

        ProgressBar cat1PB = findViewById(R.id.cat1ProgressBar);
        ProgressBar cat2PB = findViewById(R.id.cat2progressBar);
        ProgressBar cat3PB = findViewById(R.id.cat3progressBar);
        ProgressBar cat4PB = findViewById(R.id.cat4ProgressBar);

        //User Categories
        TextView cat1 = findViewById(R.id.cat1TextView);
        TextView cat2 = findViewById(R.id.cat2TextView);
        TextView cat3 = findViewById(R.id.cat3TextView);
        TextView cat4 = findViewById(R.id.cat4TextView);


        String catResponse = client.sendMessage("getcategories;" + passable);
        String[] categoriesMessage = catResponse.split(";");
        System.out.println(categoriesMessage.length);
        String[][] categories = new String[categoriesMessage.length][2];

        for (int i = 0; i < categoriesMessage.length; i++) {
            categories[i] = categoriesMessage[i].split(" ");
        }

        //Set the default categories for the user
        food.setText(categories[1][0]);
        foodBudget = Double.parseDouble(categories[1][1]);

        clothes.setText(categories[0][0]);
        clothesBudget = Double.parseDouble(categories[0][1]);

        groceries.setText(categories[2][0]);
        groceriesBudget = Double.parseDouble(categories[2][1]);


        if (categoriesMessage.length > 3) {
            cat1PB.setVisibility(View.VISIBLE);
            cat1.setText(categories[3][0]);
            cat1Budget = Double.parseDouble(categories[3][1]);


        } else {
            cat1.setText("");
            cat1Budget = 0;
            cat1PB.setVisibility(View.INVISIBLE);


        }
        if (categoriesMessage.length > 4) {
            cat2PB.setVisibility(View.VISIBLE);
            cat2.setText(categories[4][0]);
            cat2Budget = Double.parseDouble(categories[4][1]);

        } else {
            cat2.setText("");
            cat2Budget = 0;
            cat2PB.setVisibility(View.INVISIBLE);


        }
        if (categoriesMessage.length > 5) {
            cat3PB.setVisibility(View.VISIBLE);
            cat3.setText(categories[5][0]);
            cat3Budget = Double.parseDouble(categories[5][1]);

        } else {
            cat3.setText("");
            cat3Budget = 0;
            cat3PB.setVisibility(View.GONE);


        }
        if (categoriesMessage.length > 6) {
            cat4PB.setVisibility(View.VISIBLE);
            cat4.setText(categories[6][0]);
            cat4Budget = Double.parseDouble(categories[6][1]);

        } else {
            cat4.setText("");
            cat4Budget = 0;
            cat4PB.setVisibility(View.GONE);

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
                    if (transAmount[i][4].equals(getMonth()) && transAmount[i][5].equals(getYear())) {
                        double trans = Double.parseDouble(transAmount[i][2]);
                        foodTotal += trans;
                    }
                }
                if (transAmount[i][6].equals(clothes.getText().toString())) {
                    if (transAmount[i][4].equals(getMonth()) && transAmount[i][5].equals(getYear())) {
                        double trans = Double.parseDouble(transAmount[i][2]);
                        clothesTotal += trans;
                    }
                }
                if (transAmount[i][6].equals(groceries.getText().toString())) {
                    if (transAmount[i][4].equals(getMonth()) && transAmount[i][5].equals(getYear())) {
                        double trans = Double.parseDouble(transAmount[i][2]);
                        groceriesTotal += trans;
                    }
                }
                if (transAmount[i][6].equals(cat1.getText().toString())) {
                    if (transAmount[i][4].equals(getMonth()) && transAmount[i][5].equals(getYear())) {
                        double trans = Double.parseDouble(transAmount[i][2]);
                        cat1Total += trans;
                    }
                }
                if (transAmount[i][6].equals(cat2.getText().toString())) {
                    if (transAmount[i][4].equals(getMonth()) && transAmount[i][5].equals(getYear())) {
                        double trans = Double.parseDouble(transAmount[i][2]);
                        cat2Total += trans;
                    }
                }
                if (transAmount[i][6].equals(cat3.getText().toString())) {
                    if (transAmount[i][4].equals(getMonth()) && transAmount[i][5].equals(getYear())) {
                        double trans = Double.parseDouble(transAmount[i][2]);
                        cat3Total += trans;
                    }
                }
                if (transAmount[i][6].equals(cat4.getText().toString())) {
                    if (transAmount[i][4].equals(getMonth()) && transAmount[i][5].equals(getYear())) {
                        double trans = Double.parseDouble(transAmount[i][2]);
                        cat4Total += trans;
                    }
                }
            }
        }



        foodPB.setMax(100);
        double foodprogress = ( foodTotal / foodBudget ) * 100;
        foodPB.setProgress((int )foodprogress);
        TextView foodProg = findViewById(R.id.foodProgress);
        if (foodTotal >= 0) {
            foodProg.setText((String.valueOf((float) foodTotal) + "/" + String.valueOf((float) foodBudget)));
        } else {
            foodProg.setText(("0.0 /" + String.valueOf((float) foodBudget)));
        }
        if (((float)foodTotal / ((float) foodBudget)) > 1) {
            Context context =  getApplicationContext();
            LinearLayout layout = new LinearLayout(context);
            layout.setOrientation(LinearLayout.VERTICAL);
            TextView warning = new TextView(GoalActivity.this);
            warning.setText("Whoops. Looks Like You Overspent in Your Category: " + food.getText().toString() + ". Please Consider Changing Your Budgets Around!");
            warning.setGravity(Gravity.CENTER);
            AlertDialog.Builder warningView = new AlertDialog.Builder(GoalActivity.this);
            warningView.setTitle("WARNING");
            layout.addView(warning);
            warningView.setView(layout);
            warningView.setPositiveButton("Ok",null);
            AlertDialog dialog = warningView.create();
            dialog.show();
        }

        clothesPB.setMax(100);
        double clothesprogress = ((float) clothesTotal / clothesBudget ) * 100;
        clothesPB.setProgress((int )clothesprogress);
        TextView clothesProg = findViewById(R.id.clothesProgress);
        if (clothesTotal >= 0) {
            clothesProg.setText((String.valueOf((float) clothesTotal) + "/" + String.valueOf((float) clothesBudget)));
        } else {
            clothesProg.setText(("0.0 /" + String.valueOf((float) clothesBudget)));
        }
        if (((float)clothesTotal / ((float) clothesBudget)) > 1) {
            Context context =  getApplicationContext();
            LinearLayout layout = new LinearLayout(context);
            layout.setOrientation(LinearLayout.VERTICAL);
            TextView warning = new TextView(GoalActivity.this);
            warning.setText("Whoops. Looks Like You Overspent in Your Category: " + clothes.getText().toString() + ". Please Consider Changing Your Budgets Around!");
            warning.setGravity(Gravity.CENTER);
            AlertDialog.Builder warningView = new AlertDialog.Builder(GoalActivity.this);
            warningView.setTitle("WARNING");
            layout.addView(warning);
            warningView.setView(layout);
            warningView.setPositiveButton("Ok",null);
            AlertDialog dialog = warningView.create();
            dialog.show();
        }

        groceriesPB.setMax(100);
        double groceriesprogress = ((float) groceriesTotal / groceriesBudget ) * 100;
        groceriesPB.setProgress((int )groceriesprogress);
        TextView groceriesProg = findViewById(R.id.groceriesProgress);
        if (groceriesTotal >= 0) {
            groceriesProg.setText((String.valueOf((float) groceriesTotal) + "/" + String.valueOf((float) groceriesBudget)));
        } else {
            groceriesProg.setText(("0.0 /" + String.valueOf((float) groceriesBudget)));
        }
        if (((float)groceriesTotal / ((float) groceriesBudget)) > 1) {
            Context context =  getApplicationContext();
            LinearLayout layout = new LinearLayout(context);
            layout.setOrientation(LinearLayout.VERTICAL);
            TextView warning = new TextView(GoalActivity.this);
            warning.setText("Whoops. Looks Like You Overspent in Your Category: " + groceries.getText().toString() + ". Please Consider Changing Your Budgets Around!");
            warning.setGravity(Gravity.CENTER);
            AlertDialog.Builder warningView = new AlertDialog.Builder(GoalActivity.this);
            warningView.setTitle("WARNING");
            layout.addView(warning);
            warningView.setView(layout);
            warningView.setPositiveButton("Ok",null);
            AlertDialog dialog = warningView.create();
            dialog.show();
        }


        if (cat1PB.getVisibility() == View.VISIBLE) {
            cat1PB.setMax(100);
            double cat1progress = ((float) cat1Total / cat1Budget) * 100;
            cat1PB.setProgress((int) cat1progress);
            TextView cat1Prog = findViewById(R.id.cat1Progress);
            if (cat1Total >=0) {
                cat1Prog.setText((String.valueOf((float) cat1Total) + "/" + String.valueOf((float) cat1Budget)));
            } else {
                cat1Prog.setText(("0.0 /" + String.valueOf((float) cat1Budget)));
            }
            if (((float)cat1Total / ((float) cat1Budget)) > 1) {
                Context context =  getApplicationContext();
                LinearLayout layout = new LinearLayout(context);
                layout.setOrientation(LinearLayout.VERTICAL);
                TextView warning = new TextView(GoalActivity.this);
                warning.setText("Whoops. Looks Like You Overspent in Your Category: " + cat1.getText().toString() + ". Please Consider Changing Your Budgets Around!");
                warning.setGravity(Gravity.CENTER);
                String category = client.sendMessage("getcategories;" + passable);
                AlertDialog.Builder warningView = new AlertDialog.Builder(GoalActivity.this);
                warningView.setTitle("WARNING");
                layout.addView(warning);
                warningView.setView(layout);
                warningView.setPositiveButton("Ok",null);
                AlertDialog dialog = warningView.create();
                dialog.show();
            }

        } else {
            TextView cat1Prog = findViewById(R.id.cat1Progress);
            cat1Prog.setText("");
        }
        if (cat2PB.getVisibility() == View.VISIBLE) {
            cat2PB.setMax(100);
            double cat2progress = ((float) cat2Total / cat2Budget) * 100;
            cat2PB.setProgress((int) cat2progress);
            TextView cat2Prog = findViewById(R.id.cat2Progress);
            if (cat2Total >=0) {
                cat2Prog.setText((String.valueOf((float) cat2Total) + "/" + String.valueOf((float) cat2Budget)));
            } else {
                cat2Prog.setText(("0.0 /" + String.valueOf((float) cat2Budget)));
            }
            if (((float)cat2Total / ((float) cat2Budget)) > 1) {
                Context context =  getApplicationContext();
                LinearLayout layout = new LinearLayout(context);
                layout.setOrientation(LinearLayout.VERTICAL);
                TextView warning = new TextView(GoalActivity.this);
                warning.setText("Whoops. Looks Like You Overspent in Your Category: " + cat2.getText().toString() + ". Please Consider Changing Your Budgets Around!");
                warning.setGravity(Gravity.CENTER);
                AlertDialog.Builder warningView = new AlertDialog.Builder(GoalActivity.this);
                warningView.setTitle("WARNING");
                layout.addView(warning);
                warningView.setView(layout);
                warningView.setPositiveButton("Ok",null);
                AlertDialog dialog = warningView.create();
                dialog.show();
            }
        } else {
            TextView cat2Prog = findViewById(R.id.cat2Progress);
            cat2Prog.setText("");
        }

        if (cat3PB.getVisibility() == View.VISIBLE) {
            cat3PB.setMax(100);
            double cat3progress = ((float) cat3Total / cat3Budget) * 100;
            cat3PB.setProgress((int) cat3progress);
            TextView cat3Prog = findViewById(R.id.cat3progress);
            if (cat3Total >=0) {
                cat3Prog.setText((String.valueOf((float) cat3Total) + "/" + String.valueOf((float) cat3Budget)));
            } else {
                cat3Prog.setText(("0.0 /" + String.valueOf((float) cat3Budget)));
            }
            if (((float)cat3Total / ((float) cat3Budget)) > 1) {
                Context context =  getApplicationContext();
                LinearLayout layout = new LinearLayout(context);
                layout.setOrientation(LinearLayout.VERTICAL);
                TextView warning = new TextView(GoalActivity.this);
                warning.setText("Whoops. Looks Like You Overspent in Your Category: " + cat3.getText().toString() + ". Please Consider Changing Your Budgets Around!");
                warning.setGravity(Gravity.CENTER);
                AlertDialog.Builder warningView = new AlertDialog.Builder(GoalActivity.this);
                warningView.setTitle("WARNING");
                layout.addView(warning);
                warningView.setView(layout);
                warningView.setPositiveButton("Ok",null);
                AlertDialog dialog = warningView.create();
                dialog.show();
            }
        } else {
            TextView cat3Prog = findViewById(R.id.cat3progress);
            cat3Prog.setText("");
        }

        if (cat4PB.getVisibility() == View.VISIBLE) {
            cat4PB.setMax(100);
            double cat4progress = ((float) cat4Total / cat4Budget) * 100;
            cat4PB.setProgress((int) cat4progress);
            TextView cat4Prog = findViewById(R.id.cat4Progress);
            if (cat4Total >=0) {
                cat4Prog.setText((String.valueOf((float) cat4Total) + "/" + String.valueOf((float) cat4Budget)));
            } else {
                cat4Prog.setText(("0.0 /" + String.valueOf((float) cat4Budget)));

            }
            if (((float)cat4Total / ((float) cat4Budget)) > 1) {
                Context context =  getApplicationContext();
                LinearLayout layout = new LinearLayout(context);
                layout.setOrientation(LinearLayout.VERTICAL);
                TextView warning = new TextView(GoalActivity.this);
                warning.setText("Whoops. Looks Like You Overspent in Your Category: " + cat4.getText().toString() + ". Please Consider Changing Your Budgets Around!");
                warning.setGravity(Gravity.CENTER);
                AlertDialog.Builder warningView = new AlertDialog.Builder(GoalActivity.this);
                warningView.setTitle("WARNING");
                layout.addView(warning);
                warningView.setView(layout);
                warningView.setPositiveButton("Ok",null);
                AlertDialog dialog = warningView.create();
                dialog.show();
            }
        } else {
            TextView cat4Prog = findViewById(R.id.cat4Progress);
            cat4Prog.setText("");
        }
        navigation();
    }

    /**
     * Get year as a string to be used in the report page
     * @return
     */
    private String getYear() {
        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);

        int year = cal.get(Calendar.YEAR);
        String yearString = String.valueOf(year);
        return yearString;
    }

    /**
     * Get months as a string to be used in the report page
     * @return
     */
    private String getMonth(){
        String[] months = new String[]{"January", "February", "March", "April",
                "May", "June", "July", "August", "September", "October",
                "November", "December"};
        String monthString;
        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);

        int month = cal.get(Calendar.MONTH);
        if (month == 0) {
            monthString = months[0];
        } else if (month == 1) {
            monthString = months[1];
        } else if (month == 2) {
            monthString = months[2];
        } else if (month == 3) {
            monthString = months[3];
        } else if (month == 4) {
            monthString = months[4];
        } else if (month == 5) {
            monthString = months[5];
        } else if (month == 6) {
            monthString = months[6];
        } else if (month == 7) {
            monthString = months[7];
        } else if (month == 8) {
            monthString = months[8];
        } else if (month == 9) {
            monthString = months[9];
        } else if (month == 10) {
            monthString = months[10];
        } else if (month == 11) {
            monthString = months[11];
        } else {
            return "Unable to retrieve month";
        }

        return monthString;
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

