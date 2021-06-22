package com.geekbrains.tests.espresso

import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.geekbrains.tests.*
import com.geekbrains.tests.NUMBER_OF_RESULTS_MINUS_1
import com.geekbrains.tests.NUMBER_OF_RESULTS_PLUS_1
import com.geekbrains.tests.NUMBER_OF_RESULTS_ZERO
import com.geekbrains.tests.view.details.DetailsActivity
import com.geekbrains.tests.view.details.DetailsFragment
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailsFragmentEspressoTest {

    private lateinit var scenario: FragmentScenario<DetailsFragment>
    private lateinit var context: Context

    @Before
    fun setup() {
        //Запускаем Fragment в корне Activity
        scenario = launchFragmentInContainer()
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun fragment_AssertNotNull() {
        scenario.onFragment {
            TestCase.assertNotNull(it)
        }
    }

    @Test
    fun fragmentTextView_NotNull() {
        scenario.onFragment {
            val totalCountTextView = it.requireView().findViewById<TextView>(R.id.detailsTotalCountTextView)
            TestCase.assertNotNull(totalCountTextView)
        }
    }

   @Test
    fun fragmentTextView_HasText() {
        scenario.onFragment {
            it.setCount(0)
            val totalCountTextView = it.requireView().findViewById<TextView>(R.id.detailsTotalCountTextView)
            TestCase.assertEquals(NUMBER_OF_RESULTS_ZERO, totalCountTextView.text)
        }
    }

    @Test
    fun fragmentTextView_IsVisible() {
        scenario.onFragment {
            val totalCountTextView = it.requireView().findViewById<TextView>(R.id.detailsTotalCountTextView)
            TestCase.assertEquals(View.VISIBLE, totalCountTextView.visibility)
        }
    }

    @Test
    fun fragmentButtons_AreVisible() {
        scenario.onFragment {
            val decrementButton = it.requireView().findViewById<Button>(R.id.decrementButton)
            TestCase.assertEquals(View.VISIBLE, decrementButton.visibility)

            val incrementButton = it.requireView().findViewById<Button>(R.id.incrementButton)
            TestCase.assertEquals(View.VISIBLE, incrementButton.visibility)
        }
    }

    @Test
    fun fragmentButtonIncrement_IsWorking() {
        scenario.onFragment {
            val incrementButton = it.requireView().findViewById<Button>(R.id.incrementButton)
            val totalCountTextView = it.requireView().findViewById<TextView>(R.id.detailsTotalCountTextView)
            incrementButton.performClick()

            TestCase.assertEquals(NUMBER_OF_RESULTS_PLUS_1, totalCountTextView.text)
        }
    }

    @Test
    fun fragmentButtonDecrement_IsWorking() {
        scenario.onFragment {
            val decrementButton = it.requireView().findViewById<Button>(R.id.decrementButton)
            val totalCountTextView = it.requireView().findViewById<TextView>(R.id.detailsTotalCountTextView)
            decrementButton.performClick()

            TestCase.assertEquals(NUMBER_OF_RESULTS_MINUS_1, totalCountTextView.text)
        }
    }

    @Test
    fun fragmentCreateIntent_NotNull() {
        val intent = DetailsActivity.getIntent(context, 0)
        TestCase.assertNotNull(intent)
    }

    @Test
    fun fragmentCreateIntent_HasExtras() {
        val intent = DetailsActivity.getIntent(context, 0)
        val bundle = intent.extras
        TestCase.assertNotNull(bundle)
    }

    @Test
    fun fragmentCreateIntent_HasCount() {
        val intent = DetailsActivity.getIntent(context, TEST_NUMBER_REPOS)
        val bundle = intent.extras
        TestCase.assertEquals(
            TEST_NUMBER_REPOS,
            bundle?.getInt(DetailsActivity.TOTAL_COUNT_EXTRA, 0)
        )
    }

    @Test
    fun fragment_testBundle() {
        //Можно передавать аргументы во Фрагмент, но это необязательно
        val fragmentArgs = bundleOf("TOTAL_COUNT_EXTRA" to 10)
        //Запускаем Fragment с аргументами
        val scenario = launchFragmentInContainer<DetailsFragment>(fragmentArgs)
        //Возможность менять стейт Фрагмента
        scenario.moveToState(Lifecycle.State.RESUMED)

        val assertion = matches(withText("Number of results: 10"))
        onView(withId(R.id.detailsTotalCountTextView)).check(assertion)
    }

    @Test
    fun fragment_testSetCountMethod() {
        scenario.onFragment { fragment ->
            fragment.setCount(10)
        }
        val assertion = matches(withText("Number of results: 10"))
        onView(withId(R.id.detailsTotalCountTextView)).check(assertion)
    }

    @Test
    fun fragment_testIncrementButton() {
        onView(withId(R.id.incrementButton)).perform(click())
        onView(withId(R.id.detailsTotalCountTextView)).check(matches(withText(NUMBER_OF_RESULTS_PLUS_1)))
    }
}