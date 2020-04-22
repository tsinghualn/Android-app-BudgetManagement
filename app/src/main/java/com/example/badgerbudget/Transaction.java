package com.example.badgerbudget;

import com.example.badgerbudget.data.model.Client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Transaction  extends Report{

    String passable;
    Client client;
    String transaction;

    ArrayList<String[]> typeExpense = new ArrayList<>();
    ArrayList<String[]> typeIncome = new ArrayList<>();


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
    HashMap<String, Double> expenseByMonth = new HashMap<>();
    HashMap<String, Double> incomeByMonth = new HashMap<>();

    HashMap<String, Double> categList = new HashMap<>();

    // new constructor method to initialize
    public Transaction(String username){

        client = new Client(6868, "10.0.2.2");
        this.passable = username;
        transaction = client.sendMessage("gettransactions;" + passable);
        setTransaction(transaction);

    }

    public void setTransaction(String wholeTrans){
        String[] transList;

        if (!wholeTrans.equals("")){

            transList = wholeTrans.split(";");

            for (int i = 0; i < transList.length; i++){

                String[] oneTrans = transList[i].split(" ");

                // orderID [0]
                // type [1]
                // amount[2]
                // date [3]
                // month [4]
                // year [5]
                // category [6]

                String type = oneTrans[1];
                if(type.equals("Expense")){
                    typeExpense.add(oneTrans);

                } else if(type.equals("Income")){
                    typeIncome.add(oneTrans);
                }
            }

        } else {
            // there is no transaction for this user
            transList = null;
            typeExpense = null;
            typeIncome = null;
            // later, properly handle it by returning false or something..
        }

    }


    public ArrayList<String[]> setTransactionInRange(String sMonth, String sYear, String eMonth, String eYear){

        ArrayList<String[]> expenseInRange = new ArrayList<>();
        Integer sMonthInt = MONTHMAP.get(sMonth);
        Integer eMonthInt = MONTHMAP.get(eMonth);
        Integer sYearInt = Integer.parseInt(sYear.trim());
        Integer eYearInt = Integer.parseInt(eYear.trim());

        for(String[] each: typeExpense){

            // orderID [0]
            // type [1]
            // amount[2]
            // date [3]
            // month [4]
            // year [5]
            // category [6]

            String amount = each[2];
            String category = each[6];
            int month = MONTHMAP.get(each[4]);

            int year = Integer.parseInt(each[5].trim());


            // year of transaction is in the range
            if(sYearInt.intValue() <= year && year <= eYearInt.intValue()){

                // the range is for same year (sYear = eYear = year)
                if(sYearInt.intValue() == year && eYearInt.intValue() == year) {
                    if(sMonthInt.intValue() <= month && month <= eMonthInt.intValue()){
                        expenseInRange.add(each);
                    }
                } else {
                    // year of transaction = start year
                    if(sYearInt.intValue() == year) {
                        if (sMonthInt.intValue() <= month && month <= 12) {
                            // month of transaction is after start month of the range
                            expenseInRange.add(each);
                        }
                        // year of transaction is not same with sYear or eYear, but in the range
                    } else if(sYearInt.intValue() < year && year < eYearInt.intValue()){
                        if (1 <= month && month <= 12){
                            // valid month
                            expenseInRange.add(each);
                        }
                    } else if(eYearInt.equals(year)){
                        if(eMonthInt.intValue() >= month && month >= 1){
                            // less than endMonth
                            expenseInRange.add(each);
                        }
                    }
                }
            }
        }
        return expenseInRange;
    }

    public HashMap<String, Double> getExpenseByMonth(ArrayList<String[]> expenseInRange){


        for(String[] eachExpense: expenseInRange){

            // get month-year pair to use as a key
            String m = eachExpense[4];
            String y = eachExpense[5];
            String monthYearPair = m.concat(y);

            // amount in the transaction
            Double amount = Double.parseDouble(eachExpense[2]);

            if(expenseByMonth != null && expenseByMonth.containsKey(monthYearPair)){
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


    public HashMap<String, Double> getCategList(ArrayList<String[]> expenseInRange){
        for(String[] eachExpense: expenseInRange){

            String category = eachExpense[6];

            // amount in the transaction
            Double amount = Double.parseDouble(eachExpense[2]);

            if(categList.containsKey(category)){
                // already exist in the hashmap
                Double prevExpense = categList.get(category);
                Double currentExpense = prevExpense + amount;
                categList.put(category, currentExpense);
            } else {
                // doesn't exist in the hashmap
                categList.put(category, amount);
            }
        }
        return categList;
    }



    public ArrayList<String[]> setIncomeInRange(String sMonth, String sYear, String eMonth, String eYear){

        ArrayList<String[]> incomeInRange = new ArrayList<>();
        Integer sMonthInt = MONTHMAP.get(sMonth);
        Integer eMonthInt = MONTHMAP.get(eMonth);
        Integer sYearInt = Integer.parseInt(sYear.trim());
        Integer eYearInt = Integer.parseInt(eYear.trim());

        for(String[] each: typeIncome){

            // orderID [0]
            // type [1]
            // amount[2]
            // date [3]
            // month [4]
            // year [5]
            // category [6]

            String amount = each[2];
            String category = each[6];
            int month = MONTHMAP.get(each[4]);

            int year = Integer.parseInt(each[5].trim());

            // year of transaction is in the range
            if(sYearInt.intValue() <= year && year <= eYearInt.intValue()){

                // the range is for same year (sYear = eYear = year)
                if(sYearInt.intValue() == year && eYearInt.intValue() == year) {
                    if(sMonthInt.intValue() <= month && month <= eMonthInt.intValue()){
                        incomeInRange.add(each);
                    }
                } else {
                    // year of transaction = start year
                    if(sYearInt.intValue() == year) {
                        if (sMonthInt.intValue() <= month && month <= 12) {
                            // month of transaction is after start month of the range
                            incomeInRange.add(each);
                        }
                        // year of transaction is not same with sYear or eYear, but in the range
                    } else if(sYearInt.intValue() < year && year < eYearInt.intValue()){
                        if (1 <= month && month <= 12){
                            // valid month
                            incomeInRange.add(each);
                        }
                    } else if(eYearInt.equals(year)){
                        if(eMonthInt.intValue() >= month && month >= 1){
                            // less than endMonth
                            incomeInRange.add(each);
                        }
                    }
                }
            }
        }
        return incomeInRange;
    }


    public HashMap<String, Double> getIncomeByMonth(ArrayList<String[]> incomeInRange){


        for(String[] eachIncome: incomeInRange){

            // get month-year pair to use as a key
            String m = eachIncome[4];
            String y = eachIncome[5];
            String monthYearPair = m.concat(y);

            // amount in the transaction
            Double amount = Double.parseDouble(eachIncome[2]);

            if(incomeByMonth != null && incomeByMonth.containsKey(monthYearPair)){
                // already exist in the hashmap
                Double prev = incomeByMonth.get(monthYearPair);
                Double current = prev + amount;
                incomeByMonth.put(monthYearPair, current);
            } else {
                // doesn't exist in the hashmap
                incomeByMonth.put(monthYearPair, amount);
            }
        }
        return incomeByMonth;
    }



}


