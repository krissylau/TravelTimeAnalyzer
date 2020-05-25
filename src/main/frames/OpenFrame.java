package main.frames;

import main.model.Data;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OpenFrame extends JFrame implements ActionListener {
    private Data data;
    JButton newEntry;
    JButton getETA;
    JButton seePast;

    public OpenFrame(Data data) {
        this.data = data;
        data.estimateDurations();
        Font font = new Font("Segoe UI Emoji", Font.PLAIN, 18);
        JLabel welcome = new JLabel("Travel Duration Tracker");
        welcome.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 30));

        JLabel note = new JLabel("use to track travel time to UBC based on weather conditions");
        note.setFont(new Font("Segoe UI Emoji", Font.ITALIC, 18));

        newEntry = new JButton("New Entry");
        newEntry.setFont(font);

        add(welcome);
        add(note);
        add(newEntry);
        newEntry.addActionListener(this);

        getETA = new JButton("Get estimated time of arrival");
        getETA.setFont(font);
        add(getETA);
        getETA.addActionListener(this);

        seePast = new JButton("See past entries");
        seePast.setFont(font);
        add(seePast);
        seePast.addActionListener(this);

        setLayout(new FlowLayout(FlowLayout.CENTER, 30, 30));
        setVisible(true);
        setSize(550,310);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == newEntry) {
            EntrySetUpFrame setUp = new EntrySetUpFrame(data);
        }
        if(e.getSource() == getETA) {
            ETAFrame ETAFrame = new ETAFrame(data);
        }
        if(e.getSource() == seePast) {
            LoadFrame loadFrame = new LoadFrame(data);
        }
    }
}
