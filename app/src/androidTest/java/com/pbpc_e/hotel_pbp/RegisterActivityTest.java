package com.pbpc_e.hotel_pbp;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RegisterActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void registerActivityTest() {
        onView(isRoot()).perform(waitFor(3600));

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.button_register), withText("New user? Register now"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                6)));
        materialButton.perform(scrollTo(), click());


        onView(isRoot()).perform(waitFor(2000));

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.button_register), withText("Register"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                7)));
        materialButton2.perform(scrollTo(), click());

        onView(isRoot()).perform(waitFor(2000));

        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.input_email),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.input_layout_email),
                                        0),
                                0)));
        textInputEditText.perform(scrollTo(), replaceText("cornelnova00@gmail.com"), closeSoftKeyboard());

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.input_phone),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.input_layout_phone),
                                        0),
                                0)));
        textInputEditText2.perform(scrollTo(), replaceText("08993371852"), closeSoftKeyboard());

        ViewInteraction textInputEditText3 = onView(
                allOf(withId(R.id.input_password),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.input_layout_password),
                                        0),
                                0)));
        textInputEditText3.perform(scrollTo(), replaceText("asd12345"), closeSoftKeyboard());

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.button_register), withText("Register"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                7)));
        materialButton3.perform(scrollTo(), click());

        onView(isRoot()).perform(waitFor(2000));

        ViewInteraction textInputEditText4 = onView(
                allOf(withId(R.id.input_email), withText("cornelnova00@gmail.com"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.input_layout_email),
                                        0),
                                0)));
        textInputEditText4.perform(scrollTo(), replaceText(""));

        ViewInteraction textInputEditText5 = onView(
                allOf(withId(R.id.input_email),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.input_layout_email),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText5.perform(closeSoftKeyboard());

        ViewInteraction textInputEditText6 = onView(
                allOf(withId(R.id.input_name),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.input_layout_name),
                                        0),
                                0)));
        textInputEditText6.perform(scrollTo(), replaceText("CornelNova"), closeSoftKeyboard());

        ViewInteraction materialButton4 = onView(
                allOf(withId(R.id.button_register), withText("Register"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                7)));
        materialButton4.perform(scrollTo(), click());

        onView(isRoot()).perform(waitFor(2000));

        ViewInteraction textInputEditText7 = onView(
                allOf(withId(R.id.input_email),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.input_layout_email),
                                        0),
                                0)));
        textInputEditText7.perform(scrollTo(), replaceText("cornelnova00"), closeSoftKeyboard());

        ViewInteraction materialButton5 = onView(
                allOf(withId(R.id.button_register), withText("Register"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                7)));
        materialButton5.perform(scrollTo(), click());

        onView(isRoot()).perform(waitFor(2000));

        ViewInteraction textInputEditText8 = onView(
                allOf(withId(R.id.input_email), withText("cornelnova00"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.input_layout_email),
                                        0),
                                0)));
        textInputEditText8.perform(scrollTo(), replaceText("cornelnova00@gmail.com"));

        ViewInteraction textInputEditText9 = onView(
                allOf(withId(R.id.input_email), withText("cornelnova00@gmail.com"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.input_layout_email),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText9.perform(closeSoftKeyboard());

        ViewInteraction textInputEditText10 = onView(
                allOf(withId(R.id.input_phone), withText("08993371852"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.input_layout_phone),
                                        0),
                                0)));
        textInputEditText10.perform(scrollTo(), replaceText(""));

        ViewInteraction textInputEditText11 = onView(
                allOf(withId(R.id.input_phone),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.input_layout_phone),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText11.perform(closeSoftKeyboard());

        ViewInteraction materialButton6 = onView(
                allOf(withId(R.id.button_register), withText("Register"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                7)));
        materialButton6.perform(scrollTo(), click());

        onView(isRoot()).perform(waitFor(2000));

        ViewInteraction textInputEditText12 = onView(
                allOf(withId(R.id.input_phone),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.input_layout_phone),
                                        0),
                                0)));
        textInputEditText12.perform(scrollTo(), replaceText("123445"), closeSoftKeyboard());

        ViewInteraction materialButton7 = onView(
                allOf(withId(R.id.button_register), withText("Register"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                7)));
        materialButton7.perform(scrollTo(), click());

        onView(isRoot()).perform(waitFor(2000));

        ViewInteraction textInputEditText13 = onView(
                allOf(withId(R.id.input_phone), withText("123445"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.input_layout_phone),
                                        0),
                                0)));
        textInputEditText13.perform(scrollTo(), replaceText("08993371852"));

        ViewInteraction textInputEditText14 = onView(
                allOf(withId(R.id.input_phone), withText("08993371852"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.input_layout_phone),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText14.perform(closeSoftKeyboard());

        ViewInteraction textInputEditText15 = onView(
                allOf(withId(R.id.input_password), withText("asd12345"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.input_layout_password),
                                        0),
                                0)));
        textInputEditText15.perform(scrollTo(), replaceText(""));

        ViewInteraction textInputEditText16 = onView(
                allOf(withId(R.id.input_password),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.input_layout_password),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText16.perform(closeSoftKeyboard());

        ViewInteraction materialButton8 = onView(
                allOf(withId(R.id.button_register), withText("Register"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                7)));
        materialButton8.perform(scrollTo(), click());

        onView(isRoot()).perform(waitFor(2000));

        ViewInteraction textInputEditText17 = onView(
                allOf(withId(R.id.input_password),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.input_layout_password),
                                        0),
                                0)));
        textInputEditText17.perform(scrollTo(), replaceText("asd12345"), closeSoftKeyboard());

        ViewInteraction materialButton9 = onView(
                allOf(withId(R.id.button_register), withText("Register"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                7)));
        materialButton9.perform(scrollTo(), click());

        onView(isRoot()).perform(waitFor(2000));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

    public static ViewAction waitFor(long delay) {
        return new ViewAction() {
            @Override public Matcher<View> getConstraints() {
                return ViewMatchers.isRoot();
            }

            @Override public String getDescription() {
                return "wait for " + delay + "milliseconds";
            }

            @Override public void perform(UiController uiController, View view) {
                uiController.loopMainThreadForAtLeast(delay);
            }
        };
    }
}
