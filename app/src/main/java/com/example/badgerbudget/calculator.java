package com.example.badgerbudget;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;


public class calculator extends AppCompatActivity {

    private double userPrice;
    private double perc;
    private String state;
    private double tipAmount;
    private double taxAmount;

    private Spinner tip, tax;
    private Button btn_tip, btn_tax;

    EditText text_amount_tip;
    TextView tipDisp;

    private double double_amount_tip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);


        showDropDown();
        getTipPerc();

    }

    private void showDropDown(){

        tip = (Spinner) findViewById(R.id.tipDropDown);
        ArrayAdapter<String> tipAdapter = new ArrayAdapter<String>(calculator.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Tip));
        tipAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // show adapter in the spinner
        tip.setAdapter(tipAdapter);

    }

    public void onClick_tip(View view){

        // get user input amount
        text_amount_tip = (EditText) findViewById(R.id.enterCost1);
        String string_amount_tip = text_amount_tip.getText().toString();
        double_amount_tip = Double.valueOf(string_amount_tip);


        tipAmount = double_amount_tip * (0.01 *perc);

        tipDisp = (TextView) findViewById(R.id.tipDisp);
        tipDisp.setText(String.valueOf(tipAmount));

    }

    public void onClick_tax(View view){

    }


     //get the selected dropdown list value
    private void getTipPerc() {
        tip.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tempTip = tip.getSelectedItem().toString();
                tempTip = tempTip.substring(0, tempTip.length() - 1);
                perc = Double.valueOf(tempTip);

                Toast.makeText(getApplicationContext(), "SELECTED " + tempTip, Toast.LENGTH_SHORT). show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }






}
