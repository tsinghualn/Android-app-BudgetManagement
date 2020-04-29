package com.example.badgerbudget;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import com.example.badgerbudget.data.model.Client;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;

public class CategoryPageActivity extends AppCompatActivity {
    TableLayout categoryTable;
    TextView salary;
    Button addCategory;
    Button deleteCategory;

    String passable;
    Client client = new Client(6868, "10.0.2.2");
    String cat = "";
    String[] categoriesMessage;
    String[][] categories;
    int index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_page);
        Bundle un = getIntent().getExtras();
        String username = un.getString("username");
        passable = username;
        salary = findViewById(R.id.curSalaryTextView);
        categoryTable = findViewById(R.id.categoryTable);
        addCategory = findViewById(R.id.addCategoryBtn);
        deleteCategory = findViewById(R.id.deleteCateogryBtn);

        navigation();
        //Make everything show up in the categories table
        //2 rows in the table
        categoryTable.setColumnStretchable(0, true);
        categoryTable.setColumnStretchable(1, true);
        TableRow defaults = new TableRow(this);
        TextView catName = new TextView(this);
        catName.setText("Category Name");
        TextView catBudget = new TextView(this);
        catBudget.setText("Category Budget");
        defaults.addView(catName);
        defaults.addView(catBudget);
        categoryTable.addView(defaults);



        String category = client.sendMessage("getcategories;" + passable);

        if (!category.equals("")) {
            String[] parsed = category.split(" ");
            categoryTable.setColumnStretchable(0, true);
            categoryTable.setColumnStretchable(1, true);
            categoriesMessage = category.split(";");
            System.out.println("categoriesMessage.length: " + categoriesMessage.length);
            categories = new String[categoriesMessage.length][2];
            for (int i = 0; i < categoriesMessage.length; i++) {
                if (categoriesMessage.length != 0) {
                    categories[i] = categoriesMessage[i].split(" ");
                    TableRow tr = new TableRow(this);
                    tr.setClickable(true);
                    TextView catname = new TextView(this);
                    if (!categories[i].equals(null)) {
                        catname.setText(categories[i][0]);
                        TextView budget = new TextView(this);
                        budget.setText(categories[i][1]);
                        tr.addView(catname);
                        tr.addView(budget);
                        tr.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                TableRow tr = (TableRow) v;

                                TextView cat = (TextView) tr.getChildAt(0);
                                TextView bud = (TextView) tr.getChildAt(1);

                                final String catResult = cat.getText().toString();
                                String budResult = bud.getText().toString();

                                Context context =  getApplicationContext();
                                LinearLayout layout = new LinearLayout(context);
                                layout.setOrientation(LinearLayout.VERTICAL);
                                AlertDialog.Builder editCat = new AlertDialog.Builder(CategoryPageActivity.this);
                                editCat.setTitle("Edit Category");
                                final EditText input = new EditText(context);
                                layout.addView(input);
                                input.setText(catResult);
                                final EditText budget = new EditText(context);
                                budget.setText(budResult);
                                layout.addView(budget);
                                editCat.setView(layout);
                                editCat.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (!input.getText().toString().equals("") && !budget.getText().toString().equals("")) {
                                            client.sendMessage("updatecategory;" + passable + " " + input.getText().toString() + " " + budget.getText().toString() + " " + catResult);
                                            toastMessage("Category Successfully Changed.");
                                            finish();
                                            startActivity(getIntent());
                                        } else {
                                            toastMessage("Please Insert a Category and Set a Budget ");
                                        }
                                    }
                                });
                                editCat.setNegativeButton("Cancel", null);
                                AlertDialog dialog = editCat.create();
                                dialog.show();
                            }
                        });
                        categoryTable.addView(tr);
                    } else {
                        System.out.println("categories[i] is null");
                        break;
                    }
                } else {
                    System.out.println("categoriesMessage.length is 0");
                    break;
                }
            }
        }


        String response = client.sendMessage("getsalary;" + passable);
        salary.setText("$"+response);

        Integer total = 0;
        for (int i = 0; i < categories.length; i++) {
            total += Integer.parseInt(categories[i][1]);
        }
        Integer salaryInt = Integer.parseInt(response);
        if (total > salaryInt ) {
            //Warns the user that their category budgets are higher than their monthly salary
            Context context =  getApplicationContext();
            LinearLayout layout = new LinearLayout(context);
            layout.setOrientation(LinearLayout.VERTICAL);
            AlertDialog.Builder warning = new AlertDialog.Builder(CategoryPageActivity.this);
            warning.setTitle("Warning!");
            TextView warningMessage = new TextView(this);
            warningMessage.setText("WARNING: Your category budgets exceed your monthly income. Please Consider Reducing Some Category Budgets");
            warningMessage.setGravity(Gravity.CENTER);
            layout.addView(warningMessage);
            warning.setPositiveButton("Ok", null);
            warning.setView(layout);
            AlertDialog dialog = warning.create();
            dialog.show();
        }
        addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context =  getApplicationContext();
                LinearLayout layout = new LinearLayout(context);
                layout.setOrientation(LinearLayout.VERTICAL);
                AlertDialog.Builder addCat = new AlertDialog.Builder(CategoryPageActivity.this);
                addCat.setTitle("Add a Category");
                final EditText input = new EditText(context);
                layout.addView(input);
                input.setHint("Category Name");
                input.setGravity(Gravity.CENTER);
                final EditText budget = new EditText(context);
                budget.setHint("Category Budget");
                budget.setGravity(Gravity.CENTER);
                layout.addView(budget);
                addCat.setView(layout);

                addCat.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String month = getMonth();
                        String year = getYear();

                        if (categoriesMessage.length <= 6) {
                            if (!input.getText().toString().equals("") && !budget.getText().toString().equals("")) {
                                String response = client.sendMessage("insertcategories;" + passable + " " + input.getText().toString() + " " + budget.getText().toString() + " " + month + " " + year);
                                if (response.equals("Category Successfully Added")) {
                                    toastMessage("Category Successfully Added.");
                                    finish();
                                    startActivity(getIntent());
                                } else {
                                    toastMessage("Category Already Exists. Choose A Different Name");
                                }
                            } else {
                                toastMessage("Make Sure a Category Name and Budget is Entered! ");
                            }
                        } else {
                            toastMessage("Unable to Insert Category. Maximum Number of Categories Reached");
                        }
                    }
                });
                addCat.setNegativeButton("Cancel", null);
                AlertDialog dialog = addCat.create();
                dialog.show();
            }
        });

        deleteCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context =  getApplicationContext();
                LinearLayout layout = new LinearLayout(context);
                layout.setOrientation(LinearLayout.VERTICAL);
                TextView warning = new TextView(CategoryPageActivity.this);
                warning.setText("Warning: Deleting a Category will also delete any Transactions associated with that Category");
                warning.setGravity(Gravity.CENTER);
                String category = client.sendMessage("getcategories;" + passable);
                System.out.println("response from client: " + category);
                layout.addView(warning);

                String[] categoriesMessage = category.split(";");
                String[][] categories = new String[categoriesMessage.length][2];
                String[] arrayCategorySpinner = new String[categoriesMessage.length];
                for (int i = 0; i < categoriesMessage.length; i++) {
                    categories[i] = categoriesMessage[i].split(" ");
//                    System.out.println("Categories Hopefully: " + categories[i][0]);
                    arrayCategorySpinner[i] = categories[i][0];
                }

                //Take to the delete category page.
                AlertDialog.Builder deleteCat = new AlertDialog.Builder(CategoryPageActivity.this);
                deleteCat.setTitle("Delete Category");
                final Spinner categorySpinner = new Spinner(CategoryPageActivity.this);
                final ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>( CategoryPageActivity.this,
                        android.R.layout.simple_spinner_item, arrayCategorySpinner);
                categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                categorySpinner.setAdapter(categoryAdapter);
                categorySpinner.setGravity(Gravity.CENTER);
                layout.addView(categorySpinner);
                deleteCat.setView(layout);

                categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                        Object item = adapterView.getItemAtPosition(position);
                        String type = item.toString();
                        cat = type;
                        System.out.println("Category Selected: " + cat);
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                });

                deleteCat.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!cat.equals("Food") && !cat.equals("Clothes") && !cat.equals("Groceries")) {
                            String response = client.sendMessage("deletecategories;" + passable + " " + cat);
                            System.out.println(response);
                            if (response.equals("Category Successfully Deleted")) {
                                toastMessage("Category Successfully Deleted");
                                finish();
                                startActivity(getIntent());
                            } else {
                                toastMessage("Unable to Delete Category");
                            }
                        } else {
                            toastMessage("Unable to Delete Default Category!");
                        }
                    }
                });
                deleteCat.setNegativeButton("Cancel", null);

                AlertDialog dialog = deleteCat.create();
                dialog.show();
            }
        });
    }

    private void toastMessage(String message) {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();

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

        // navigation bar
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        Intent a = new Intent(CategoryPageActivity.this, MainPageActivity.class);
                        a.putExtra("username", passable);
                        startActivity(a);
                        break;
                    case R.id.nav_report:
                        Intent b = new Intent(CategoryPageActivity.this, Report.class);
                        b.putExtra("username", passable);
                        startActivity(b);
                        break;
                    case R.id.nav_setting:
                        Intent c = new Intent(CategoryPageActivity.this,SettingActivity.class);
                        c.putExtra("username", passable);
                        startActivity(c);
                        break;
                    case R.id.nav_goal:
                        Intent d = new Intent(CategoryPageActivity.this, GoalActivity.class);
                        d.putExtra("username", passable);
                        startActivity(d);
                        break;
                    case R.id.nav_category:
                        break;
                }
                return false;
            }
        });

    }
}
