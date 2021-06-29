package com.geekbrains.tests

import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.geekbrains.tests.view.details.DetailsViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class DetailsViewModelTest {
    private lateinit var detailsViewModel: DetailsViewModel

    @Before
    fun setUp() {
        detailsViewModel = DetailsViewModel()
    }

    @Test
    fun incrementTest(){
        detailsViewModel.onIncrement()

        val privateCount = detailsViewModel.javaClass.getDeclaredField("count").apply {
            this.isAccessible = true
        }
        val count = privateCount.get(detailsViewModel)

        Assert.assertEquals(count, 1)
    }

    @Test
    fun decrementTest(){
        detailsViewModel.onDecrement()

        val privateCount = detailsViewModel.javaClass.getDeclaredField("count").apply {
            this.isAccessible = true
        }
        val count = privateCount.get(detailsViewModel)

        Assert.assertEquals(count, -1)
    }

}