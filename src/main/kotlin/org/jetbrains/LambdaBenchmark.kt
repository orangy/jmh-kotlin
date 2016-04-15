package org.jetbrains

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.BenchmarkMode
import org.openjdk.jmh.annotations.Mode
import org.openjdk.jmh.annotations.OutputTimeUnit
import java.util.concurrent.ThreadLocalRandom
import java.util.concurrent.TimeUnit

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
open class LambdaBenchmark : SizedBenchmark() {
    private fun <T> runLambda(x: () -> T): T = x()

    @Benchmark fun noncapturingLambda(): Int {
        var x: Int = 0
        for (i in 0..size) {
            x += runLambda { size }
        }
        return x
    }

    @Benchmark fun capturingLambda(): Int {
        val addendum = ThreadLocalRandom.current().nextInt(20)
        var x: Int = 0
        for (i in 0..size) {
            x += runLambda { addendum }
        }
        return x
    }

    @Benchmark fun mutatingLambda(): Int {
        val addendum = ThreadLocalRandom.current().nextInt(20)
        var x: Int = 0
        for (i in 0..size) {
            runLambda { x += addendum }
        }
        return x
    }
}