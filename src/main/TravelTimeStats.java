package main;

import main.frames.OpenFrame;
import main.model.Data;

import java.io.IOException;

public class TravelTimeStats {
    private Data data = new Data();

    private void start() throws IOException {
        data.load();
        OpenFrame openFrame = new OpenFrame(data);
        data.save();
    }

    // statistics tracker for travel time to UBC from home based on weather
    // tracker will get more accurate as more entries are entered
    // tracker will tell you your estimated arrival time
    // as more data is added, estimated times will be more accurate
    public static void main(String[] args) throws IOException {
        TravelTimeStats tts = new TravelTimeStats();
        tts.start();
    }
}
