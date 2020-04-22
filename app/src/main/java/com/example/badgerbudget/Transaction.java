package com.example.badgerbudget;

import com.example.badgerbudget.data.model.Client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Transaction  extends Report{

    String passable;
    Client client;
    String wholeTrans;

    ArrayList<String[]> typeExpense = new ArrayList<>();
    ArrayList<String[]> typeIncome = new ArrayList<>();

    ArrayList<String[]> expenseInRange = new ArrayList<>();

    Double totalIncome;


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
    //HashMap<String, Double> categList, expenseByMonth;

    String[] categories = {"categ1", "categ2", "categ3"};
    double[] catExpense={300,200,600};

    String[] months={"January","February","March"};
    double[] monExpense={1000,600,1500};
    double[] monIncome={2000,2400,3000};


    // new constructor method to initialize
    public Transaction(String username){

        client = new Client(6868, "10.0.2.2");
        this.passable = username;
        wholeTrans = client.sendMessage("gettransactions;" + passable);
        setTransaction();

    }

    public void setTransaction(){

        String[] transList = wholeTrans.split(";");

        for (int i = 0; i < transList.length; i++){

            String[] oneTrans = transList[i].split(" ");

            // orderID [0]
            // type [1]
            // amount[2]
            // category [3]
            // month [4]
            // year [5]

            if(oneTrans[2].equals("Expense")){
                typeExpense.add(oneTrans);

            } else if(oneTrans[2].equals("Income")){
                typeIncome.add(oneTrans);
            }
        }
    }


    public void setTransactionInRange(String sMonth, String sYear, String eMonth, String eYear){

        Integer sMonthInt = MONTHMAP.get(sMonth);
        Integer eMonthInt = MONTHMAP.get(eMonth);
        Integer sYearInt = Integer.parseInt(sYear.trim());
        Integer eYearInt = Integer.parseInt(eYear.trim());

        for(String[] each: typeExpense){

            String amount = each[2];
            String category = each[3];
            Integer month = MONTHMAP.get(each[4]);
            Integer year = Integer.parseInt(each[5].trim());

            // year of transaction is in the range
            if(sYearInt <= year && year <= eYearInt){

                // the range is for same year (sYear = eYear = year)
                if(sYearInt == year && eYearInt == year) {
                    if(sMonthInt <= month && month <= eMonthInt){
                        expenseInRange.add(each);
                    }
                } else {
                    // year of transaction = start year
                    if(sYearInt == year) {
                        if (sMonthInt <= month && month <= 12) {
                            // month of transaction is after start month of the range
                            expenseInRange.add(each);
                        }
                        // year of transaction is not same with sYear or eYear, but in the range
                    } else if(sYearInt < year && year < eYearInt){
                        if (1 <= month && month <= 12){
                            // valid month
                            expenseInRange.add(each);
                        }
                    } else if(eYearInt == year){
                        if(eMonthInt >= month && month >= 1){
                            // less than endMonth
                            expenseInRange.add(each);
                        }
                    }
                }
            }
        }
    }

    public Map<String, Double> getExpenseByMonth(){

        for(String[] eachExpense: expenseInRange){

            // get month-year pair to use as a key
            String monthYearPair = eachExpense[4];
            monthYearPair.concat(eachExpense[5]);

            // amount in the transaction
            Double amount = Double.parseDouble(eachExpense[2]);

            if(expenseByMonth.containsKey(monthYearPair)){
                // already exist in the hashmap
                Double prevExpense = expenseByMonth.get(monthYearPair);
                Double currentExpense = prevExpense + amount;
                expenseByMonth.put(monthYearPair, currentExpense);
            } else {
                // doesn't exist in the hashmap
                expenseByMonth.put(monthYearPair, amount);
            }
        }
        return expenseByMonth;
    }

    public Map<String, Double> getCategList(){
        for(String[] eachExpense: expenseInRange){

            String category = eachExpense[3];

            // amount in the transaction
            Double amount = Double.parseDouble(eachExpense[2]);

            if(expenseByMonth.containsKey(category)){
                // already exist in the hashmap
                Double prevExpense = expenseByMonth.get(category);
                Double currentExpense = prevExpense + amount;
                expenseByMonth.put(category, currentExpense);
            } else {
                // doesn't exist in the hashmap
                expenseByMonth.put(category, amount);
            }
        }
        return categList;
    }

/*

    public Double getTotalIncome(){

        for(String[] eachIncome: )
        return totalIncome;

    }
*/

}


