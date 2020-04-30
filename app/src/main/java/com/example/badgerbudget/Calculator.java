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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Calculator extends AppCompatActivity {

    double userPrice;
    double tipPerc, taxPerc;
    String state;
    double tipAmount;
    double taxAmount;

    Spinner tip, tax;
    Button btn_tip, btn_tax;

    EditText text_amount_tip, text_amount_tax;
    TextView tipDisp, taxDisp;
    TextView taxPercDisp;

    List<String> statesList = new ArrayList<String>();
    List<Double> taxPercList = new ArrayList<Double>();
    Map<String, Double> stateTaxList = new HashMap<String,Double>();

    BufferedReader reader;
    InputStream is;

    private double double_amount_tip, double_amount_tax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        setStateTax();
        showDropDown();
        getTipPerc();
        getTaxPerc();


    }

    public void setStateTax(){
        taxPercDisp = (TextView) findViewById(R.id.taxPercDisp);
        is = this.getResources().openRawResource(R.raw.statetax);
        reader = new BufferedReader(new InputStreamReader(is));
        String data = "";
        if(is != null){
            try{
                while((data = reader.readLine()) != null){
                    // get state
                    statesList.add(data);
                    data = reader.readLine();
                    // tax percentage
                    taxPercList.add(Double.valueOf(data));
                    // empty line
                    reader.readLine();
                }
                is.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        // if lists are filled after above code
        if(!(statesList.isEmpty() && taxPercList.isEmpty())){
            for(int i=0; i < statesList.size(); i++){
                stateTaxList.put(statesList.get(i), taxPercList.get(i));
            }
        }
    }


    private void showDropDown(){

        // tip dropdown
        tip = (Spinner) findViewById(R.id.tipDropDown);
        ArrayAdapter<String> tipAdapter = new ArrayAdapter<String>(Calculator.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Tip));
        tipAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // show adapter in the spinner
        tip.setAdapter(tipAdapter);

        // tax dropdown
        tax = (Spinner) findViewById(R.id.taxDropDown);
        ArrayAdapter<String> taxAdapter = new ArrayAdapter<String>(Calculator.this,
                android.R.layout.simple_list_item_1, statesList);
        taxAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // show adapter in the spinner
        tax.setAdapter(taxAdapter);

    }

    public void onClick_tip(View view){

        // get user input amount
        text_amount_tip = (EditText) findViewById(R.id.enterCost1);
        String string_amount_tip = "";
        if(isEmpty(text_amount_tip)){
            string_amount_tip = "0.0";
        } else {
            string_amount_tip = text_amount_tip.getText().toString();
        }

        double_amount_tip = Double.valueOf(string_amount_tip);

        tipAmount = double_amount_tip * tipPerc;

        tipDisp = (TextView) findViewById(R.id.tipDisp);
        tipDisp.setText("$ " + String.format("%.2f", tipAmount));

    }

    private boolean isEmpty(EditText eText) {

        return eText.getText().toString().trim().length() == 0;

    }

    public void onClick_tax(View view){

        text_amount_tax = (EditText) findViewById(R.id.enterCost2);
        String string_amount_tax = "";

        if(isEmpty(text_amount_tax)){
            string_amount_tax = "0.0";
        } else {
            string_amount_tax = text_amount_tax.getText().toString();
        }
        double_amount_tax = Double.valueOf(string_amount_tax);
        taxAmount = double_amount_tax * taxPerc;
        double totalAmount = taxAmount + double_amount_tax;

        taxPercDisp.setText(String.format("%.2f", taxPerc) + "%");
        taxDisp = (TextView) findViewById(R.id.taxDisp);
        taxDisp.setText("$ " + String.format("%.2f", totalAmount));

    }


     //get the selected dropdown list value
    public void getTipPerc() {
        tip.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tempTip = tip.getSelectedItem().toString();
                tempTip = tempTip.substring(0, tempTip.length() - 1);
                tipPerc = 0.01 * Double.valueOf(tempTip);

                Toast.makeText(getApplicationContext(), "SELECTED " + tempTip, Toast.LENGTH_SHORT). show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    /* get tax percent
    https://www.salestaxinstitute.com/resources/rates
     */
    public void getTaxPerc() {

        tax.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tempState = tax.getSelectedItem().toString();
                Set<String> keys = stateTaxList.keySet();
/*                for(String key : keys){
                    if(tempState.equals(key)){
                        taxPerc = 0.01 * Double.valueOf(stateTaxList.get(key));
                        break;
                    }
                }*/
                taxPerc = findTaxPerc(tempState, keys);
                Toast.makeText(getApplicationContext(), "SELECTED " + taxPerc, Toast.LENGTH_SHORT). show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public double findTaxPerc(String state, Set<String> keys){

        double tempTaxPerc = 0.0;
        for(String key : keys){
            if(state.equals(key)){
                tempTaxPerc = 0.01 * Double.valueOf(stateTaxList.get(key));
                break;
            }
        }
        return tempTaxPerc;
    }




}
