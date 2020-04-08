package com.example.badgerbudget;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
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

        TextView currentBalenceText = (TextView) findViewById(R.id.currentBalenceText);
        TextView currentExpenseText = (TextView) findViewById(R.id.currentExpenseText);
        TextView targetExpenseText = (TextView) findViewById(R.id.targetExpenseText);
        Spinner typeSpinner = (Spinner) findViewById(R.id.typeSpinner);
        Spinner categorySpinner = (Spinner) findViewById(R.id.categorySpinner);

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
