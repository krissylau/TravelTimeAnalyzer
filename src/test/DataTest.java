package test;

import main.model.Data;
import main.model.Entry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DataTest {
    private Data data;
    private Entry rainEntry;
    private Entry sunEntry;
    private Entry snowEntry;

    @BeforeEach
    public void runBefore() {
        data = new Data();
        rainEntry = new Entry("rain", 120);
        sunEntry = new Entry("sun", 120);
        snowEntry = new Entry("snow", 120);
        data.addToData(rainEntry);
        data.addToData(sunEntry);
        data.addToData(snowEntry);
        data.estimateDurations();
    }

    @Test
    public void testAddToData() {
        ArrayList<Entry> testRain = new ArrayList<>();
        testRain.add(rainEntry);
        assertEquals(testRain, data.getData("rain"));

        ArrayList<Entry> testSun = new ArrayList<>();
        testSun.add(sunEntry);
        assertEquals(testSun, data.getData("sun"));

        ArrayList<Entry> testSnow = new ArrayList<>();
        testSnow.add(snowEntry);
        assertEquals(testSnow, data.getData("snow"));
        assertTrue(testSun.size() == 1);
        assertTrue(testRain.size() == 1);

        Entry newEntry = new Entry("sun", 130);
        data.addToData(newEntry);
        testSun.add(newEntry);
        assertEquals(testSun, data.getData("sun"));
        assertTrue(testSnow.size() == 1);
        assertTrue(testRain.size() == 1);
    }

    @Test
    public void testEstimateDurations() {
        Integer rainDuration = 120;
        Integer sunDuration = 120;
        Integer snowDuration = 120;

        assertEquals(rainDuration, data.getEstimate("rain"));
        assertEquals(sunDuration, data.getEstimate("sun"));
        assertEquals(snowDuration, data.getEstimate("snow"));

        Entry sun = new Entry("sun", 140);
        data.addToData(sun);
        sunDuration = (sunDuration+140)/2;
        data.estimateDurations();
        assertEquals(rainDuration, data.getEstimate("rain"));
        assertEquals(sunDuration, data.getEstimate("sun"));
        assertEquals(snowDuration, data.getEstimate("snow"));
    }

    @Test
    public void testGetArrivalTime() {
        String result = data.getArrivalTime("00:00", "60");
        assertEquals("01:00", result);

        result = data.getArrivalTime("00:45", "60");
        assertEquals("01:45", result);

        result = data.getArrivalTime("12:00", "60");
        assertEquals("13:00", result);

        result = data.getArrivalTime("12:12", "60");
        assertEquals("13:12", result);

        result = data.getArrivalTime("12:59", "10");
        assertEquals("13:09", result);

        result = data.getArrivalTime("23:00", "60");
        assertEquals("00:00 the next day", result);
    }

    @Test
    public void testGetData() {
        ArrayList<Entry> rain = new ArrayList<>();
        rain.add(rainEntry);
        assertEquals(rain, data.getData("rAin"));

        ArrayList<Entry> sun = new ArrayList<>();
        sun.add(sunEntry);
        assertEquals(sun, data.getData("SUN"));

        ArrayList<Entry> snow = new ArrayList<>();
        snow.add(snowEntry);
        assertEquals(snow, data.getData("snow"));
    }

    @Test
    public void testLoad() throws IOException {
        data.save();
        Data test = new Data();
        test.load();
        ArrayList<Entry> testArray = test.getData("rain");
        Entry e = testArray.get(0);
        assertEquals(rainEntry.getWeather(), e.getWeather());
        assertEquals(rainEntry.getDuration(), e.getDuration());
        assertEquals(1, testArray.size());

        testArray = test.getData("sun");
        e = testArray.get(0);
        assertEquals(sunEntry.getWeather(), e.getWeather());
        assertEquals(sunEntry.getDuration(), e.getDuration());
        assertEquals(1, testArray.size());

        testArray = test.getData("snow");
        e = testArray.get(0);
        assertEquals(snowEntry.getWeather(), e.getWeather());
        assertEquals(snowEntry.getDuration(), e.getDuration());
        assertEquals(1, testArray.size());
    }

    @Test
    public void testSave() throws IOException {
        data.save();
        List<String> rain = Files.readAllLines(Paths.get("rain.txt"));
        assertEquals("Rain Entries:", rain.get(0));
        assertEquals("rain  120", rain.get(1));

        List<String> sun = Files.readAllLines(Paths.get("sun.txt"));
        assertEquals("Sun Entries:", sun.get(0));
        assertEquals("sun  120", sun.get(1));

        List<String> snow = Files.readAllLines(Paths.get("snow.txt"));
        assertEquals("Snow Entries:", snow.get(0));
        assertEquals("snow  120", snow.get(1));
    }
}
