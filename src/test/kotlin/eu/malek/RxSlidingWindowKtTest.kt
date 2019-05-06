package eu.malek

import io.reactivex.Observable
import org.junit.Test
import java.util.concurrent.TimeUnit

class RxSlidingWindowKtTest {

    @Test
    fun slidingWindow() {
        val testSequence = "asXZYe".asIterable()
        val timespan = 5000L


        Observable.fromIterable(testSequence)
            .compose(slidingWindow(timespan, TimeUnit.MILLISECONDS, 3))
            .test()
            .assertValues(
                arrayListOf('a', 's', 'X'),
                arrayListOf('s', 'X', 'Z'),
                arrayListOf('X', 'Z', 'Y'),
                arrayListOf('Z', 'Y', 'e')
            )
    }

    @Test
    fun slidingWindowWithCountRange() {
        val testSequence = "asXZYe".asIterable()
        val timespan = 5000L


        Observable.fromIterable(testSequence)
            .compose(slidingWindow(timespan, TimeUnit.MILLISECONDS, 3..4))
            .test()
            .assertValues(
                arrayListOf('a', 's', 'X'),
                arrayListOf('a', 's', 'X', 'Z'),
                arrayListOf('s', 'X', 'Z', 'Y'),
                arrayListOf('X', 'Z', 'Y', 'e')
            )
    }
}

