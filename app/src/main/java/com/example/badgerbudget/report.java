package com.example.badgerbudget;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.charts.Cartesian;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class report extends AppCompatActivity {

    // A pie chart that shows overall spending per category
    AnyChartView pieChartView;
    String[] categories={"Category1","Category2","Category3"};
    int[] catExpense={300,200,600};

    String[] months={"January","February","March"};
    int[] monExpense={1000,600,1500};
    int[] monIncome={2000,2400,3000};

    // A bar chart that shows trend of overall spending/expense
    AnyChartView barChartView;

    private Spinner startMonth, endMonth, startYear, endYear;
    private Button btnSubmit;

    // A list of pairs of category with corresponding expense in dollar
    // A list of pairs of month with corresponding expense in dollar
    private HashMap<String, Double> categList, expenseByMonth;

    private String str_startMonth, str_endMonth, str_startYear, str_endYear;

    // Display total spend on report and use for calculating expense proportion of each category
    double totalSpend = 0;
    // Display total income on report
    double totalIncome = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        // show&use navigation

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

        //navigation();

        // drop down menu (spinner)
        createDropDownMenu();
        addListenerOnButton();


        pieChartView=findViewById(R.id.pie_chart_view);
        viewCategPieChart();

        barChartView=findViewById(R.id.bar_chart_view);
        viewExpenseBarChart();



        // get total expense
        TextView textView_totSpDisp = (TextView) findViewById(R.id.TotSpendDisp);
        totalSpend = getTotalValue(monExpense);
        textView_totSpDisp.setText("$" + totalSpend);

        // get total income
        TextView textView_totIncDisp = (TextView) findViewById(R.id.TotIncDisp);
        totalIncome = getTotalValue(monIncome);
        textView_totIncDisp.setText("$" + totalIncome);

    }


    private void navigation() {

    }

    /*
    Create a bar chart that shows a trend of expense by month. X-axis shows months,
    and Y-axis shows the amount of expense in dollars.
    This method uses expenseByMonth to get data.
     */
    public void viewExpenseBarChart(){

        Cartesian bar = AnyChart.column();
        List<DataEntry> barDataEntries = new ArrayList<>();

        for(int i=0;i<months.length;i++){
            barDataEntries.add(new ValueDataEntry(months[i],monExpense[i]));
        }

        bar.data(barDataEntries);
        bar.title("Monthly expense trend");
        barChartView.setChart(bar);
    }


    /*
    Create a pie chart that is divided with categories and shows the amount of expense in dollar
     and number of percentage in a pie chart. This method uses categList to get data.
     */

    public void viewCategPieChart(){

        Pie pie = AnyChart.pie();
        List<DataEntry> pieDataEntries = new ArrayList<>();

        for(int i=0;i<categories.length;i++){
            pieDataEntries.add(new ValueDataEntry(categories[i],catExpense[i]));
        }

        pie.data(pieDataEntries);
        pie.title("Expense");
        pieChartView.setChart(pie);

    }

    public void createDropDownMenu(){
        // dropdown menu: startMonth
        startMonth = (Spinner) findViewById(R.id.sMonth);
        ArrayAdapter<String> sMonthAdapter = new ArrayAdapter<String>(report.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Month));
        sMonthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // show adapter in the spinner
        startMonth.setAdapter(sMonthAdapter);

        // dropdown menu: startMonth
        endMonth = (Spinner) findViewById(R.id.eMonth);
        ArrayAdapter<String> eMonthAdapter = new ArrayAdapter<String>(report.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Month));
        eMonthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // show adapter in the spinner
        endMonth.setAdapter(eMonthAdapter);

        // dropdown menu: end Year
        startYear = (Spinner) findViewById(R.id.sYear);
        ArrayAdapter<String> sYearAdapter = new ArrayAdapter<String>(report.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Year));
        sYearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // show adapter in the spinner
        startYear.setAdapter(sYearAdapter);

        // dropdown menu: end Year
        endYear = (Spinner) findViewById(R.id.eYear);
        ArrayAdapter<String> eYearAdapter = new ArrayAdapter<String>(report.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Year));
        eYearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // show adapter in the spinner
        endYear.setAdapter(eYearAdapter);


    }

    /* get the selected dropdown list value */
    public void addListenerOnButton() {

        startMonth = (Spinner) findViewById(R.id.sMonth);
        btnSubmit = (Button) findViewById(R.id.submit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v){
                Toast.makeText(report.this,
                        "OnClickListener: " +
                        "\nstartMonth : " + String.valueOf(startMonth.getSelectedItem()) +
                                " startYear: " + String.valueOf(startYear.getSelectedItem()) +
                        "\nendMoth : " + String.valueOf(endMonth.getSelectedItem()) +
                        " endYear: " + String.valueOf(endYear.getSelectedItem()),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    /* get a list of categories and corresponding expense from database */
    public HashMap<String, Double> getCategories(String startMonth, String endMonth, String startYear, String endYear){


        return categList;
    }

    /* Get a list of months (from starting month to end month) and corresponding expense from database */
    public HashMap<String, Double> getExpense(String startMonth, String endMonth, String startYear, String endYear) {


        return expenseByMonth;
    }

    /* Calculate total spending for a specified period of time by adding all the monthly spending in expenseByMonth. */
    public double getTotalValue(int[] amount){

        double totalAmount = 0.0;
        for (int i=0 ; i < amount.length; i++) {
            totalAmount += amount[i];
        }

        return totalAmount;
    }

    /* Get a pop-up window that shows a transaction history of selected period of time */
    public void onClick_viewTrans(View v){

    }
}

