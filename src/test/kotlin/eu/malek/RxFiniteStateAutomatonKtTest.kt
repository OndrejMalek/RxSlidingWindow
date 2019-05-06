package eu.malek

import io.reactivex.Observable
import org.junit.Test
import java.util.concurrent.TimeUnit

class RxFiniteStateAutomatonKtTest {


    @Test
    fun testFiniteStateAutomaton() {
        val testSequence = "asXZYe".asIterable()
        val acceptingState = "XZY".toList()
        val timespan = 5000L


        Observable.fromIterable(testSequence)
            .compose(finiteStateAutomaton(acceptingState, timespan, TimeUnit.MILLISECONDS))
            .test()
            .assertValues(
                false,
                false,
                false,
                false,
                true,
                false
            )
    }


}

