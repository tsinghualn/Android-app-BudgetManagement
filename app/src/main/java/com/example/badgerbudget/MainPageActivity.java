package com.example.badgerbudget;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

public class MainPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        TextView currentBalanceText = (TextView) findViewById(R.id.currentBalanceText);
        TextView currentExpenseText = (TextView) findViewById(R.id.currentExpenseText);
        TextView targetExpenseText = (TextView) findViewById(R.id.targetExpenseText);
        Spinner typeSpinner = (Spinner) findViewById(R.id.typeSpinner);
        Spinner categorySpinner = (Spinner) findViewById(R.id.categorySpinner);
        EditText noteText = (EditText) findViewById(R.id.noteText);
        final EditText amountText = (EditText) findViewById(R.id.amountText);
        Button addButton = (Button) findViewById(R.id.addButton);



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
        String currentExpenseString = "5252";
        /*
        select sum(Amount) from Transaction
        where Type = 'expense' AND month(Datetime) = 3 And year(Datetime) = 2020 AND
        UserName = 'test1';
         */
        currentExpenseText.setText(currentExpenseString);

        // target expense
        TextView targetExpense = new TextView(getApplicationContext());
        String targetExpenseString = "5252";
        /*
        select * from Goal;
        select Amount from Goal
        where month(YearMonth) = 3 And year(YearMonth) = 2020 AND UserName = 'test1';
         */
        targetExpenseText.setText(targetExpenseString);


        // note
        String note = noteText.getText().toString();

        // amount
        String amount = amountText.getText().toString();



        // get categories

        // super.onCreate(savedInstanceState);
       //  setContentView(R.layout.activity_main_page);
        String[] arrayCategorySpinner = new String[] {
                "food", "groceries", "clothes", "4", "5", "6", "7"
        };
        /*
        select * from Category;
         */
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>( MainPageActivity.this,
                android.R.layout.simple_spinner_item, arrayCategorySpinner);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);


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
                    builder.setMessage("successfully added")
                            .setPositiveButton("okay", null)
                            .create()
                            .show();
                    // todo: add to data base

                    // refresh
                   // Intent addIntent = new Intent(MainPageActivity.this, MainPageActivity.class);
                   // MainPageActivity.this.startActivity(addIntent);
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
                        startActivity(a);
                        break;
                    case R.id.nav_setting:
                        Intent b = new Intent(MainPageActivity.this,SettingActivity.class);
                        startActivity(b);
                        break;
                    case R.id.nav_goal:
                        // Intent c = new Intent(MainPageActivity.this, GoalsActivity.class);
                        // startActivity(c)
                        break;
                    case R.id.nav_category:
                        Intent d = new Intent(MainPageActivity.this, CategoryPageActivity.class);
                        startActivity(d);
                        break;
                }
                return false;
            }
        });

    }

}
