package eu.malek

import io.reactivex.Observable
import java.util.concurrent.TimeUnit


/**
 * @return true on every element where sequence is acceptingState within timespan else false
 */
fun <T> finiteStateAutomaton(
    acceptingState: List<T>,
    timespan: Long,
    timespanUnit: TimeUnit
): (Observable<T>) -> Observable<Boolean> {
    return {
        it
            .compose(slidingWindow(timespan, timespanUnit, 1..acceptingState.size))
            .map { it == acceptingState }
    }
}