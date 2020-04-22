package com.example.badgerbudget;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.badgerbudget.data.model.Client;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;

public class MainPageActivity extends AppCompatActivity {
    Client client = new Client(6868, "10.0.2.2");
    String passable;
    String transCat;
    String transType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        Bundle un = getIntent().getExtras();
        String username = un.getString("username");
        passable = username;
        System.out.println("passable in main: " + passable);
        TextView currentBalanceText = (TextView) findViewById(R.id.currentBalanceText);
        TextView currentExpenseText = (TextView) findViewById(R.id.currentExpenseText);
        TextView targetExpenseText = (TextView) findViewById(R.id.targetExpenseText);
        Spinner typeSpinner = (Spinner) findViewById(R.id.typeSpinner);
        Spinner categorySpinner = (Spinner) findViewById(R.id.categorySpinner);
        EditText noteText = (EditText) findViewById(R.id.noteText);
        final EditText amountText = (EditText) findViewById(R.id.amountText);
        Button addButton = (Button) findViewById(R.id.addCategoryBtn);

        String totalAmount = "";
        String response = client.sendMessage("gettransactions;" + username);
        System.out.println("response in Main: " + response + "for user: " + username);
        if (!response.equals("")) {
            String[] transactions = response.split(";");

            String[][] transAmount = new String[transactions.length][5];
            for (int i = 0; i < transactions.length; i++) {
                transAmount[i] = transactions[i].split(" ");
            }

            int total = 0;
            for (int i = 0; i < transactions.length; i++) {
                int trans = Integer.parseInt(transAmount[i][2]);
                total += trans;
            }
            totalAmount = String.valueOf(total);
        } else {
            totalAmount = "0";
        }

        // current balance
        TextView currentBalance = new TextView(getApplicationContext());
        String currentBalanceString = "5252";
        /*
        select Balance from Transaction
        where Datetime = (select Max(Datetime) from Transaction) AND UserName = 'test1';
         */
        // setCurrentBalance(currentBalanceString);
        currentBalanceText.setText(currentBalanceString);



        // current expense
        TextView currentExpense = new TextView(getApplicationContext());
        String currentExpenseString = totalAmount;
        /*
        select sum(Amount) from Transaction
        where Type = 'expense' AND month(Datetime) = 3 And year(Datetime) = 2020 AND
        UserName = 'test1';
         */
        currentExpenseText.setText(currentExpenseString);

        // target expense
        TextView targetExpense = new TextView(getApplicationContext());
        int expense = Integer.parseInt(totalAmount);


        int cbalance = Integer.parseInt(currentBalanceString);
        int texpense = cbalance - expense;
        String targetExpenseString = String.valueOf(texpense);

        /*
        select * from Goal;
        select Amount from Goal
        where month(YearMonth) = 3 And year(YearMonth) = 2020 AND UserName = 'test1';
         */
        targetExpenseText.setText(targetExpenseString);


        // note
        String note = noteText.getText().toString();

        // amount
        final String amount = amountText.getText().toString();



        // get categories

        // super.onCreate(savedInstanceState);
        //  setContentView(R.layout.activity_main_page);

        String category = client.sendMessage("getcategories;" + passable);
        System.out.println("Categories: " + category);
        String[] categoriesMessage = category.split(";");
        String[][] categories = new String[categoriesMessage.length][2];

        String[] arrayCategorySpinner = new String[categories.length + 3];
        for (int i = 0; i < categoriesMessage.length+3; i++) {
            if (i ==0) {
                arrayCategorySpinner[i] = "Food";
            }
            if (i == 1) {
                arrayCategorySpinner[i] = "Groceries";
            }
            if (i == 2) {
                arrayCategorySpinner[i] = "Clothes";
            }
            if (i > 2) {
                categories[i - 3] = categoriesMessage[i - 3].split(" ");
                arrayCategorySpinner[i] = categories[i - 3][0];
            }
        }

        /*
        select * from Category;
         */
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>( MainPageActivity.this,
                android.R.layout.simple_spinner_item, arrayCategorySpinner);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        categorySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Object item = adapterView.getItemAtPosition(position);
                String type = item.toString();
                transCat = type;
                System.out.println("Category Selected: " + transCat);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });


        // get type

        //super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main_page);
        String[] arrayTypeSpinner = new String[] {
                "Expense", "Income"
        };
        /*
        select Type from Transaction
         */
        final ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arrayTypeSpinner);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);
        typeSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Object item = adapterView.getItemAtPosition(position);
                String type = item.toString();
                transType = type;
                System.out.println("Type Selected: " + transType);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        // ok button
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(amountText.getText().toString().equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainPageActivity.this);
                    builder.setMessage("Failed. Check categories, type, and amount.")
                            .setPositiveButton("okay", null)
                            .create()
                            .show();
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainPageActivity.this);
                    builder.setMessage("Transaction Successfully Added.")
                            .setPositiveButton("okay", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                    startActivity(getIntent());
                                }
                            })
                            .create()
                            .show();

                    String transAmount = amountText.getText().toString();
                    if (transType.equals("Expense")) {
                        client.sendMessage("inserttransaction;" + passable + " " + transType + " " + transAmount + " " + transCat + " April");
                        System.out.print("Type: " + transType + " Amount: " + transAmount+ "\n");
                    } else if (transType.equals("Income")) {
                        transAmount = "-" + transAmount;
                        client.sendMessage("inserttransaction;" + passable + " " + transType + " " + transAmount + " " + transCat + " April");
                        System.out.print("Type: " + transType + " Amount: " + transAmount + "\n");

                    }
                }
            }
        });



        // graph

        /*
        select day(DateTime) as Day, sum(Amount) as Expense from Transaction
        where Type = 'expense' AND month(Datetime) = 3 And year(Datetime) = 2020 AND
	    UserName = 'test1' Group by day(DateTime);
         */
        LineChartView lineChartView = findViewById(R.id.chart);
        String[] axisData = {"4.02", "4.03", "4.04", "4.05", "4.06", "4.07", "4.08", "4.09", "4.10",
                "4.11", "4.12", "4.13"};
        int[] yAxisData = {50, 20, 15, 30, 20, 60, 15, 40, 45, 10, 90, 18};
        List yAxisValues = new ArrayList();
        List axisValues = new ArrayList();
        Line line = new Line(yAxisValues);
        for(int i = 0; i < axisData.length; i++){
            axisValues.add(i, new AxisValue(i).setLabel(axisData[i]));
        }

        for (int i = 0; i < yAxisData.length; i++){
            yAxisValues.add(new PointValue(i, yAxisData[i]));
        }
        List lines = new ArrayList();
        lines.add(line);
        LineChartData data = new LineChartData();
        data.setLines(lines);
        lineChartView.setLineChartData(data);


        navigation();
    }

    private void navigation() {

        // navigation bar
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        break;
                    case R.id.nav_report:
                        Intent a = new Intent(MainPageActivity.this, report.class);
                        a.putExtra("username", passable);
                        startActivity(a);
                        break;
                    case R.id.nav_setting:
                        Intent b = new Intent(MainPageActivity.this,SettingActivity.class);
                        b.putExtra("username", passable);

                        startActivity(b);
                        break;
                    case R.id.nav_goal:
                        // Intent c = new Intent(MainPageActivity.this, GoalsActivity.class);
                        // c.putExtra("username", username);

                        // startActivity(c)
                        break;
                    case R.id.nav_category:
                        Intent d = new Intent(MainPageActivity.this, CategoryPageActivity.class);
                        d.putExtra("username", passable);
                        startActivity(d);
                        break;
                }
                return false;
            }
        });

    }

}
