package eu.malek

import io.reactivex.Observable
import io.reactivex.schedulers.Timed
import java.util.*
import java.util.concurrent.TimeUnit





/**
 * Sliding overlapping window. If countInRange of elements are within timespan
 * then groups elements into List otherwise filtered out
 */
fun <T> slidingWindow(
    timespan: Long,
    timespanUnit: TimeUnit,
    countInRange: IntRange
): (Observable<T>) -> Observable<List<T>> {
    return { from ->
        from.timestamp(timespanUnit)
            .scan(
                ArrayDeque<Timed<T>>(countInRange.last + 1),
                { queue: ArrayDeque<Timed<T>>, t: Timed<T>
                    ->
                    queue.addLast(t)
                    if (queue.size > countInRange.last) queue.removeFirst()
                    queue.removeAll { timed -> queue.last().time() - timed.time() > timespan }
                    queue
                }
            )
            .filter({ it.size in countInRange })
            .map { it.toTypedArray() }
            .map { it.map { timed -> timed.value() } }
    }
}

/**
 * Sliding overlapping window. If exactCount of elements are within timespan
 * then groups elements into List otherwise filtered out
 */
fun <T> slidingWindow(
    timespan: Long,
    timespanUnit: TimeUnit,
    exactCount: Int
): (Observable<T>) -> Observable<List<T>> {
    return { from ->
        from.timestamp(timespanUnit)
            .scan(
                ArrayDeque<Timed<T>>(exactCount + 1),
                { queue: ArrayDeque<Timed<T>>, t: Timed<T>
                    ->
                    queue.addLast(t)
                    if (queue.size > exactCount) queue.removeFirst()
                    queue.removeAll { timed -> queue.last().time() - timed.time() > timespan }
                    queue
                }
            )
            .filter { it.size == exactCount }
            .map { it.toTypedArray() }
            .map { it.map { timed -> timed.value() } }
    }
}