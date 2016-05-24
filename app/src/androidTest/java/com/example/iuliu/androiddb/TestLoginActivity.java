package com.example.iuliu.androiddb;

import android.support.test.espresso.action.CloseKeyboardAction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class TestLoginActivity {
    private String TestAccount;
    private String TestPassword;
    private String TestPhone;
    private String TestEmail;

    @Rule
    public ActivityTestRule<Login> myActivityRule = new ActivityTestRule<>(Login.class);

    @Before
    public void initString(){
        // create string to enter
        TestAccount = "Jocke";
        TestPassword = "1234";
    }
    @Test
    public void TestClick(){
        //type text and press button
        onView(withId(R.id.editTextAcc)).perform(typeText(TestAccount));
        onView(withId(R.id.editTextPass)).perform(typeText(TestPassword));

        onView(withId(R.id.buttonLogin)).perform(click());
    }
    @Rule
    public ActivityTestRule<CreateAcc> myActivityRule2 = new ActivityTestRule<>(CreateAcc.class);

    @Before
    public void initString2() {
        // create string to enter
        TestAccount = "Jocke1";
        TestPassword = "1234";
        TestPhone = "0763264458";
        TestEmail = "hej_78@hotmail.com";
    }
    @Test
    public void TestCreateAcc(){
        onView(withId(R.id.editTextAccName)).perform(typeText(TestAccount));
        onView(withId(R.id.editTextPass)).perform(typeText(TestPassword));
        //onView(withId(R.id.editTextPhoneNr)).perform(typeText(TestPhone));
        onView(withId(R.id.editTextEmail)).perform(typeText(TestEmail));

        onView(withId(R.id.buttonCreate)).perform(click());
    }
}
