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
        Font font = new Font("Segoe UI Emoji", Font.PLAIN, 18);
        JLabel instructions = new JLabel("Enter today's data: ");
        instructions.setFont(new Font ("Segoe UI Emoji", Font.PLAIN, 20));
        add(instructions);

        JLabel weatherNote = new JLabel("Select weather for entry: ");
        weatherNote.setFont(font);
        add(weatherNote);

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

        String[] weatherStrings = {"-----------", "Rain", "Sun", "Snow"};
        weatherBox = new JComboBox(weatherStrings);
        weatherBox.setFont(font);
        weatherBox.setSelectedIndex(0);
        add(weatherBox);
        weatherBox.addActionListener(this);


        JLabel startTime = new JLabel("Enter departure time");
        startTime.setFont(font);
        add(startTime);

        String[] startHour = new String[25];
        startHour[0] = "--";
        generateTime(startHour, 24);
        startHourBox = new JComboBox(startHour);
        startHourBox.setFont(font);
        startHourBox.setSelectedIndex(0);
        add(startHourBox);
        startHourBox.addActionListener(this);
        startHourBox.setKeySelectionManager(hourKey);

        JLabel colon = new JLabel(":");
        colon.setFont(font);
        add(colon);

        String[] startMin = new String[61];
        startMin[0] = "--";
        generateTime(startMin, 60);
        startMinBox = new JComboBox(startMin);
        startMinBox.setFont(font);
        startMinBox.setSelectedIndex(0);
        add(startMinBox);
        startMinBox.addActionListener(this);
        startMinBox.setKeySelectionManager(minKey);

        JLabel endTime = new JLabel("Enter arrival time");
        endTime.setFont(font);
        add(endTime);

        String[] endHour = new String[25];
        endHour[0] = "--";
        generateTime(endHour, 24);
        endHourBox = new JComboBox(endHour);
        endHourBox.setFont(font);
        endHourBox.setSelectedIndex(0);
        add(endHourBox);
        endHourBox.addActionListener(this);
        endHourBox.setKeySelectionManager(hourKey);

        JLabel colon2 = new JLabel(":");
        colon2.setFont(font);
        add(colon2);

        String[] endMin = new String[61];
        endMin[0] = "--";
        generateTime(endMin, 60);
        endMinBox = new JComboBox(endMin);
        endMinBox.setFont(font);
        endMinBox.setSelectedIndex(0);
        add(endMinBox);
        endMinBox.addActionListener(this);
        endMinBox.setKeySelectionManager(minKey);

        JLabel format = new JLabel("**Arrival time must be after departure time**");
        format.setFont(new Font("Segoe UI Emoji", Font.ITALIC, 15));
        add(format);

        addEntry = new JButton("Add Entry");
        addEntry.setFont(font);
        close = new JButton("Close");
        close.setFont(font);
        add(addEntry);
        addEntry.addActionListener(this);
        add(close);
        close.addActionListener(this);

        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        setVisible(true);
        setSize(390,350);
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
        } catch (InvalidTimeException ex) {
            TimeErrorFrame errorFrame = new TimeErrorFrame();
        }

    }
}
