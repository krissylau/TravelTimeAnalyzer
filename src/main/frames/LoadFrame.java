package main.frames;

import main.model.Data;
import main.model.Entry;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LoadFrame extends JFrame implements ActionListener {
    JButton close;

    public LoadFrame(Data data) {
        Font font = new Font("Segoe UI Emoji", Font.PLAIN, 18);

        ArrayList<ArrayList<Entry>> arrays = new ArrayList<>();
        arrays.add(data.getData("rain"));
        arrays.add(data.getData("sun"));
        arrays.add(data.getData("snow"));
        data.estimateDurations();

        for(ArrayList<Entry> a: arrays) {
            String text = "";
            JTextArea textArea = new JTextArea(10,15);
            JScrollPane scroll = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            JLabel w;
            Entry en = a.get(0);
            String weather = en.getWeather();
            Integer time = data.getEstimate(weather);
            if (weather.equalsIgnoreCase("rain")) {
                w = new JLabel("Rain Data  ");
            }
            else if (weather.equalsIgnoreCase("sun")) {
                w = new JLabel("Sun Data   ");
            }
            else {
                w = new JLabel("Snow Data");
            }
            w.setFont(font);
            add(w);
            for (Entry e: a) {
                text += e.getDuration() + "\n";
            }
            text += "\nEstimated travel time:\n" + time + " minutes";
            textArea.setText(text);
            textArea.setFont(font);
            add(scroll);
        }

        close = new JButton("Close");
        close.setFont(font);
        add(close);
        close.addActionListener(this);

        setLayout(new FlowLayout(FlowLayout.CENTER));
        setVisible(true);
        setSize(400, 1000);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == close) {
            dispose();
        }
    }
}
