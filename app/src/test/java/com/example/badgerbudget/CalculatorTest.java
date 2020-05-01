package com.example.badgerbudget;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.EditText;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.example.badgerbudget.Calculator;

public class CalculatorTest {

    Calculator calculator;
    EditText text_amount_tip;

    @Before
    public void setUp() throws Exception {


    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void onCreate() {

    }

    @Test
    public void setStateTax() {

        Calculator calculator = new Calculator();


        calculator.setStateTax();
/*

        calculator = mock(Calculator.class);
        calculator.setStateTax();
        verify(calculator).setStateTax();
*/


    }

    @Test
    public void onClick_tip() {

    }


    @Test
    public void onClick_tax() {


    }

    @Test
    public void getTipPerc() {

    }

    @Test
    public void getTaxPerc() {
    }

    @Test
    public void findTaxPerc() {
    }
}