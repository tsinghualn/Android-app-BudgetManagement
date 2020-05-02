package com.example.badgerbudget;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.example.badgerbudget.data.model.Client;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

    AnyChartView barChartView;
    List<DataEntry> barDataEntries;




    double totalAmount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
            System.out.println();
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main_page);
            Bundle un = getIntent().getExtras();
            String username = un.getString("username");
            passable = username;
            TextView currentBalanceText = (TextView) findViewById(R.id.currentBalanceText);
            TextView currentExpenseText = (TextView) findViewById(R.id.currentExpenseText);
            TextView targetExpenseText = (TextView) findViewById(R.id.targetExpenseText);
            Spinner typeSpinner = (Spinner) findViewById(R.id.typeSpinner);
            Spinner categorySpinner = (Spinner) findViewById(R.id.categorySpinner);
            final EditText noteText = (EditText) findViewById(R.id.noteText);
            final EditText amountText = (EditText) findViewById(R.id.amountText);
            Button addButton = (Button) findViewById(R.id.addCategoryBtn);

            String totalAmount = "";
            String response = client.sendMessage("gettransactions;" + username);
            System.out.print("Response: " + response);
            if (!response.equals("")) {
                String[] transactions = response.split(";");

                String[][] transAmount = new String[transactions.length][7];
                for (int i = 0; i < transactions.length; i++) {
                    transAmount[i] = transactions[i].split(" ");
                }

                double total = 0.0;
                for (int i = 0; i < transactions.length; i++) {
                    if (transAmount[i][4].equals(getMonth()) && transAmount[i][5].equals(getYear())) {

                        double trans = Double.parseDouble(transAmount[i][2]);
                        total += trans;
                    }
                }
                totalAmount = String.format("%.2f", total);
            } else {
                totalAmount = "0";
            }

            String salary = client.sendMessage("getsalary;" + passable);

            // current balance
            TextView currentBalance = new TextView(getApplicationContext());
            String currentBalanceString = salary;
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
            double expense = Double.parseDouble(totalAmount);


            double cbalance = Double.parseDouble(currentBalanceString);

            String category = client.sendMessage("getcategories;" + passable);
            String[] categoriesMessage = category.split(";");
            String[][] categories = new String[categoriesMessage.length][2];
            double target = 0.0;

            for (int i = 0; i < categoriesMessage.length; i++) {
                categories[i] = categoriesMessage[i].split(" ");
                target +=  Double.parseDouble(categories[i][1]);
            }

        //Should be all of the categories budgets added up.

            double texpense = target;
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
        if (texpense < expense) {
            Context context =  getApplicationContext();
            LinearLayout layout = new LinearLayout(context);
            layout.setOrientation(LinearLayout.VERTICAL);
            TextView warning = new TextView(MainPageActivity.this);
            warning.setText("Whoops. Looks Like Your Current Expenses are more than your Target Expenses for the month. Time to Cut Back on Spending!");
            warning.setGravity(Gravity.CENTER);
            androidx.appcompat.app.AlertDialog.Builder warningView = new androidx.appcompat.app.AlertDialog.Builder(MainPageActivity.this);
            warningView.setTitle("WARNING");
            layout.addView(warning);
            warningView.setView(layout);
            warningView.setPositiveButton("Ok",null);
            androidx.appcompat.app.AlertDialog dialog = warningView.create();
            dialog.show();
        }


            String[] arrayCategorySpinner = new String[categories.length ];
            for (int i = 0; i < categoriesMessage.length; i++) {
                    arrayCategorySpinner[i] = categories[i][0];
            }

        /*
        select * from Category;
         */
            ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(MainPageActivity.this,
                    android.R.layout.simple_spinner_item, arrayCategorySpinner);
            categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            categorySpinner.setAdapter(categoryAdapter);

            categorySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                    Object item = adapterView.getItemAtPosition(position);
                    String type = item.toString();
                    transCat = type;
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                }
            });


            // get type

            //super.onCreate(savedInstanceState);
            // setContentView(R.layout.activity_main_page);
            String[] arrayTypeSpinner = new String[]{
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
                    if (amountText.getText().toString().equals("")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainPageActivity.this);
                        builder.setMessage("Failed. Check categories, type, and amount.")
                                .setPositiveButton("okay", null)
                                .create()
                                .show();
                    } else {
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

                       // String date = getDate();
                        String date = getDateString(0);
                        String year = getYear();
                        String month = getMonth();
                        String transAmount = amountText.getText().toString();
                        String note;
                        if (noteText.getText().toString().equals("")) {
                            note = "NULL";
                        } else {
                            note = noteText.getText().toString();

                        }
                        if (transType.equals("Expense")) {
                            client.sendMessage("inserttransaction;" + passable + " " + transType + " " + transAmount + " "
                                    + date +  " " + month + " " + year + " " + transCat + " " + note);
                        } else if (transType.equals("Income")) {
                            transAmount = "-" + transAmount;
                            client.sendMessage("inserttransaction;" + passable + " " + transType + " " + transAmount + " "
                                    + date + " " + month + " " + year + " "  + transCat + " " + note);

                        }
                    }
                }
            });

            // graph

        /*
        select day(DateTime) as Day, sum(Amount) as Expense from Transaction
        where Type = 'expense' AND month(Datetime) = 3 And year(Datetime) = 2020 AND
	    UserName = 'test1' Group by day(DateTime);

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
        */

            // bar graph
            viewExpenseBarChart();

            navigation();
        }

    public void viewExpenseBarChart(){
        barChartView=findViewById(R.id.bar_chart_view);

        String today = getDateString(0);
        String today1 = getDateString(1);
        String today2 = getDateString(2);
        String today3 = getDateString(3);
        String today4 = getDateString(4);
        String today5 = getDateString(5);
        String today6 = getDateString(6);

        //System.out.println("Today 1: " + today6);
        double todayExpense = 0;
        double today1Expense = 0;
        double today2Expense = 0;
        double today3Expense = 0;
        double today4Expense = 0;
        double today5Expense = 0;
        double today6Expense = 0;
        // todo: get daily expense
        String transaction = client.sendMessage("gettransactions;" + passable);
        String[] transactionsMessage = transaction.split(";");
        String[][] transactions = new String[transactionsMessage.length][8];
        if (!transaction.equals("")) {
            for (int i = 0; i < transactionsMessage.length; i++) {
                transactions[i] = transactionsMessage[i].split(" ");

                if (transactions[i][1].equals("Expense")) {

                    if (transactions[i][3].equals(today)) {
                        todayExpense += Double.valueOf(transactions[i][2]);

                    } else if (transactions[i][3].equals(today1)) {
                        today1Expense += Double.valueOf(transactions[i][2]);
                    } else if (transactions[i][3].equals(today2)) {
                        today2Expense += Double.valueOf(transactions[i][2]);
                    } else if (transactions[i][3].equals(today3)) {
                        today3Expense += Double.valueOf(transactions[i][2]);
                    } else if (transactions[i][3].equals(today4)) {
                        today4Expense += Double.valueOf(transactions[i][2]);
                    } else if (transactions[i][3].equals(today5)) {
                        today5Expense += Double.valueOf(transactions[i][2]);
                    } else if (transactions[i][3].equals(today6)) {
                        today6Expense += Double.valueOf(transactions[i][2]);
                    }
                }
            }
        } else {
            toastMessage("No Transaction Data Available");
        }

        // add data to chart

        Cartesian bar = AnyChart.column();

        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry(today6, today6Expense));
        data.add(new ValueDataEntry(today5, today5Expense));
        data.add(new ValueDataEntry(today4, today4Expense));
        data.add(new ValueDataEntry(today3, today3Expense));
        data.add(new ValueDataEntry(today2, today2Expense));
        data.add(new ValueDataEntry(today1, today1Expense));
        data.add(new ValueDataEntry(today, todayExpense));
        //data.add(new ValueDataEntry(transactions[1][1], todayExpense));

        bar.data(data);
        bar.title("Daily expense trend");
        barChartView.setChart(bar);
    }

    private String getDateString(int i) {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -i);
        return dateFormat.format(cal.getTime());
    }
/*
    public void addExpense(String[] months, double[] monExpense){

        barDataEntries = new ArrayList<>();

        // use hashmap!

        for(int i=0;i<months.length;i++){
            barDataEntries.add(new ValueDataEntry(months[i],monExpense[i]));
        }

    }
*/

    /**
     * Get full date to be used in the report page
     * @return
     */
    private String getDate() {
        //Gets the date for later
        DateFormat df = new SimpleDateFormat("MM/dd/yy");
        Date date = new Date();
        String dateString = df.format(date);
        return dateString;
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


    private void toastMessage(String message) {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();

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
                        Intent a = new Intent(MainPageActivity.this, Report.class);
                        a.putExtra("username", passable);
                        startActivity(a);
                        break;
                    case R.id.nav_setting:
                        Intent b = new Intent(MainPageActivity.this,SettingActivity.class);
                        b.putExtra("username", passable);

                        startActivity(b);
                        break;
                    case R.id.nav_goal:
                         Intent c = new Intent(MainPageActivity.this, GoalActivity.class);
                         c.putExtra("username", passable);
                         startActivity(c);
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
