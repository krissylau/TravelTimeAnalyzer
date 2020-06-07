package main.frames;

import main.exceptions.InvalidTimeException;
import main.model.Data;
import main.model.Entry;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class EntrySetUpFrame extends JFrame implements ActionListener {
    private Data data;
    JComboBox weatherBox;
    JButton close;
    JButton addEntry;
    String message;
    JComboBox startMinBox;
    JComboBox startHourBox;
    JComboBox endMinBox;
    JComboBox endHourBox;
    String startMin;
    String startHour;
    String endMin;
    String endHour;

    public EntrySetUpFrame(Data data) {
        this.data = data;
        SpringLayout layout = new SpringLayout();
        Container container = getContentPane();
        Font font = new Font("Segoe UI Emoji", Font.PLAIN, 18);
        JLabel instructions = new JLabel("Enter today's data: ");
        instructions.setFont(new Font ("Segoe UI Emoji", Font.PLAIN, 20));
        layout.putConstraint(SpringLayout.WEST, instructions, 5, SpringLayout.WEST, container);
        layout.putConstraint(SpringLayout.NORTH, instructions, 10, SpringLayout.NORTH, container);
        add(instructions);

        JLabel weatherNote = new JLabel("Select weather: ");
        weatherNote.setFont(font);
        layout.putConstraint(SpringLayout.WEST, weatherNote, 5, SpringLayout.WEST, container);
        layout.putConstraint(SpringLayout.NORTH, weatherNote, 35, SpringLayout.NORTH, container);
        add(weatherNote);

        String[] weatherStrings = {"-----------", "Rain", "Sun", "Snow"};
        weatherBox = new JComboBox(weatherStrings);
        weatherBox.setFont(font);
        weatherBox.setSelectedIndex(0);
        layout.putConstraint(SpringLayout.WEST, weatherBox, 130, SpringLayout.WEST, container);
        layout.putConstraint(SpringLayout.NORTH, weatherBox, 30, SpringLayout.NORTH, container);
        add(weatherBox);
        weatherBox.addActionListener(this);

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

        JLabel startTime = new JLabel("Enter departure time: ");
        startTime.setFont(font);
        layout.putConstraint(SpringLayout.WEST, startTime, 5, SpringLayout.WEST, container);
        layout.putConstraint(SpringLayout.NORTH, startTime, 65, SpringLayout.NORTH, container);
        add(startTime);

        String[] startHour = new String[25];
        startHour[0] = "--";
        generateTime(startHour, 24);
        startHourBox = new JComboBox(startHour);
        startHourBox.setFont(font);
        startHourBox.setSelectedIndex(0);
        layout.putConstraint(SpringLayout.WEST, startHourBox, 100, SpringLayout.WEST, container);
        layout.putConstraint(SpringLayout.NORTH, startHourBox, 85, SpringLayout.NORTH, container);
        add(startHourBox);
        startHourBox.addActionListener(this);
        startHourBox.setKeySelectionManager(hourKey);

        JLabel colon = new JLabel(":");
        colon.setFont(font);
        layout.putConstraint(SpringLayout.WEST, colon, 162, SpringLayout.WEST, container);
        layout.putConstraint(SpringLayout.NORTH, colon, 90, SpringLayout.NORTH, container);
        add(colon);

        String[] startMin = new String[61];
        startMin[0] = "--";
        generateTime(startMin, 60);
        startMinBox = new JComboBox(startMin);
        startMinBox.setFont(font);
        startMinBox.setSelectedIndex(0);
        layout.putConstraint(SpringLayout.WEST, startMinBox, 180, SpringLayout.WEST, container);
        layout.putConstraint(SpringLayout.NORTH, startMinBox, 85, SpringLayout.NORTH, container);
        add(startMinBox);
        startMinBox.addActionListener(this);
        startMinBox.setKeySelectionManager(minKey);

        JLabel endTime = new JLabel("Enter arrival time: ");
        endTime.setFont(font);
        layout.putConstraint(SpringLayout.WEST, endTime, 5, SpringLayout.WEST, container);
        layout.putConstraint(SpringLayout.NORTH, endTime, 120, SpringLayout.NORTH, container);

        add(endTime);

        String[] endHour = new String[25];
        endHour[0] = "--";
        generateTime(endHour, 24);
        endHourBox = new JComboBox(endHour);
        endHourBox.setFont(font);
        endHourBox.setSelectedIndex(0);
        add(endHourBox);
        layout.putConstraint(SpringLayout.WEST, endHourBox, 100, SpringLayout.WEST, container);
        layout.putConstraint(SpringLayout.NORTH, endHourBox, 140, SpringLayout.NORTH, container);
        endHourBox.addActionListener(this);
        endHourBox.setKeySelectionManager(hourKey);

        JLabel colon2 = new JLabel(":");
        colon2.setFont(font);
        layout.putConstraint(SpringLayout.WEST, colon2, 162, SpringLayout.WEST, container);
        layout.putConstraint(SpringLayout.NORTH, colon2, 145, SpringLayout.NORTH, container);
        add(colon2);

        String[] endMin = new String[61];
        endMin[0] = "--";
        generateTime(endMin, 60);
        endMinBox = new JComboBox(endMin);
        endMinBox.setFont(font);
        endMinBox.setSelectedIndex(0);
        layout.putConstraint(SpringLayout.WEST, endMinBox, 180, SpringLayout.WEST, container);
        layout.putConstraint(SpringLayout.NORTH, endMinBox, 140, SpringLayout.NORTH, container);
        add(endMinBox);
        endMinBox.addActionListener(this);
        endMinBox.setKeySelectionManager(minKey);

        JLabel format = new JLabel("**Arrival time must be after departure time**");
        format.setFont(new Font("Segoe UI Emoji", Font.ITALIC, 15));
        layout.putConstraint(SpringLayout.WEST, format, 5, SpringLayout.WEST, container);
        layout.putConstraint(SpringLayout.NORTH, format, 180, SpringLayout.NORTH, container);

        add(format);

        addEntry = new JButton("Add Entry");
        layout.putConstraint(SpringLayout.WEST, addEntry, 50, SpringLayout.WEST, container);
        layout.putConstraint(SpringLayout.NORTH, addEntry,  200, SpringLayout.NORTH, container);
        addEntry.setFont(font);
        close = new JButton("Close");
        layout.putConstraint(SpringLayout.WEST, close, 200, SpringLayout.WEST, container);
        layout.putConstraint(SpringLayout.NORTH, close,  200, SpringLayout.NORTH, container);
        close.setFont(font);
        add(addEntry);
        addEntry.addActionListener(this);
        add(close);
        close.addActionListener(this);

        pack();
        setLayout(layout);
        setVisible(true);
        setSize(350,350);
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
        JComboBox box;
        if (e.getSource() == weatherBox) {
            box = (JComboBox) e.getSource();
            String weather = (String) box.getSelectedItem();
            if (weather.equalsIgnoreCase("Rain")) {
                message = "rain";
            } else if (weather.equalsIgnoreCase("Sun")) {
                message = "sun";
            } else {
                message = "snow";
            }
        }
        if (e.getSource() == startMinBox) {
            box = (JComboBox) e.getSource();
            startMin = (String) box.getSelectedItem();
        }
        if (e.getSource() == startHourBox) {
            box = (JComboBox) e.getSource();
            startHour = (String) box.getSelectedItem();
        }
        if (e.getSource() == endMinBox) {
            box = (JComboBox) e.getSource();
            endMin = (String) box.getSelectedItem();
        }
        if (e.getSource() == endHourBox) {
            box = (JComboBox) e.getSource();
            endHour = (String) box.getSelectedItem();
        }
        if (e.getSource() == addEntry) {
            ArrayList<JComboBox> boxes = new ArrayList<>();
            boxes.add(weatherBox);
            boxes.add(startHourBox);
            boxes.add(startMinBox);
            boxes.add(endMinBox);
            boxes.add(endHourBox);
            for(JComboBox b: boxes) {
                if(b.getSelectedIndex() == 0) {
                    String message = "Please ensure you have made a selection in each box.";
                    MessageFrame nullFrame = new MessageFrame(message);
                    break;
                }
            }
            entrySetUp();
        }
        if (e.getSource() == close) {
            dispose();
        }
        try {
            data.save();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
    }

    private void entrySetUp() {
        String w = message;
        String end = endHour + ":" + endMin;
        String start = startHour + ":" + startMin;
        Entry e = null;
        try {
            e = new Entry(w, start, end);
            data.addToData(e);
            data.estimateDurations();
            dispose();
            EntrySetUpFrame newEntry = new EntrySetUpFrame(data);
            MessageFrame addedMsg = new MessageFrame("New entry has been added to data.");
        } catch (InvalidTimeException ex) {
            TimeErrorFrame errorFrame = new TimeErrorFrame();
        }

    }
}
