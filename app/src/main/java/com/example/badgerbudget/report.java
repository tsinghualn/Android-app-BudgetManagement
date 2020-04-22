package com.example.badgerbudget;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.MonthDisplayHelper;
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
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.lang.Object.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class report extends AppCompatActivity {


    String[] categories = {"categ1", "categ2", "categ3"};
    double[] catExpense={300,200,600};

    String[] months={"January","February","March"};
    double[] monExpense={1000,600,1500};
    double[] monIncome={2000,2400,3000};

    String sMonth, sYear, eMonth, eYear;
    // new pie chart
    PieChart pieChart;
    ArrayList<PieEntry> yValues;

    // A bar chart that shows trend of overall spending/expense
    AnyChartView barChartView;
    List<DataEntry> barDataEntries;
    double totalAmount;

    Spinner startMonth, endMonth, startYear, endYear;
    Button btnSubmit;

    // A list of pairs of category with corresponding expense in dollar
    // A list of pairs of month with corresponding expense in dollar
    HashMap<String, Double> categList, expenseByMonth;

    // map of month in string - month in integer
    private static final Map<String, Integer> MONTHMAP = new HashMap<String, Integer>() {
        {
            put("January", 1);
            put("February",2);
            put("March",3);
            put("April",4);
            put("May",5);
            put("June",6);
            put("July",7);
            put("August",8);
            put("September",9);
            put("October",10);
            put("November",11);
            put("December",12);
        }
    };

    String str_startMonth, str_endMonth, str_startYear, str_endYear;

    // Display total spend on report and use for calculating expense proportion of each category
    double totalSpend = 0;
    // Display total income on report
    double totalIncome = 0;

    String passable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        Bundle un = getIntent().getExtras();
        String username = un.getString("username");
        passable = username;
        // show&use navigation
        // navigation bar
        navigation();

        // drop down menu (spinner)
        createDropDownMenu();
        addListenerOnButton();

        viewCategPieChart();

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
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        Intent a = new Intent(report.this, MainPageActivity.class);
                        a.putExtra("username", passable);
                        startActivity(a);
                        break;
                    case R.id.nav_report:
                        // Intent b = new Intent(report.this, report.class);
                        // startActivity(b);
                        break;
                    case R.id.nav_setting:
                        Intent c = new Intent(report.this,SettingActivity.class);
                        c.putExtra("username", passable);
                        startActivity(c);
                        break;
                    case R.id.nav_goal:
                         Intent d = new Intent(report.this, GoalActivity.class);
                        d.putExtra("username", passable);
                         startActivity(d);
                        break;
                    case R.id.nav_category:
                        Intent e = new Intent(report.this, CategoryPageActivity.class);
                        e.putExtra("username", passable);
                        startActivity(e);
                        break;
                }
                return false;
            }
        });
    }

    /*
    Create a bar chart that shows a trend of expense by month. X-axis shows months,
    and Y-axis shows the amount of expense in dollars.
    This method uses expenseByMonth to get data.
     */
    public void viewExpenseBarChart(){

        barChartView=findViewById(R.id.bar_chart_view);

        addExpense(months, monExpense);

        Cartesian bar = AnyChart.column();

        bar.data(barDataEntries);
        bar.title("Monthly expense trend");
        barChartView.setChart(bar);
    }



    public void addExpense(String[] months, double[] monExpense){

        barDataEntries = new ArrayList<>();

        // use hashmap! 
        
        for(int i=0;i<months.length;i++){
            barDataEntries.add(new ValueDataEntry(months[i],monExpense[i]));
        }

    }


    /*
    Create a pie chart that is divided with categories and shows the amount of expense in dollar
     and number of percentage in a pie chart. This method uses categList to get data.
     */

    public void viewCategPieChart(){

        pieChart = (PieChart)findViewById(R.id.piechart);

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5,10,5,5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setDrawHoleEnabled(false);
        pieChart.setHoleColor(Color.BLACK);
        pieChart.setTransparentCircleRadius(61f);

        setyValues();
        
        Description description = new Description();
        description.setText("Expense");
        description.setTextSize(10);
        pieChart.setDescription(description);

        PieDataSet dataSet = new PieDataSet(yValues,"");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data = new PieData((dataSet));
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);

    }

    public void setyValues(){

        yValues = new ArrayList<PieEntry>();

        for(int i=0; i < categories.length; i++){
            float a = (float) catExpense[i];
            yValues.add(new PieEntry(a,categories[i]));
        }

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
    private void addListenerOnButton() {

        startMonth = (Spinner) findViewById(R.id.sMonth);
        btnSubmit = (Button) findViewById(R.id.submit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v){

                sMonth = String.valueOf(startMonth.getSelectedItem());
                sYear =  String.valueOf(startYear.getSelectedItem());
                eMonth = String.valueOf(endMonth.getSelectedItem());
                eYear = String.valueOf(endYear.getSelectedItem());

                if(isValidRange(sMonth, sYear, eMonth, eYear)){
                    // valid
                    // call transaction query here (if month/year are valid)

                } else {
                    Toast.makeText(report.this,
                            "OnClickListener: " +
                                    "Please select a valid range of months",
                            Toast.LENGTH_SHORT).show();
                }

                Toast.makeText(report.this,
                        "OnClickListener: " +
                        "\nstartMonth : " + sMonth +
                                " startYear: " + sYear +
                        "\nendMoth : " + eMonth +
                        " endYear: " + eYear,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean isValidRange(String sm, String sy, String em, String ey){

        int eyInt = Integer.parseInt(ey.trim());
        int syInt = Integer.parseInt(sy.trim());

        // check if endYear < staryYear
        if( eyInt < syInt ) {
            // invalid
            return false;
        } else {
            // valid year endYear >= staryYear

            Integer smInt = MONTHMAP.get(sm);
            Integer emInt = MONTHMAP.get(em);

            // if two years are same
            if(eyInt == syInt){

                if(smInt <= emInt){
                    // valid
                    return true;
                } else if(smInt > emInt){
                    // invalid
                    return false;
                }

            } else if(eyInt > syInt) {
                // valid, month doesn't matter since year differ.
                return true;
            }
        }
        return false;
    }


    public void setData(String startMonth, String endMonth, String startYear, String endYear){

        // get data from database with args

        // for loop iterate through and put data into array

        // check if two array length are matched

    }
    /* get a list of categories and corresponding expense from database */
    public void setCategories(){



    }

    /* Get a list of months (from starting month to end month) and corresponding expense from database */
    public void setExpense() {

        //expenseByMonth.put("January", 3000.5);

    }

    /* Calculate total spending for a specified period of time by adding all the monthly spending in expenseByMonth. */
    public double getTotalValue(double[] amount){

        // get Expense for each month from hashmap 
        // sum up for each month, for each transaction

        totalAmount = 0;
        for (int i=0 ; i < amount.length; i++) {
            totalAmount += amount[i];
        }

        return totalAmount;
    }

    /* Get a pop-up window that shows a transaction history of selected period of time */
    public void onClick_viewTrans(View v){

        // bring start & end month/year when open transaction

    }
}

