package Graphics;

import api.Algorithms;
import api.DirectedWeightedGraphAlgorithms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LoadScreen {
    String statusLabel = "Status: Enter json path";
    GUI gui;

    public LoadScreen(GUI g){
        this.gui = g;
    }

    // initiate the Screen
    public void init(){

        JFrame frame = new JFrame("Directed Weighted Graph");
        frame.setSize(500,250);

        Container contentPane = frame.getContentPane();
        contentPane.setLayout(null);
        contentPane.setBackground(new Color(220, 190, 153));

        JLabel Header = new JLabel("Directed Weighted Graph -- Made by Eldad and Ilan");
        Header.setBounds(0,0, frame.getWidth(),30);
        Header.setHorizontalAlignment(JTextField.CENTER);
        Header.setForeground(new Color(10, 59, 73));
        Header.setFont(new Font("Serif", Font.BOLD, 18));
        contentPane.add(Header);

        JTextArea tx = new JTextArea("./data/G1.json");
        tx.setLocation(10,100);
        tx.setSize(250,50);
        contentPane.add(tx);

        JButton button = new JButton("Create graph");
        button.setLocation(260,100);
        button.setSize(160,50);
        contentPane.add(button);

        JLabel Status = new JLabel(this.statusLabel);
        Status.setBounds(10,150, frame.getWidth(),30);
        Status.setHorizontalAlignment(JTextField.CENTER);
        Status.setForeground(new Color(0, 0, 0));
        Status.setFont(new Font("Serif", Font.BOLD, 18));
        contentPane.add(Status);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String str = tx.getText();
                DirectedWeightedGraphAlgorithms algo = new Algorithms();
                try {
                    algo.load(str);

                    gui.update(algo);
                    gui.repaint();
                    gui.updateSize();
                    frame.dispose();

                } catch (IOException ex) {
                    tx.setText("Status: File Not Found!");
                    frame.repaint();
                    ex.printStackTrace();
                }
            }
        });

        frame.setVisible(true);

    }
}
