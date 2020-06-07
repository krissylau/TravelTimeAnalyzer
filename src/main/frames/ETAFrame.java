package main.frames;

import main.model.Data;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ETAFrame extends JFrame implements ActionListener {
    private Data data;
    JButton getETA;
    JComboBox minBox;
    JComboBox hourBox;
    String min;
    String hour;
    JButton close;
    JComboBox weatherBox;
    String message;

    public ETAFrame(Data data) {
        this.data = data;
        SpringLayout layout = new SpringLayout();
        Container container = getContentPane();
        Font font = new Font("Segoe UI Emoji", Font.PLAIN, 18);
        JLabel weather = new JLabel("Enter current weather (rain, sun or snow)");
        weather.setFont(font);
        layout.putConstraint(SpringLayout.WEST, weather, 5, SpringLayout.WEST, container);
        layout.putConstraint(SpringLayout.NORTH, weather, 10, SpringLayout.NORTH, container);
        JLabel w = new JLabel("Weather: ");
        w.setFont(font);
        layout.putConstraint(SpringLayout.WEST, w, 5, SpringLayout.WEST, container);
        layout.putConstraint(SpringLayout.NORTH, w, 35, SpringLayout.NORTH, container);
        String[] weatherStrings = {"-------", "Rain", "Sun", "Snow"};
        weatherBox = new JComboBox(weatherStrings);
        weatherBox.setFont(font);
        weatherBox.setSelectedIndex(0);
        layout.putConstraint(SpringLayout.WEST, weatherBox, 100, SpringLayout.WEST, container);
        layout.putConstraint(SpringLayout.NORTH, weatherBox, 30, SpringLayout.NORTH, container);

        add(weather);
        add(w);
        add(weatherBox);
        weatherBox.addActionListener(this);

        JLabel time = new JLabel("Enter departure time in 24 hour format (ex. 14:00, 16:39)");
        time.setFont(font);
        layout.putConstraint(SpringLayout.WEST, time, 5, SpringLayout.WEST, container);
        layout.putConstraint(SpringLayout.NORTH, time, 60, SpringLayout.NORTH, container);
        add(time);

        JLabel t = new JLabel("Time: ", JLabel.TRAILING);
        t.setFont(font);
        layout.putConstraint(SpringLayout.WEST, t, 5, SpringLayout.WEST, container);
        layout.putConstraint(SpringLayout.NORTH, t, 85, SpringLayout.NORTH, container);
        add(t);

        JComboBox.KeySelectionManager hourKey = new JComboBox.KeySelectionManager() {
            @Override
            public int selectionForKey(char aKey, ComboBoxModel aModel) {
                int ret = 0;
                if (aKey == '1') {
                    ret = 11;
                }
                if (aKey == '2') {
                    ret = 21;
                }
                return ret;
            }
        };

        JComboBox.KeySelectionManager minKey = new JComboBox.KeySelectionManager() {
            @Override
            public int selectionForKey(char aKey, ComboBoxModel aModel) {
                int ret = 0;
                int key = Character.getNumericValue(aKey);
                if (key < 6) {
                    ret = key*10;
                }
                return ret+1;
            }
        };

        String[] hour = new String[25];
        hour[0] = "--";
        generateTime(hour, 24);
        hourBox = new JComboBox(hour);
        hourBox.setFont(font);
        hourBox.addActionListener(this);
        hourBox.setSelectedIndex(0);
        layout.putConstraint(SpringLayout.WEST, hourBox, 100, SpringLayout.WEST, container);
        layout.putConstraint(SpringLayout.NORTH, hourBox, 80, SpringLayout.NORTH, container);
        add(hourBox);
        t.setLabelFor(hourBox);
        hourBox.setKeySelectionManager(hourKey);

        JLabel colon = new JLabel(":");
        colon.setFont(font);
        layout.putConstraint(SpringLayout.WEST, colon, 162, SpringLayout.WEST, container);
        layout.putConstraint(SpringLayout.NORTH, colon, 85, SpringLayout.NORTH, container);
        add(colon);

        String[] min = new String[61];
        min[0] = "--";
        generateTime(min, 60);
        minBox = new JComboBox(min);
        minBox.setFont(font);
        minBox.addActionListener(this);
        minBox.setSelectedIndex(0);
        layout.putConstraint(SpringLayout.WEST, minBox, 180, SpringLayout.WEST, container);
        layout.putConstraint(SpringLayout.NORTH, minBox, 80, SpringLayout.NORTH, container);
        add(minBox);
        minBox.setKeySelectionManager(minKey);

        getETA = new JButton("Calculate ETA");
        layout.putConstraint(SpringLayout.WEST, getETA, 50, SpringLayout.WEST, container);
        layout.putConstraint(SpringLayout.NORTH, getETA,  115, SpringLayout.NORTH, container);
        getETA.setFont(font);
        add(getETA);
        getETA.addActionListener(this);

        close = new JButton("Close");
        layout.putConstraint(SpringLayout.WEST, close, 300, SpringLayout.WEST, container);
        layout.putConstraint(SpringLayout.NORTH, close, 115, SpringLayout.NORTH, container);
        close.setFont(font);
        add(close);
        close.addActionListener(this);

        pack();
        setLayout(layout);
        setVisible(true);
        setSize(500,230);
    }

    // EFFECTS: generates hours/minutes
    private void generateTime(String[] startMin, int i2) {
        for (int i = 0; i < i2; i++) {
            String time = Integer.toString(i);
            if (i < 10) {
                time = "0" + time;
            }
            startMin[i+1] = time;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == weatherBox) {
            JComboBox box = (JComboBox) e.getSource();
            String weather = (String) box.getSelectedItem();
            if (weather.equalsIgnoreCase("Rain")) {
                message = "rain";
            } else if (weather.equalsIgnoreCase("Sun")) {
                message = "sun";
            } else {
                message = "snow";
            }
        }
        if (e.getSource() == minBox) {
            JComboBox box = (JComboBox) e.getSource();
            min = (String) box.getSelectedItem();
        }
        if (e.getSource() == hourBox) {
            JComboBox box = (JComboBox) e.getSource();
            hour = (String) box.getSelectedItem();
        }
        if (e.getSource() == getETA) {
            if (weatherBox.getSelectedIndex() == 0 || minBox.getSelectedIndex() == 0 ||
                    hourBox.getSelectedIndex() == 0) {
                String message = "Please ensure you have made a selection in each box.";
                MessageFrame nullFrame = new MessageFrame(message);
            }
            String time = hour + ":" + min;
            ResultFrame resultFrame = new ResultFrame(message, time, data);
            dispose();
        }

        if (e.getSource() == close) {
            dispose();
        }
    }
}
