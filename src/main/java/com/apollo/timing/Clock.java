package com.apollo.timing;

import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;

import java.util.concurrent.TimeUnit;

// a clock that counts down from a given timer.
public class Clock {

    private long nanoTime;
    private Stopwatch watch;

    public Clock(TimeUnit units, long timeout) {
        Preconditions.checkNotNull(units, "units must not be null");
        nanoTime = units.toNanos(timeout);
        watch = Stopwatch.createStarted();
    }

    // resets and starts the clock.
    public void restart() {
        watch.reset();
        watch.start();
    }

    // checks if the clock is up (has reached 0)
    public boolean isUp() {
        return watch.elapsed(TimeUnit.NANOSECONDS) >= nanoTime;
    }
}
