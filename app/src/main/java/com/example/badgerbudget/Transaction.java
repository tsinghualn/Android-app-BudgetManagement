package com.example.badgerbudget;

import com.example.badgerbudget.data.model.Client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class Transaction  extends Report{

    String passable;
    Client client;
    String transaction;
    String userInfo;

    ArrayList<String[]> typeExpense = new ArrayList<>();
    ArrayList<String[]> typeIncome = new ArrayList<>();
    ArrayList<String[]> wholeTransaction = new ArrayList<>();
    double salary;


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
    LinkedHashMap<String, Double> expenseByMonth = new LinkedHashMap<>();
    LinkedHashMap<String, Double> incomeByMonth = new LinkedHashMap<>();

    LinkedHashMap<String, Double> categList = new LinkedHashMap<>();

    // new constructor method to initialize
    public Transaction(String username){

        client = new Client(6868, "10.0.2.2");

        this.passable = username;

        transaction = client.sendMessage("gettransactions;" + passable);
        userInfo = client.sendMessage("getuserinfo;" + passable);
        setSalary(userInfo);
        setTransaction(transaction);


    }

    // For testing
    public Transaction(){

        //transaction = client.sendMessage("gettransactions;" + passable);
        //userInfo = client.sendMessage("getuserinfo;" + passable);
        //setSalary(userInfo);
        //setTransaction(transaction);
        transaction = "";
        userInfo = "";

    }

    public void setValues(){

    }

    public void setSalary(String userInfo){

        if(!userInfo.equals("")){

            String[] oneUser = userInfo.split(" ");

            salary = Double.parseDouble(oneUser[4]);
            // userID [0]
            // PASSWORD [1]
            // SQ1-A [2]
            // SQ2-A [3]
            // salary [4]
            // date [5]
            // userName [6]

        }

    }


    public double setSalaryInRange(String sMonth, String sYear, String eMonth, String eYear){
        Integer sMonthInt = MONTHMAP.get(sMonth);
        Integer eMonthInt = MONTHMAP.get(eMonth);
        Integer sYearInt = Integer.parseInt(sYear.trim());
        Integer eYearInt = Integer.parseInt(eYear.trim());

        double totalSalary = 0.0;

        // for each year from start year to end year
        for(int y = sYearInt; y <= eYearInt; y++){
            int sm = 0;
            int em = 0;
            // If y == sYear == eYear -> sMonth ~ eMonth
            // if y == sYear && y < eYear -> sMonth ~ 12
            // if y > sYear && y < eYear -> 1 ~ 12
            // if y > sYear && y == eYear -> 1 ~ eMonth
            if(y == sYearInt && y == eYearInt) {
                sm = sMonthInt;
                em = eMonthInt;
            } else if (y == sYearInt && y < eYearInt) {
                sm = sMonthInt;
                em = 12;
            } else if (y > sYearInt && y < eYearInt) {
                sm = 1;
                em = 12;
            } else if (y > sYearInt && y == eYearInt) {
                sm = 1;
                em = eMonthInt;
            }

            // sum up salary
            for(int m = sm; m <= em; m++){
                totalSalary = totalSalary + salary;
            }

        }

        return totalSalary;

    }

    public void setTransaction(String wholeTrans){
        String[] transList;

        if (wholeTrans != null && !wholeTrans.isEmpty()){

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
                wholeTransaction.add(oneTrans);

                String type = oneTrans[1];
                if(type.equals("Expense")){
                    typeExpense.add(oneTrans);

                } else if(type.equals("Income")){
                    typeIncome.add(oneTrans);
                }
            }

        } else {
            // there is no transaction for this user
            typeExpense = new ArrayList<>();
            typeIncome = new ArrayList<>();
            wholeTransaction = new ArrayList<>();
            // transList = null;
/*            wholeTransaction = null;
            typeExpense = null;
            typeIncome = null;*/
            // later, properly handle it by returning false or something..
        }

    }

    public ArrayList<String[]> setListInRange(String sMonth, String sYear, String eMonth, String eYear, String type) {

        ArrayList<String[]> transInRange = new ArrayList<>();
        ArrayList<String[]> tempTransaction = new ArrayList<>();
        Integer sMonthInt = MONTHMAP.get(sMonth);
        Integer eMonthInt = MONTHMAP.get(eMonth);
        Integer sYearInt = Integer.parseInt(sYear.trim());
        Integer eYearInt = Integer.parseInt(eYear.trim());

        if (type.equals("expense")){
            tempTransaction = typeExpense;
        } else if (type.equals("income")){
            tempTransaction = typeIncome;
        } else if (type.equals("all")){
            tempTransaction = wholeTransaction;
        }

        for(String[] each: tempTransaction){

            // orderID [0]
            // type [1]
            // amount[2]
            // date [3]
            // month [4]
            // year [5]
            // category [6]

            int month = MONTHMAP.get(each[4]);
            int year = Integer.parseInt(each[5].trim());

            // year of transaction is in the range
            if(sYearInt.intValue() <= year && year <= eYearInt.intValue()){

                // the range is for same year (sYear = eYear = year)
                if(sYearInt.intValue() == year && eYearInt.intValue() == year) {
                    if(sMonthInt.intValue() <= month && month <= eMonthInt.intValue()){
                        transInRange.add(each);
                    }
                } else {
                    // year of transaction = start year
                    if(sYearInt.intValue() == year) {
                        if (sMonthInt.intValue() <= month && month <= 12) {
                            // month of transaction is after start month of the range
                            transInRange.add(each);
                        }
                        // year of transaction is not same with sYear or eYear, but in the range
                    } else if(sYearInt.intValue() < year && year < eYearInt.intValue()){
                        if (1 <= month && month <= 12){
                            // valid month
                            transInRange.add(each);
                        }
                    } else if(eYearInt.equals(year)){
                        if(eMonthInt.intValue() >= month && month >= 1){
                            // less than endMonth
                            transInRange.add(each);
                        }
                    }
                }
            }
        }
        return transInRange;
    }



/*    public ArrayList<String[]> setwholeInRange(String sMonth, String sYear, String eMonth, String eYear) {

        ArrayList<String[]> wholeTransInRange = new ArrayList<>();
        Integer sMonthInt = MONTHMAP.get(sMonth);
        Integer eMonthInt = MONTHMAP.get(eMonth);
        Integer sYearInt = Integer.parseInt(sYear.trim());
        Integer eYearInt = Integer.parseInt(eYear.trim());

        for(String[] each: wholeTransaction){

            // orderID [0]
            // type [1]
            // amount[2]
            // date [3]
            // month [4]
            // year [5]
            // category [6]

            int month = MONTHMAP.get(each[4]);

            int year = Integer.parseInt(each[5].trim());


            // year of transaction is in the range
            if(sYearInt.intValue() <= year && year <= eYearInt.intValue()){

                // the range is for same year (sYear = eYear = year)
                if(sYearInt.intValue() == year && eYearInt.intValue() == year) {
                    if(sMonthInt.intValue() <= month && month <= eMonthInt.intValue()){
                        wholeTransInRange.add(each);
                    }
                } else {
                    // year of transaction = start year
                    if(sYearInt.intValue() == year) {
                        if (sMonthInt.intValue() <= month && month <= 12) {
                            // month of transaction is after start month of the range
                            wholeTransInRange.add(each);
                        }
                        // year of transaction is not same with sYear or eYear, but in the range
                    } else if(sYearInt.intValue() < year && year < eYearInt.intValue()){
                        if (1 <= month && month <= 12){
                            // valid month
                            wholeTransInRange.add(each);
                        }
                    } else if(eYearInt.equals(year)){
                        if(eMonthInt.intValue() >= month && month >= 1){
                            // less than endMonth
                            wholeTransInRange.add(each);
                        }
                    }
                }
            }
        }
        return wholeTransInRange;
    }*/

/*

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
*/



    public LinkedHashMap<String, Double> getExpenseByMonth(ArrayList<String[]> expenseInRange, String sMonth, String sYear, String eMonth, String eYear){

        Integer sMonthInt = MONTHMAP.get(sMonth);
        Integer eMonthInt = MONTHMAP.get(eMonth);
        Integer sYearInt = Integer.parseInt(sYear.trim());
        Integer eYearInt = Integer.parseInt(eYear.trim());

        Set<String> monthKeySet = MONTHMAP.keySet();

        for(int y = sYearInt; y <= eYearInt; y++){

            int sm = 0;
            int em = 0;
            // If y == sYear == eYear -> sMonth ~ eMonth
            // if y == sYear && y < eYear -> sMonth ~ 12
            // if y > sYear && y < eYear -> 1 ~ 12
            // if y > sYear && y == eYear -> 1 ~ eMonth
            if(y == sYearInt && y == eYearInt) {
                sm = sMonthInt;
                em = eMonthInt;
            } else if (y == sYearInt && y < eYearInt) {
                sm = sMonthInt;
                em = 12;
            } else if (y > sYearInt && y < eYearInt) {
                sm = 1;
                em = 12;
            } else if (y > sYearInt && y == eYearInt) {
                sm = 1;
                em = eMonthInt;
            }

            // for each month
            for(int m = sm; m <= em; m++){

                // for each month, find matching month and put as a key
                for(String monthKey:monthKeySet){
                    if(m == MONTHMAP.get(monthKey)){
                        String yearKey = Integer.toString(y);
                        String monthYearPair = monthKey.concat(yearKey);
                        expenseByMonth.put(monthYearPair, 0.0);
                    }
                }
            }
        }

        for(String[] eachExpense: expenseInRange){

            // get month-year pair to use as a key

            String m = eachExpense[4];
            String y = eachExpense[5];
            String monthYearPair = m.concat(y);


            // amount in the transaction
            Double amount = Double.parseDouble(eachExpense[2]);

            Double prevExpense = expenseByMonth.get(monthYearPair);
            Double currentExpense = prevExpense + amount;
            expenseByMonth.put(monthYearPair, currentExpense);
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

    public LinkedHashMap<String, Double> getIncomeByMonth(ArrayList<String[]> incomeInRange){

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


