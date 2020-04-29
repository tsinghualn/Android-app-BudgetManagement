package com.example.badgerbudget;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class TransactionTest {

    String username = "testing0421";

    // when testing it, comment out setTransaction on the constructor!! 
    //Transaction transaction = new Transaction(username);
    @Test
    public void setTransaction() {
        Transaction transaction = new Transaction(username);
        String wholeTrans = "1 Expense 50 4/22/20 April 2020 Food;2 Expense 50 4/22/20 April 2020 book;1 Income 100 4/22/20 April 2020 Food;";
        transaction.setTransaction(wholeTrans);

        assertEquals("There should be two expense type transaction lists",2, transaction.typeExpense.size());
        assertEquals("The amount of expense", "50", transaction.typeExpense.get(0)[2]);
        assertEquals("Month", "April", transaction.typeExpense.get(0)[4]);
        assertEquals("Year", "2020", transaction.typeExpense.get(1)[5]);
        assertEquals("Category of expense 2", "book", transaction.typeExpense.get(1)[6]);
        assertEquals("There should be one income transaction list", 1, transaction.typeIncome.size());

        String emptyTrans = "";
        transaction.setTransaction(emptyTrans);
        assertNull(transaction.typeExpense);
        assertNull(transaction.typeIncome);

    }

    @Test
    public void setTransactionInRange() {

        Transaction transaction = new Transaction(username);
        String wholeTrans = "1 Expense 50 4/22/20 April 2020 Food;2 Expense 50 4/22/20 April 2020 book;1 Income 100 4/22/20 April 2020 Food;";
        transaction.setTransaction(wholeTrans);

        // testcase 1: different months, different years
        ArrayList<String[]> testList1 = transaction.setTransactionInRange("January", "2018", "April", "2020");
        assertEquals("number of expense within the range", 2, testList1.size());


        // testcase 2: there is no transaction within the range
        ArrayList<String[]> testList2 = transaction.setTransactionInRange("January", "2018", "January", "2020");
        assertEquals("number of expense within the range", 0, testList2.size());

        // testcase 3: same year, different months
        ArrayList<String[]> testList3 = transaction.setTransactionInRange("January", "2020", "April", "2020");
        assertEquals("number of expense within the range", 2, testList3.size());

        // testcase 4: same year, same month
        ArrayList<String[]> testList4 = transaction.setTransactionInRange("April", "2020", "April", "2020");
        assertEquals("number of expense within the range", 2, testList4.size());

    }

    @Test
    public void getExpenseByMonth() {
        Transaction transaction = new Transaction(username);
        String wholeTrans = "1 Expense 50 4/22/20 April 2020 Food;2 Expense 50 4/22/20 April 2020 book;1 Income 100 4/22/20 April 2020 Food;";
        transaction.setTransaction(wholeTrans);
        ArrayList<String[]> testList = transaction.setTransactionInRange("April", "2020", "April", "2020");

        transaction.getExpenseByMonth(testList);

        // transaction with same key (month year pair)
        assertEquals("size of expenseByMonth", 1, transaction.expenseByMonth.size());

        // transaction with same key has summed value of expense
        assertEquals("sum of two transaction in same month-year", 100, transaction.expenseByMonth.get("April2020"), 0.01);


    }

    @Test
    public void getCategList() {

        Transaction transaction = new Transaction(username);
        String wholeTrans = "1 Expense 50 4/22/20 April 2020 Food;2 Expense 50 4/22/20 April 2020 book;1 Income 100 4/22/20 April 2020 Food;";
        transaction.setTransaction(wholeTrans);
        ArrayList<String[]> testList = transaction.setTransactionInRange("April", "2020", "April", "2020");

        transaction.getCategList(testList);

        assertEquals("size of categList", 2, transaction.categList.size());
        assertEquals("value in a category", 50, transaction.categList.get("Food"), 0.01);
        assertEquals("value in a category", 50, transaction.categList.get("book"), 0.01);

    }
}