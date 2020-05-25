package main.frames;

import main.model.Data;

import javax.swing.*;
import java.awt.*;

public class ResultFrame extends JFrame {

    public ResultFrame(String weather, String time, Data data) {
        String estimate = Integer.toString(data.getEstimate(weather));
        JLabel departure = new JLabel("The time of departure is " + time);
        departure.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
        add(departure);

        JLabel duration = new JLabel("The estimated duration of this trip is " + estimate + " minutes.");
        duration.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
        add(duration);

        String eta = data.getArrivalTime(time, estimate);
        JLabel etaLabel = new JLabel("The estimated time of arrival is: " + eta);
        etaLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
        add(etaLabel);

        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        setVisible(true);
        setSize(500, 180);
    }
}
