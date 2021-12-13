package Graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShortPathScreen {

    GUI gui;

    public ShortPathScreen(GUI g) {
        this.gui = g;
    }

    public void init() {

        JFrame frame = new JFrame("Directed Weighted Graph");
        frame.setSize(500, 250);

        Container contentPane = frame.getContentPane();
        contentPane.setLayout(null);
        contentPane.setBackground(new Color(220, 190, 153));

        JLabel Header = new JLabel("Directed Weighted Graph -- Made by Eldad ,Ilan and Nir");
        Header.setBounds(0, 0, frame.getWidth(), 30);
        Header.setHorizontalAlignment(JTextField.CENTER);
        Header.setForeground(new Color(10, 59, 73));
        Header.setFont(new Font("Serif", Font.BOLD, 18));
        contentPane.add(Header);

        JTextArea src = new JTextArea("1");
        src.setLocation(10, 100);
        src.setSize(125, 50);
        contentPane.add(src);

        JTextArea dst = new JTextArea("2");
        dst.setLocation(140, 100);
        dst.setSize(120, 50);
        contentPane.add(dst);

        JButton button = new JButton("Get Shortest Path");
        button.setLocation(260, 100);
        button.setSize(160, 50);
        contentPane.add(button);

        JLabel srcText = new JLabel("Source");
        srcText.setBounds(10,75, 125,25);
        srcText.setHorizontalAlignment(JTextField.CENTER);
        srcText.setForeground(new Color(0, 0, 0));
        srcText.setFont(new Font("Serif", Font.BOLD, 18));
        contentPane.add(srcText);

        JLabel dstText = new JLabel("Destination");
        dstText.setBounds(140,75, 120,25);
        dstText.setHorizontalAlignment(JTextField.CENTER);
        dstText.setForeground(new Color(0, 0, 0));
        dstText.setFont(new Font("Serif", Font.BOLD, 18));
        contentPane.add(dstText);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String srcNode = src.getText();
                String dstNode = dst.getText();

                int srcKey = Integer.parseInt(srcNode);
                int dstKey = Integer.parseInt(dstNode);

                gui.shortestPath(srcKey, dstKey);

                frame.dispose();

            }
        });

        frame.setVisible(true);

    }

}
