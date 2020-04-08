package com.example.badgerbudget;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.charts.Cartesian;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;


public class report extends AppCompatActivity {

    AnyChartView pieChartView;
    String[] categories={"Category1","Category2","Category3"};
    int[] catExpense={300,200,600};

    String[] months={"Jan","Feb","Mar"};
    int[] monExpense={1000,600,1500};


    AnyChartView barChartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        pieChartView=findViewById(R.id.pie_chart_view);
        createPieChart();

        barChartView=findViewById(R.id.bar_chart_view);
        createBarChart();

        // navigation bar
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        Intent a = new Intent(report.this, MainPageActivity.class);
                        startActivity(a);
                        break;
                    case R.id.nav_report:
                        // Intent b = new Intent(report.this, report.class);
                        // startActivity(b);
                        break;
                    case R.id.nav_setting:
                        Intent c = new Intent(report.this,SettingActivity.class);
                        startActivity(c);
                        break;
                    case R.id.nav_goal:
                        // Intent d = new Intent(report.this, GoalsActivity.class);
                        // startActivity(d)
                        break;
                    case R.id.nav_category:
                        Intent e = new Intent(report.this, CategoryPageActivity.class);
                        startActivity(e);
                        break;
                }
                return false;
            }
        });
    }

    public void createBarChart(){

        Cartesian bar = AnyChart.column();
        List<DataEntry>barDataEntries=new ArrayList<>();

        for(int i=0;i<months.length;i++){
            barDataEntries.add(new ValueDataEntry(months[i],monExpense[i]));
        }

        bar.data(barDataEntries);
        bar.title("Monthly expense trend");
        barChartView.setChart(bar);
    }


    public void createPieChart(){

        Pie pie=AnyChart.pie();
        List<DataEntry>pieDataEntries=new ArrayList<>();

        for(int i=0;i<categories.length;i++){
            pieDataEntries.add(new ValueDataEntry(categories[i],catExpense[i]));
        }

        pie.data(pieDataEntries);
        pie.title("Expense");
        pieChartView.setChart(pie);

    }
}

