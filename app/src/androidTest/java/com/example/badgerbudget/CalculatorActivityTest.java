package com.example.badgerbudget;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import android.widget.TextView;

import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;


public class CalculatorActivityTest extends ActivityInstrumentationTestCase2<Calculator> {

    //@Rule
    //public ActivityTestRule<Calculator> calcActivityRule = new ActivityTestRule<>(Calculator.class);
    // default constructor
    public CalculatorActivityTest(){
        super(Calculator.class);
    }

    @Test
    public void testString(){
        Activity calcActivity = getActivity();
        ////TextView text1Tip = (TextView) calcActivity.findViewById(R.id.tipText);
        //assertEquals("Tip:", text1Tip.getText().toString());

        ////onView(withText("Tip:")).check(matches(isDisplayed()));


        //EditText enterCost = (EditText) calcActivity.findViewById(R.id.enterCost1);
        //enterCost.setText("100.0");
        onView(withId(R.id.enterCost1)).perform(clearText());

        // successfully set text (user input cost)
        onView(withId(R.id.enterCost1)).perform(typeText("100.0"), closeSoftKeyboard());
        onView(withText("100.0")).check(matches(isDisplayed()));

        //onView(withId(R.id.tipDropDown)).perform(click());

        onView(withId(R.id.btn_tip)).perform(click());
        //onView(withId(R.id.tipDisp)).check(matches(withText("12.00")));
        //onView(withText("12.00")).check(matches(isDisplayed()));

    }


    @Test
    public void tipTest() throws Exception {

        assertEquals(1,1);

        Activity calcActivity = getActivity();

        //EditText enterCost = (EditText) calcActivity.findViewById(R.id.enterCost1);
        //enterCost.setText("100.0");
        onView(withId(R.id.enterCost1)).perform(clearText());
        onView(withId(R.id.enterCost1)).perform(typeText("100.0"), closeSoftKeyboard());

        //onView(withId(R.id.tipDropDown)).perform(click());

        onView(withId(R.id.btn_tip)).perform(click());
        onView(withId(R.id.tipDisp)).check(matches(withText("12.00")));

    }

/*    @Before
    public static void setupClass() {
        throw new RuntimeException("Sorry dude, you won't find any test!");
    }*/

    @Test
    public void onCreate() {
    }

    @Test
    public void setStateTax() {
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