package main.frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MessageFrame extends JFrame implements ActionListener {
    JButton close;

    public MessageFrame(String msg) {
        Font font = new Font("Segoe UI Emoji", Font.PLAIN, 18);
        JLabel message = new JLabel(msg);
        message.setFont(font);
        add(message);

        close = new JButton("Close");
        close.setFont(font);
        close.addActionListener(this);
        add(close);

        setLayout(new FlowLayout());
        setVisible(true);
        setSize(500,150);
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == close) {
            dispose();
        }
    }
}
