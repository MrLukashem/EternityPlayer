package com.mrlukashem.mediacontentprovider

import android.support.test.runner.AndroidJUnit4
import com.mrlukashem.mediacontentprovider.multithreading.Dispatcher
import com.mrlukashem.mediacontentprovider.multithreading.TasksDispatcher
import com.mrlukashem.mediacontentprovider.providers.ContentViews
import junit.framework.Assert
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by MrLukashem on 21.03.2018.
 */
@RunWith(AndroidJUnit4::class)
class DispatcherTests {
    @Test fun dispatcherTest() {
        var callbackCalled = false
        val dispatcher = TasksDispatcher<ContentViews>()
        val task: () -> ContentViews = { emptyList() }
        val cb: (views: ContentViews) -> Unit = {
            _ ->
            callbackCalled = true }

        dispatcher.begin()
        dispatcher.dispatch(task, cb)
        dispatcher.begin()
        dispatcher.begin()
        dispatcher.begin()

        Thread.sleep(1000)
        Assert.assertTrue(callbackCalled)
    }
}