package com.example.badgerbudget;

import android.test.ActivityInstrumentationTestCase2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.LinkedHashMap;

import static org.junit.Assert.*;

public class ReportTest {

    @Test
    public void isValidRange() {
        Report report = new Report();

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
    public void getTotalValue() {
        Report report = new Report();
        LinkedHashMap<String, Double> map1 = new LinkedHashMap<>();
        map1.put("January2020", 50.42);
        map1.put("February2020", 325.62);
        map1.put("March2020", 235.12);

        double total1 = report.getTotalValue(map1);
        assertEquals("correctly calculate total value", 50.42+325.62+235.12, total1,0.01);

        LinkedHashMap<String, Double> map2 = new LinkedHashMap<>();

        double total2 = report.getTotalValue(map2);
        assertEquals("correctly calculate total value", 0.0, total2 , 0.01);

    }

}