package com.geekbrains.tests

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers

const val TEST_NUMBER_REPOS = 42
internal const val NUMBER_OF_RESULTS_ZERO = "Number of results: 0"
internal const val NUMBER_OF_RESULTS_PLUS_1 = "Number of results: 1"
internal const val NUMBER_OF_RESULTS_MINUS_1 = "Number of results: -1"

fun view_isDisplayed(resId: Int){
    Espresso.onView(ViewMatchers.withId(resId))
        .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
}