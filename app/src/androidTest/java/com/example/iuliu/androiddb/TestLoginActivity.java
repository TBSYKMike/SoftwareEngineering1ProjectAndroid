package com.example.iuliu.androiddb;

import android.support.test.espresso.action.CloseKeyboardAction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
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
@SmallTest
public class TestLoginActivity {
    private String TestAccount;
    private String TestPassword;

    @Rule
    public ActivityTestRule<LoginActivity> myActivityRule = new ActivityTestRule<>(LoginActivity.class);

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

}
