package com.example.badgerbudget;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
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

        // current balance
        TextView currentBalance = new TextView(getApplicationContext());
        String currentBalanceString = "";
        currentBalance.setText(currentBalanceString);

        // current expense
        TextView currentExpense = new TextView(getApplicationContext());
        String currentExpenseString = "";
        currentExpense.setText(currentExpenseString);

        // target expense
        TextView targetExpense = new TextView(getApplicationContext());
        String targetExpenseString = "";
        targetExpense.setText(targetExpenseString);

        // get categories
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        String[] arrayCategorySpinner = new String[] {
                "1", "2", "3", "4", "5", "6", "7"
        };
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arrayCategorySpinner);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        // get type
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        String[] arrayTypeSpinner = new String[] {
                "1", "2", "3", "4", "5", "6", "7"
        };
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arrayTypeSpinner);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);


        // graph
        LineChartView lineChartView = findViewById(R.id.chart);

        String[] axisData = {"Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sept",
                "Oct", "Nov", "Dec"};
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

    }
}