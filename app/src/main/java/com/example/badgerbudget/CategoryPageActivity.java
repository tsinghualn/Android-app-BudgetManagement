package com.example.badgerbudget;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.badgerbudget.data.model.Client;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import org.w3c.dom.Text;

public class CategoryPageActivity extends AppCompatActivity {
    TableLayout categoryTable;
    TextView salary;
    Button addCategory;
    Button deleteCategory;
    String passable;
    Client client = new Client(6868, "10.0.2.2");
    String cat = "";

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

        TableRow foodRow = new TableRow(this);
        TextView food = new TextView(this);
        food.setText("Food");
        TextView foodBudget = new TextView(this);
        foodBudget.setText("100");
        foodRow.addView(food);
        foodRow.addView(foodBudget);
        categoryTable.addView(foodRow);

        TableRow groceriesRow = new TableRow(this);
        TextView groceries = new TextView(this);
        groceries.setText("Groceries");
        TextView groceriesBudget = new TextView(this);
        groceriesBudget.setText("100");
        groceriesRow.addView(groceries);
        groceriesRow.addView(groceriesBudget);
        categoryTable.addView(groceriesRow);

        TableRow clothesRow = new TableRow(this);
        TextView clothes = new TextView(this);
        clothes.setText("Clothes");
        TextView clothesBudget = new TextView(this);
        clothesBudget.setText("50");
        clothesRow.addView(clothes);
        clothesRow.addView(clothesBudget);
        categoryTable.addView(clothesRow);


        String category = client.sendMessage("getcategories;" + passable);

        if (!category.equals("")) {
            String[] parsed = category.split(" ");
            categoryTable.setColumnStretchable(0, true);
            categoryTable.setColumnStretchable(1, true);
            String[] categoriesMessage = category.split(";");
            String[][] categories = new String[categoriesMessage.length][2];
            for (int i = 0; i < categoriesMessage.length; i++) {
                if (categoriesMessage.length != 0) {
                    categories[i] = categoriesMessage[i].split(" ");
                    TableRow tr = new TableRow(this);
                    TextView catname = new TextView(this);
                    if (!categories[i].equals(null)) {
                        catname.setText(categories[i][0]);
                        TextView budget = new TextView(this);
                        budget.setText(categories[i][1]);
                        tr.addView(catname);
                        tr.addView(budget);
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


        salary.setText("$10000000");

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
                final EditText budget = new EditText(context);
                budget.setHint("Category Budget");
                layout.addView(budget);
                addCat.setView(layout);
                addCat.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!input.getText().toString().equals("") && !budget.getText().toString().equals("")) {
                            client.sendMessage("insertcategories;" + passable + " " + input.getText().toString() + " " + budget.getText().toString());
                            toastMessage("Category Successfully Added.");
                            finish();
                            startActivity(getIntent());
                        } else {
                            toastMessage("Please Insert a Category and Set a Budget ");
                        }
                    }
                });
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
                String category = client.sendMessage("getcategories;" + passable);
                System.out.println("response from client: " + category);

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
                        String response = client.sendMessage("deletecategories;" + passable + " " + cat);
                        System.out.println(response);
                        if (response.equals("Category Successfully Deleted")) {
                            toastMessage("Category Successfully Deleted");
                            finish();
                            startActivity(getIntent());
                        } else {
                            toastMessage("Unable to Delete Category");
                        }
                    }
                });

                AlertDialog dialog = deleteCat.create();
                dialog.show();
            }
        });
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
                        // Intent d = new Intent(MainPageActivity.this, GoalsActivity.class);
                        //d.putExtra("username", username);

                        // startActivity(d)
                        break;
                    case R.id.nav_category:
                        //Intent e = new Intent(CategoryPageActivity.this, CategoryPageActivity.class);
                        //startActivity(e);
                        break;
                }
                return false;
            }
        });

    }
}
