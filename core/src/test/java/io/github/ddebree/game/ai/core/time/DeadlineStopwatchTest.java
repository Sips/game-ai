package io.github.ddebree.game.ai.core.time;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 *
 */
public class DeadlineStopwatchTest {

    @Test
    public void testSleepUntilTimeFuture() {
        long sleepTill = System.currentTimeMillis() + 500L;

        DeadlineStopwatch deadlineStopwatch = new DeadlineStopwatch();
        deadlineStopwatch.sleepUntilElapsedTime(500L, TimeUnit.MILLISECONDS);

        assertTrue(System.currentTimeMillis() >= sleepTill);
    }
    
    @Test
    public void testSleepUntilTimePast() {
        long time0 = System.currentTimeMillis();
        DeadlineStopwatch deadlineStopwatch = new DeadlineStopwatch();
        deadlineStopwatch.sleepUntilElapsedTime(0, TimeUnit.MILLISECONDS);
        if (System.currentTimeMillis() - time0 > 5) {
            fail("Slept for longer than 5ms");
        } else {
            assertTrue(true);
        }
    }

    /**
     * Test of checkDeadline method, of class Time.
     */
    @Test
    public void testCheckDeadlineForPast() {
        try {
            DeadlineStopwatch deadlineStopwatch = new DeadlineStopwatch();
            deadlineStopwatch.setDeadline(0, TimeUnit.NANOSECONDS);
            deadlineStopwatch.checkDeadline();
            fail();
        } catch (PastDeadlineException ex) {
            assertTrue(true);
        }
    }

    /**
     * Test of checkDeadline method, of class Time.
     */
    @Test
    public void testCheckDeadlineForFuture() {
        try {
            DeadlineStopwatch deadlineStopwatch = new DeadlineStopwatch();
            deadlineStopwatch.setDeadline(100, TimeUnit.MILLISECONDS);
            deadlineStopwatch.checkDeadline();
            assertTrue(true);
        } catch (PastDeadlineException ex) {
            fail();
        }
    }
}