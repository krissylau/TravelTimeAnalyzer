package main.model;

import main.interfaces.Load;
import main.interfaces.Save;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Data implements Save, Load {
    private ArrayList<Entry> rainEntries = new ArrayList<>();
    private ArrayList<Entry> sunEntries = new ArrayList<>();
    private ArrayList<Entry> snowEntries = new ArrayList<>();

    private Integer rainDuration = 0;
    private Integer sunDuration = 0;
    private Integer snowDuration = 0;

    public void addToData(Entry entry) {
        String weather = entry.getWeather();
        if (weather.equalsIgnoreCase("rain")) {
            rainEntries.add(entry);
        }
        else if (weather.equalsIgnoreCase("sun")) {
            sunEntries.add(entry);
        }
        else {
            snowEntries.add(entry);
        }
    }

    // MODIFIES: this
    // EFFECTS: sets durations for rain, sun, snow
    public void estimateDurations() {
        rainDuration = getAverage(rainEntries);
        sunDuration = getAverage(sunEntries);
        snowDuration = getAverage(snowEntries);
    }

    // EFFECTS: returns the average duration for the requested array
    private int getAverage(ArrayList<Entry> array) {
        int total = 0;
        int n = array.size();
        for(Entry e: array) {
            total += e.getDuration();
        }
        return total/n;
    }

    // EFFECTS: returns estimate of requested duration
    public Integer getEstimate(String weather) {
        if (weather.equalsIgnoreCase("rain")) {
            return rainDuration;
        }
        else if (weather.equalsIgnoreCase("sun")) {
            return sunDuration;
        }
        else {
            return snowDuration;
        }
    }

    // EFFECTS: returns arrival time calculated from departure time and duration of travel
    public String getArrivalTime(String departureTime, String duration) {
        Integer dur = Integer.parseInt(duration);
        Integer hours = dur/60;
        Integer minutes = dur - hours*60;
        Integer startHour = Integer.parseInt(departureTime.substring(0,2));
        Integer startMinute = Integer.parseInt(departureTime.substring(3));
        Integer endMinutes = startMinute+minutes;
        if (endMinutes>59) {
            Integer remain = endMinutes-60;
            endMinutes = remain;
            hours++;
        }
        Integer endHour = startHour+hours;
        boolean signal = false;

        if (endHour > 23) {
            endHour = endHour - 24;
            signal = true;
        }

        String result;
        String min;
        String hour;
        if (endMinutes == 0) {
            min = "00";
        }
        else if (endMinutes < 10) {
            min = "0" + endMinutes;
        }
        else {
            min = Integer.toString(endMinutes);
        }
        if (endHour == 0) {
            hour = "00";
        }
        else if (endHour < 10) {
            hour = "0" + endHour;
        }
        else {
            hour = Integer.toString(endHour);
        }
        result = hour + ":" + min;

        if (signal) {
            result += " the next day";
        }
        return result;
    }

    public ArrayList<Entry> getData(String array) {
        if (array.equalsIgnoreCase("rain")) {
            return rainEntries;
        }
        else if (array.equalsIgnoreCase("sun")) {
            return sunEntries;
        }
        else {
            return snowEntries;
        }
    }


    @Override
    public void load() throws IOException {
        loadHelper("rain.txt", "Rain");
        loadHelper("sun.txt", "Sun");
        loadHelper("snow.txt", "Snow");
    }

    // EFFECTS: helper function for load
    // MODIFIES: this
    private void loadHelper(String fileName, String arrayName) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(fileName));
        lines.remove(0);
        ArrayList<Entry> array = new ArrayList<>();
        for (String line: lines) {
            ArrayList<String> partsOfLine = splitOnSpace(line);
            String weather = partsOfLine.get(0);
            Integer duration = Integer.parseInt(partsOfLine.get(1));
            Entry e = new Entry(weather, duration);
            array.add(e);
        }
        if (arrayName.equalsIgnoreCase("Rain")) {
            for (Entry e: array) {
                rainEntries.add(e);
            }
        }
        else if (arrayName.equalsIgnoreCase("Sun")) {
            for (Entry e: array) {
                sunEntries.add(e);
            }
        }
        else {
            for (Entry e: array) {
                snowEntries.add(e);
            }
        }
    }

    public static ArrayList<String> splitOnSpace(String line) {
        String[] splits = line.split("  ");
        return new ArrayList<>(Arrays.asList(splits));
    }

    @Override
    public void save() throws FileNotFoundException, UnsupportedEncodingException {
        saveHelper("rain.txt", "Rain Entries:");
        saveHelper("sun.txt", "Sun Entries:");
        saveHelper("snow.txt", "Snow Entries:");
    }

    // EFFECTS: helper function for save
    // MODIFIES: txt files for each data array
    private void saveHelper(String fileName, String title) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter(fileName, "UTF-8");
        writer.println(title);
        ArrayList<Entry> array;
        if (title.equalsIgnoreCase("Rain Entries:")) {
            array = rainEntries;
        }
        else if (title.equalsIgnoreCase("Sun Entries:")) {
            array = sunEntries;
        }
        else {
            array = snowEntries;
        }
        for (Entry e: array) {
            String weather = e.getWeather();
            String duration = Integer.toString(e.getDuration());
            writer.println(weather + "  " + duration);
        }
        writer.close();
    }
}
