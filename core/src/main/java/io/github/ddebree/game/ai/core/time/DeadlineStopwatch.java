package io.github.ddebree.game.ai.core.time;

import com.google.common.base.Stopwatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

public class DeadlineStopwatch {

    private static final Logger LOG = LogManager.getLogger(DeadlineStopwatch.class);

    private final Stopwatch stopwatch = Stopwatch.createStarted();
    private TimeUnit deadlineTimeUnit = TimeUnit.DAYS;
    private long deadlineTime = 365;
    private long wakeTimeBeforeDeadline = 1; //ms

    public void showTime(final String checkpointName) {
        LOG.info("Timer checkpoint [{}] at {}.", checkpointName, stopwatch);
    }

    public void setDeadline(long deadlineTime, TimeUnit timeUnit) {
        synchronized (this) {
            this.deadlineTime = deadlineTime;
            this.deadlineTimeUnit = timeUnit;
        }
    }

    public void setWakeTimeBeforeDeadline(long wakeTimeBeforeDeadline) {
        this.wakeTimeBeforeDeadline = wakeTimeBeforeDeadline;
    }

    public void checkDeadline() throws PastDeadlineException {
        final TimeUnit timeUnit;
        final long time;
        final long elaspsed;
        synchronized (this) {
            timeUnit = deadlineTimeUnit;
            time = deadlineTime;
        }
        elaspsed = stopwatch.elapsed(timeUnit);
        if (elaspsed >= time) {
            throw new PastDeadlineException();
        }
    }

    public void sleepUntilElapsedTime(long time, TimeUnit timeUnit) {
        long timeToSleep = time - stopwatch.elapsed(timeUnit);
        if (timeToSleep > 1) {
            long msToSleep = timeUnit.toMillis(timeToSleep);
            LOG.info("Sleeping {}{}", timeToSleep, timeUnit);
            try {
                Thread.sleep(msToSleep - wakeTimeBeforeDeadline);
            } catch (InterruptedException ex) {
                LOG.warn("Wait for time was interupted!", ex);
            }
        } else {
            LOG.debug("Not sleeping since sleeptime is zero/negative. Target time: {}, sleep time={}, unit={}",
                    time, timeToSleep, timeUnit);
        }
    }

    void resetStopwatch() {
        stopwatch.reset().start();
    }

}
