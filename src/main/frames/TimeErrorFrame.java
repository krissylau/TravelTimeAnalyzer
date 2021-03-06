package main.frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimeErrorFrame extends JFrame implements ActionListener {
    JButton close;

    public TimeErrorFrame() {
        Font font = new Font("Segoe UI Emoji", Font.PLAIN, 18);
        JLabel message = new JLabel("Please enter a arrival time that is after the departure time.");
        message.setFont(font);
        add(message);

        close = new JButton("Close");
        close.setFont(font);
        add(close);
        close.addActionListener(this);

        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        setVisible(true);
        setSize(500, 150);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == close) {
            dispose();
        }
    }
}
