package com.example.badgerbudget;

import com.anychart.AnyChart;
import com.anychart.charts.Cartesian;

import org.junit.Test;

import static java.lang.Boolean.FALSE;
import static org.junit.Assert.*;

public class reportTest extends report {

    @Test
    public void addExpense() {

        report report = new report();

        // instead of array, use hashmap!

        String[] months = {"January", "Feburary"};
        double[] expense = {10, 11};
        report.addExpense(months, expense);
        assertFalse(report.barDataEntries.isEmpty());
        //assertEquals(report.barDataEntries.get(1).getValue("January"), 1000);

    }

    @Test
    public void setyValues() {
        // more test after implementing database

        report report = new report();
        report.setyValues();

        assertTrue("length of yValues and categories list should be same", report.yValues.size() == report.categories.length);


    }

/*    @Test
    public void createDropDownMenu() {
    }*/

    @Test
    public void isValidRange() {
        report report = new report();

        // different year
        // 1. start year > end year = invalid
        boolean test = report.isValidRange("January", "2019", "March", "2018");
        assertFalse("should return false if start year is larger than end year", test);

        // 2. start year <= end year = valid
        test = report.isValidRange("January", "2018", "March", "2018");
        assertTrue("should return true if start year is equal to end year & start month is earlier than end month", test);
        test = report.isValidRange("January", "2018", "March", "2019");
        assertTrue("should return true if start year is smaller than end year", test);

        // same year different months
        test = report.isValidRange("March", "2018", "January", "2018");
        assertFalse("should return false if start year and end year are same & start month is later than end month", test);
        // same year same months
        test = report.isValidRange("March", "2018", "March", "2018");
        assertTrue("should return true if start year and end year are same & start month and end month are same", test);

    }


    @Test
    public void setData() {
        // setData method only called if month/year range is valid

    }

    @Test
    public void setCategories() {
    }

    @Test
    public void setExpense() {
    }

    @Test
    public void getTotalValue() {
        // write again once data implemented

        report report = new report();
        double[] amount1 = {};
        report.getTotalValue(amount1);
        assertEquals(0.0, report.totalAmount, 0.01);

        double[] amount2 = {12.3, 45.6, 78.9};
        report.getTotalValue(amount2);
        double sum = 12.3+45.6+78.9;

        assertEquals(sum, report.totalAmount, 0.01);

    }

}