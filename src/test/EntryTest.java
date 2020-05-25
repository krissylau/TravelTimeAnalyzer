package test;

import main.exceptions.InvalidTimeException;
import main.model.Entry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EntryTest {
    private Entry entry;
    private Entry entry2;

    @BeforeEach
    public void runBefore() {
        try {
            entry = new Entry("rain", "12:00", "14:00");
        } catch (InvalidTimeException e) {
            e.printStackTrace();
        }
        entry2 = new Entry("sun", 120);
    }

    @Test
    public void testConstructor() {
        String weather = "rain";
        String weather2 = "sun";
        Integer duration = 120;

        assertEquals(weather, entry.getWeather());
        assertEquals(weather2, entry2.getWeather());
        assertEquals(duration, entry.getDuration());
        assertEquals(duration, entry2.getDuration());
    }

    @Test
    public void testCalculateDuration() {
        Integer duration = 123;
        Integer testDuration = null;
        try {
            testDuration = entry.calculateDuration("12:00", "14:03");
            assertEquals(duration, testDuration);
        } catch (InvalidTimeException e) {
            fail("No exception should be thrown");
        }
        try {
            testDuration = (entry.calculateDuration("14:03", "12:00"));
            fail("InvalidTimeException should be thrown.");
        } catch (InvalidTimeException e) {
            e.printStackTrace();
        }
    }
}
