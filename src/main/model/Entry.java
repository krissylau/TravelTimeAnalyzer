package main.model;

import main.exceptions.InvalidTimeException;

public class Entry {
    private String weather;
    private Integer duration;

    // construct entry with weather, time of leaving, time of arriving and duration
    // Requires: times to be in valid 24hr format (ie. 16:00, 17:48)
    public Entry(String weather, String timeLeave, String timeArrive) throws InvalidTimeException {
        this.weather = weather;
        this.duration = calculateDuration(timeLeave, timeArrive);
    }

    public Entry(String weather, Integer duration) {
        this.weather = weather;
        this.duration = duration;
    }

    // EFFECTS: calculate and return duration in minutes from start and end time
    public Integer calculateDuration(String start, String end) throws InvalidTimeException {
        Integer startHour = Integer.parseInt(start.substring(0,2));
        Integer startMinute = Integer.parseInt(start.substring(3));
        Integer startTime = startHour*60+startMinute;
        Integer endHour = Integer.parseInt(end.substring(0,2));
        Integer endMinute = Integer.parseInt(end.substring(3));
        Integer endTime = endHour*60+endMinute;
        Integer duration = endTime-startTime;
        if (duration < 0) {
            throw new InvalidTimeException();
        }
        return duration;
    }

    // EFFECTS: returns weather
    public String getWeather() {
        return this.weather;
    }

    // EFFECTS: returns duration() {
    public Integer getDuration() {
        return this.duration;
    }
}
