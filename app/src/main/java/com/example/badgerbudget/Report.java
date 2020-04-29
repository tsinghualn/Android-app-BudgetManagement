package com.example.badgerbudget;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
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
import com.anychart.charts.Cartesian;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Report extends AppCompatActivity {

    boolean valid = false;

    String sMonth, sYear, eMonth, eYear;
    // new pie chart
    PieChart pieChart;
    ArrayList<PieEntry> yValues;

    // A bar chart that shows trend of overall spending/expense


    double totalAmount;

    Spinner startMonth, endMonth, startYear, endYear;
    Button btnSubmit;
    Button btnViewTrans;
    ArrayList<String[]> expenseInRange;
    ArrayList<String[]> incomeInRange;

    //Button popTrans;
    // A list of pairs of category with corresponding expense in dollar
    // A list of pairs of month with corresponding expense in dollar
    HashMap<String, Double> categList;
    HashMap<String, Double> expenseByMonth,incomeByMonth;
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

    // Display total spend on Report and use for calculating expense proportion of each category
    double totalSpend = 0;
    // Display total income on Report
    double totalIncome = 0;

    String passable;
    Transaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        Bundle un = getIntent().getExtras();
        String username = un.getString("username");
        passable = username;


        pieChart = (PieChart)findViewById(R.id.piechart);

        //transaction = new Transaction(passable);


        // show&use navigation
        // navigation bar
        navigation();

        // drop down menu (spinner)
        createDropDownMenu();
        addListenerOnButton();


    }


    private void navigation() {
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        Intent a = new Intent(Report.this, MainPageActivity.class);
                        a.putExtra("username", passable);
                        startActivity(a);
                        break;
                    case R.id.nav_report:
                        // Intent b = new Intent(Report.this, Report.class);
                        // startActivity(b);
                        break;
                    case R.id.nav_setting:
                        Intent c = new Intent(Report.this,SettingActivity.class);
                        c.putExtra("username", passable);
                        startActivity(c);
                        break;
                    case R.id.nav_goal:
                         Intent d = new Intent(Report.this, GoalActivity.class);
                        d.putExtra("username", passable);
                         startActivity(d);

                        break;
                    case R.id.nav_category:
                        Intent e = new Intent(Report.this, CategoryPageActivity.class);
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
    public void viewExpenseBarChart(HashMap<String, Double> expByMonth){

        AnyChartView barChartView=findViewById(R.id.bar_chart_view);

        Set<String> monthSet = expByMonth.keySet();

        List<DataEntry> barDataEntries = new ArrayList<>();

        for(String month: monthSet){
            barDataEntries.add(new ValueDataEntry(month, expByMonth.get(month)));
        }

        Cartesian bar = AnyChart.column();

        if(barDataEntries.isEmpty()){

        } else {
            bar.data(barDataEntries);
            bar.title("Monthly expense trend");
            barChartView.setChart(bar);

        }

    }




    /*
    Create a pie chart that is divided with categories and shows the amount of expense in dollar
     and number of percentage in a pie chart. This method uses categList to get data.
     */

    public void viewCategPieChart(){

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

        Set<String> cat = categList.keySet();

        for(String category: cat){
            double tempAmount = categList.get(category).doubleValue();
            float amount = (float) tempAmount;
            yValues.add(new PieEntry(amount, category));
        }


    }

    public void createDropDownMenu(){
        // dropdown menu: startMonth
        startMonth = (Spinner) findViewById(R.id.sMonth);
        ArrayAdapter<String> sMonthAdapter = new ArrayAdapter<String>(Report.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Month));
        sMonthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // show adapter in the spinner
        startMonth.setAdapter(sMonthAdapter);

        // dropdown menu: startMonth
        endMonth = (Spinner) findViewById(R.id.eMonth);
        ArrayAdapter<String> eMonthAdapter = new ArrayAdapter<String>(Report.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Month));
        eMonthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // show adapter in the spinner
        endMonth.setAdapter(eMonthAdapter);

        // dropdown menu: end Year
        startYear = (Spinner) findViewById(R.id.sYear);
        ArrayAdapter<String> sYearAdapter = new ArrayAdapter<String>(Report.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Year));
        sYearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // show adapter in the spinner
        startYear.setAdapter(sYearAdapter);

        // dropdown menu: end Year
        endYear = (Spinner) findViewById(R.id.eYear);
        ArrayAdapter<String> eYearAdapter = new ArrayAdapter<String>(Report.this,
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

                btnViewTrans = (Button) findViewById(R.id.viewTransBtn);

                if(isValidRange(sMonth, sYear, eMonth, eYear)){
                    // valid
                    valid = true;

                    transaction = new Transaction(passable);

                    // call transaction query here (if month/year are valid)
                    expenseInRange =  (ArrayList<String[]>)
                            (transaction.setListInRange(sMonth, sYear, eMonth, eYear, "expense").clone());

                    incomeInRange = (ArrayList<String[]>)
                            (transaction.setListInRange(sMonth, sYear, eMonth, eYear, "income").clone());

                    categList = new HashMap<String, Double> (transaction.getCategList(expenseInRange));
                    expenseByMonth = new HashMap<String, Double> (transaction.getExpenseByMonth(expenseInRange));
                    incomeByMonth = new HashMap<String, Double> (transaction.getIncomeByMonth(incomeInRange));

                    viewCategPieChart();
                    viewExpenseBarChart(expenseByMonth);

                    // get total expense
                    TextView textView_totSpDisp = (TextView) findViewById(R.id.TotSpendDisp);
                    totalSpend = getTotalValue(expenseByMonth);
                    textView_totSpDisp.setText("$" + totalSpend);

                    // get total income
                    TextView textView_totIncDisp = (TextView) findViewById(R.id.TotIncDisp);
                    //totalIncome = getTotalValue(incomeByMonth);
                    totalIncome = transaction.setSalaryInRange(sMonth, sYear, eMonth, eYear);
                    textView_totIncDisp.setText("$" + totalIncome);

                } else {
                    Toast.makeText(Report.this,
                            "OnClickListener: " +
                                    "Please select a valid range of months",
                            Toast.LENGTH_SHORT).show();
                    valid = false;
                }

                /*Toast.makeText(Report.this,
                        "OnClickListener: " +
                        "\nstartMonth : " + sMonth +
                                " startYear: " + sYear +
                        "\nendMoth : " + eMonth +
                        " endYear: " + eYear,
                        Toast.LENGTH_SHORT).show();*/
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

    /* Calculate total spending for a specified period of time by adding all the monthly spending in expenseByMonth. */
    public double getTotalValue(HashMap<String, Double> map){

        // get Expense for each month from hashmap 
        // sum up for each month, for each transaction

        totalAmount = 0;
        Set<String> keys = map.keySet();
        for(String key: keys){
            totalAmount += map.get(key);
        }

        return totalAmount;
    }

    /* Get a pop-up window that shows a transaction history of selected period of time */
    public void onClick_viewTrans(View v){

        System.out.println(valid);
        if (valid){

            Intent intent = new Intent(Report.this, PopupTransaction.class);
            intent.putExtra("startMonth", sMonth);
            intent.putExtra("startYear", sYear);
            intent.putExtra("endMonth", eMonth);
            intent.putExtra("endYear", eYear);
            intent.putExtra("userID", passable);
            intent.putExtra("expInRange", expenseInRange);
            intent.putExtra("inInRange", incomeInRange);


            startActivity(intent);

            // bring start & end month/year when open transaction

        } else {
            Toast.makeText(Report.this,
                    "OnClickListener: " +
                            "\nstartMonth : " + sMonth +
                            " startYear: " + sYear +
                            "\nendMoth : " + eMonth +
                            " endYear: " + eYear,
                    Toast.LENGTH_SHORT).show();
        }
    }
}

