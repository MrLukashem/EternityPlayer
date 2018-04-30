package com.mrlukashem.mediacontentprovider

import android.support.test.runner.AndroidJUnit4
import com.mrlukashem.mediacontentprovider.multithreading.Dispatcher
import com.mrlukashem.mediacontentprovider.multithreading.HandlerBasedDispatcher
import com.mrlukashem.mediacontentprovider.multithreading.TasksDispatcher
import com.mrlukashem.mediacontentprovider.providers.ContentViews
import junit.framework.Assert
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by MrLukashem on 21.03.2018.
 */
@RunWith(AndroidJUnit4::class)
class TasksDispatcherTests {
    private var tasksDispatcher: Dispatcher<ContentViews> = TasksDispatcher()

    @Before
    fun onBegin() {
        tasksDispatcher = TasksDispatcher()
    }

    @After
    fun onAfter() {
        tasksDispatcher.quit()
    }

    @Test
    fun beginTest() {
        Assert.assertFalse(tasksDispatcher.isAlive())

        tasksDispatcher.begin()
        Assert.assertTrue(tasksDispatcher.isAlive())
    }

    @Test
    fun tasksDispatcherQuitTests() {
        Assert.assertFalse(tasksDispatcher.isAlive())

        tasksDispatcher.begin()
        tasksDispatcher.quit()
        Thread.sleep(500)
        Assert.assertFalse(tasksDispatcher.isAlive())

        tasksDispatcher.quit()
        Assert.assertFalse(tasksDispatcher.isAlive())
    }

    @Test(expected = HandlerBasedDispatcher.DispatcherNotRunningException::class)
    fun dispatchWhenNotBegunTest() {
        tasksDispatcher.dispatch({ emptyList() }, { })
    }

    @Test
    fun dispatchTest() {
        var isTaskCalled = false
        var isCallbackCalled = false
        tasksDispatcher.begin()
        tasksDispatcher.dispatch({
            isTaskCalled = true
            emptyList()
        }, {
            isCallbackCalled = true
        })

        Thread.sleep(1000)

        Assert.assertTrue(isTaskCalled)
        Assert.assertTrue(isCallbackCalled)
    }
}