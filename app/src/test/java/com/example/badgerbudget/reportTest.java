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

        String[] months = {"January", "Feburary"};
        double[] expense = {10, 11};
        report.addExpense(months, expense);
        assertEquals(report.barDataEntries.isEmpty(), FALSE);
        //assertEquals(report.barDataEntries.get(1).getValue("January"), 1000);

    }
/*
    @Test
    public void viewCategPieChart() {

    }

    @Test
    public void createDropDownMenu() {
    }

    @Test
    public void setData() {
    }

    @Test
    public void getCategories() {
    }

    @Test
    public void getExpense() {
    }
    */
    @Test
    public void getTotalValue() {
        report report = new report();
        double[] amount1 = {};
        report.getTotalValue(amount1);
        assertEquals(report.totalAmount, 0.0, 0.01);

        double[] amount2 = {12.3, 45.6, 78.9};
        report.getTotalValue(amount2);
        double sum = 12.3+45.6+78.9;

        assertEquals(report.totalAmount, sum, 0.01);

    }

}