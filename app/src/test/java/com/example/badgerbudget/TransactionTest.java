package com.example.badgerbudget;

import com.example.badgerbudget.data.model.Client;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static org.junit.Assert.*;

public class TransactionTest {
    String sMonth;
    String sYear;
    String eMonth;
    String eYear;

    @Test
    public void setSalary() {

        Transaction transaction = new Transaction();
        String userInfo1 = "user00 pass red a 1000 1/1/1111 test00";
        transaction.setSalary(userInfo1);
        assertEquals( "Integer userInput value is matched", 1000.0, transaction.salary,0.01);

        String userInfo2 = "user00 pass red a 500.55 1/1/1111 test00";
        transaction.setSalary(userInfo2);
        assertEquals( "Double userInput value is matched", 500.55, transaction.salary,0.01);

    }

    @Test
    public void setSalaryInRange() {

        Transaction transaction = new Transaction();
        transaction.salary = 100.0;

        // If y == sYear == eYear -> sMonth ~ eMonth
        // if y == sYear && y < eYear -> sMonth ~ 12
        // if y > sYear && y < eYear -> 1 ~ 12
        // if y > sYear && y == eYear -> 1 ~ eMonth

        sMonth = "January";
        sYear = "2019";
        eMonth = "April";
        eYear = "2019";
        double totalSalary1 = transaction.setSalaryInRange(sMonth,sYear,eMonth,eYear);
        assertEquals("Test for sum of salary in range", 400.0, totalSalary1, 0.01);


        sMonth = "January";
        sYear = "2018";
        eMonth = "April";
        eYear = "2020";
        double totalSalary2 = transaction.setSalaryInRange(sMonth,sYear,eMonth,eYear);
        assertEquals("Test for sum of salary in range with all if-else visited", 2800.0, totalSalary2, 0.01);


        transaction.salary = 563.32;
        sMonth = "January";
        sYear = "2018";
        eMonth = "April";
        eYear = "2020";
        double totalSalary3 = transaction.setSalaryInRange(sMonth,sYear,eMonth,eYear);
        assertEquals("Test for sum of decimal value of salary in range with all if-else visited", 15772.96, totalSalary3, 0.01);



    }

    @Test
    public void setTransaction() {
        Transaction transaction = new Transaction();
        String wholeTrans ="1 Expense 50 4/22/20 April 2020 Food;2 Expense 50 4/22/20 April 2020 book;1 Income 100 4/22/20 April 2020 Food;";
        transaction.setTransaction(wholeTrans);

        assertEquals("There should be two expense type transaction lists",2, transaction.typeExpense.size());
        assertEquals("The amount of expense", "50", transaction.typeExpense.get(0)[2]);
        assertEquals("Month", "April", transaction.typeExpense.get(0)[4]);
        assertEquals("Year", "2020", transaction.typeExpense.get(1)[5]);
        assertEquals("Category of expense 2", "book", transaction.typeExpense.get(1)[6]);
        assertEquals("There should be one income transaction list", 1, transaction.typeIncome.size());

        String emptyTrans = "";
        transaction.setTransaction(emptyTrans);

        // not null, instead empty arraylist
        assertNotNull(transaction.typeExpense);
        assertEquals(transaction.typeExpense, new ArrayList<String[]>());
        assertNotNull(transaction.typeIncome);
        assertEquals(transaction.typeIncome, new ArrayList<String[]>());

    }

    @Test
    public void setListInRange() {

        Transaction transaction = new Transaction();
        String wholeTrans = "1 Expense 50 4/22/20 April 2020 Food;2 Expense 50 4/22/20 April 2020 book;1 Income 100 4/22/20 April 2020 Food;";
        transaction.setTransaction(wholeTrans);

        // testcase 1: different months, different years
        ArrayList<String[]> eTestList1 = transaction.setListInRange("January", "2018", "April", "2020", "expense");
        assertEquals("number of expense within the range", 2, eTestList1.size());
        ArrayList<String[]> iTestList1 = transaction.setListInRange("January", "2018", "April", "2020", "income");
        assertEquals("number of income within the range", 1, iTestList1.size());
        ArrayList<String[]> aTestList1 = transaction.setListInRange("January", "2018", "April", "2020", "all");
        assertEquals("number of all transaction within the range", 3, aTestList1.size());


        // testcase 2: there is no transaction within the range
        ArrayList<String[]> eTestList2 = transaction.setListInRange("January", "2018", "January", "2020", "expense");
        assertEquals("number of expense within the range", 0, eTestList2.size());
        ArrayList<String[]> iTestList2 = transaction.setListInRange("January", "2018", "January", "2020", "income");
        assertEquals("number of income within the range", 0, iTestList2.size());


        // testcase 3: same year, different months
        ArrayList<String[]> eTestList3 = transaction.setListInRange("January", "2020", "April", "2020", "expense");
        assertEquals("number of expense within the range", 2, eTestList3.size());
        ArrayList<String[]> iTestList3 = transaction.setListInRange("January", "2020", "April", "2020", "income");
        assertEquals("number of income within the range", 1, iTestList3.size());

        // testcase 4: same year, same month
        ArrayList<String[]> eTestList4 = transaction.setListInRange("April", "2020", "April", "2020", "expense");
        assertEquals("number of expense within the range", 2, eTestList4.size());
        ArrayList<String[]> iTestList4 = transaction.setListInRange("April", "2020", "April", "2020", "income");
        assertEquals("number of income within the range", 1, iTestList4.size());
    }

    @Test
    public void getExpenseByMonth() {

        Transaction transaction = new Transaction();
        String wholeTrans = "1 Expense 50 4/22/20 April 2020 Food;2 Expense 50 4/22/20 April 2020 book;1 Income 100 4/22/20 April 2020 Food;";
        transaction.setTransaction(wholeTrans);
        sMonth = "April";
        sYear = "2020";
        eMonth = "April";
        eYear = "2020";
        ArrayList<String[]> testList1 = transaction.setListInRange( sMonth, sYear, eMonth, eYear, "expense");

        transaction.getExpenseByMonth(testList1, sMonth, sYear, eMonth, eYear);

        // transaction with same key (month year pair)
        assertEquals("size of expenseByMonth", 1, transaction.expenseByMonth.size());

        // transaction with same key has summed value of expense
        assertEquals("sum of two transaction in same month-year", 100, transaction.expenseByMonth.get("April2020"), 0.01);

        sMonth = "November";
        sYear = "2018";
        eMonth = "April";
        eYear = "2020";
        ArrayList<String[]> testList2 = transaction.setListInRange(sMonth, sYear, eMonth, eYear, "expense");

        transaction.getExpenseByMonth(testList2, sMonth, sYear, eMonth, eYear);

        // transaction with same key (month year pair)
        assertEquals("size of expenseByMonth", 18, transaction.expenseByMonth.size());

        // transaction with same key has summed value of expense
        assertEquals("sum of two transaction in same month-year", 100, transaction.expenseByMonth.get("April2020"), 0.01);
    }

    @Test
    public void getCategList() {

        Transaction transaction = new Transaction();
        String wholeTrans = "1 Expense 50 4/22/20 April 2020 Food;2 Expense 50 4/22/20 April 2020 book;1 Income 100 4/22/20 April 2020 Food;";
        transaction.setTransaction(wholeTrans);
        sMonth = "April";
        sYear = "2020";
        eMonth = "April";
        eYear = "2020";
        ArrayList<String[]> testList = transaction.setListInRange( sMonth, sYear, eMonth, eYear, "expense");

        transaction.getCategList(testList);
        assertEquals("size of transaction by category", 2, transaction.categList.size());

        // transaction with same key has summed value of expense
        assertEquals("value in a specific category", 50.0, transaction.categList.get("Food"), 0.01);


    }

    @Test
    public void getIncomeByMonth() {

        Transaction transaction = new Transaction();
        String wholeTrans = "1 Expense 50 4/22/20 April 2020 Food;2 Expense 50 4/22/20 April 2020 book;3 Income 100 4/22/20 April 2020 Food;4 Income 123.5 4/22/20 April 2020 Food";
        transaction.setTransaction(wholeTrans);
        ArrayList<String[]> iTestList1 = transaction.setListInRange("January", "2018", "April", "2020", "income");

        transaction.getIncomeByMonth(iTestList1);
        // transaction with same key (month year pair)
        assertEquals("size of incomeByMonth", 1, transaction.incomeByMonth.size());

        // transaction with same key has summed value of expense
        assertEquals("sum of income in same month-year", 223.5, transaction.incomeByMonth.get("April2020"), 0.01);

    }
}