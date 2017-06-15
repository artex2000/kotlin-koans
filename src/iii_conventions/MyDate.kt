package iii_conventions

import syntax.qualifiedThis.labelsForExtensionFunctionLiterals
import java.util.*

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int {
        if (this.year != other.year)
            return this.year - other.year
        else if (this.month != other.month)
            return this.month - other.month
        else
            return this.dayOfMonth - other.dayOfMonth
    }
}

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)
operator fun MyDate.plus (interval: RepeatedTimeInterval): MyDate = this.addTimeIntervals(interval.timeInterval, interval.count)
operator fun MyDate.plus (interval: TimeInterval): MyDate = this.addTimeIntervals(interval, 1)

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

class RepeatedTimeInterval (val timeInterval: TimeInterval, val count: Int)

operator fun TimeInterval.times (i: Int): RepeatedTimeInterval = RepeatedTimeInterval(this, i)

class DateRange(override val start: MyDate, override val endInclusive: MyDate): ClosedRange<MyDate>, Iterable<MyDate> {
    override operator fun iterator(): Iterator<MyDate> {
        return object: Iterator<MyDate> {
            var current: MyDate = start
            override fun next(): MyDate {
                val result = current
                current = current.nextDay()
                return result
            }

            override fun hasNext(): Boolean {
                return current <= endInclusive
            }
        }
    }
}

