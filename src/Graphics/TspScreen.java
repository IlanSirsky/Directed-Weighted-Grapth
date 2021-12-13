package Graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TspScreen {

    GUI gui;

    public TspScreen(GUI g) {
        this.gui = g;
    }

    // initiate the Screen
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

        JTextArea city = new JTextArea("0,1,2,5,7");
        city.setLocation(10, 100);
        city.setSize(250, 50);
        contentPane.add(city);

        JButton button = new JButton("Start TSP Search");
        button.setLocation(260, 100);
        button.setSize(160, 50);
        contentPane.add(button);

        JLabel srcText = new JLabel("Enter Nodes you wish to add to your city");
        srcText.setBounds(10,75, 410,25);
        srcText.setHorizontalAlignment(JTextField.CENTER);
        srcText.setForeground(new Color(0, 0, 0));
        srcText.setFont(new Font("Serif", Font.BOLD, 18));
        contentPane.add(srcText);


        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cityText = city.getText();

                String[] arr = cityText.split(",");

                int[] cityFinal = new int[arr.length];
                int counter = 0;
                for(String a: arr){
                    cityFinal[counter++] = Integer.parseInt(a);
                }

                gui.tspGUI(cityFinal);

                frame.dispose();

            }
        });

        frame.setVisible(true);

    }
}
