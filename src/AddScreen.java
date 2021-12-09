import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddScreen {

    enum Mode{
        NODE,
        EDGE
    }

    Mode screen;
    GUI gui;

    public AddScreen(GUI g, Mode mode) {
        this.screen = mode;
        this.gui = g;
    }

    public void init() {

        JFrame frame = new JFrame("Directed Weighted Graph");
        frame.setSize(500, 250);

        Container contentPane = frame.getContentPane();
        contentPane.setLayout(null);
        contentPane.setBackground(new Color(220, 190, 153));

        JLabel Header = new JLabel("Directed Weighted Graph -- Made by Eldad and Ilan");
        Header.setBounds(0, 0, frame.getWidth(), 30);
        Header.setHorizontalAlignment(JTextField.CENTER);
        Header.setForeground(new Color(10, 59, 73));
        Header.setFont(new Font("Serif", Font.BOLD, 18));
        contentPane.add(Header);

        if(screen == Mode.NODE){
            AddNode(contentPane, frame);
        }
        else if(screen == Mode.EDGE){
            AddEdge(contentPane, frame);
        }

    }

    public void AddNode(Container contentPane, JFrame frame){
        JTextArea x = new JTextArea("35.2");
        x.setLocation(10, 100);
        x.setSize(125, 50);
        contentPane.add(x);

        JTextArea y = new JTextArea("32.105");
        y.setLocation(140, 100);
        y.setSize(120, 50);
        contentPane.add(y);

        JButton button = new JButton("Create Node");
        button.setLocation(260, 100);
        button.setSize(160, 50);
        contentPane.add(button);

        JLabel srcText = new JLabel("X coordinate");
        srcText.setBounds(10,75, 125,25);
        srcText.setHorizontalAlignment(JTextField.CENTER);
        srcText.setForeground(new Color(0, 0, 0));
        srcText.setFont(new Font("Serif", Font.BOLD, 18));
        contentPane.add(srcText);

        JLabel dstText = new JLabel("Y coordinate");
        dstText.setBounds(140,75, 120,25);
        dstText.setHorizontalAlignment(JTextField.CENTER);
        dstText.setForeground(new Color(0, 0, 0));
        dstText.setFont(new Font("Serif", Font.BOLD, 18));
        contentPane.add(dstText);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String xNode = x.getText();
                String yNode = y.getText();

                double xVal = Double.parseDouble(xNode);
                double yVal =  Double.parseDouble(yNode);

                gui.addNode(xVal,yVal);

                frame.dispose();

            }
        });

        frame.setVisible(true);
    }
    public void AddEdge(Container contentPane, JFrame frame){
        JTextArea src = new JTextArea("1");
        src.setLocation(10, 100);
        src.setSize(60, 50);
        contentPane.add(src);

        JTextArea dst = new JTextArea("2");
        dst.setLocation(75, 100);
        dst.setSize(60, 50);
        contentPane.add(dst);

        JTextArea weight = new JTextArea("1.7658");
        weight.setLocation(140, 100);
        weight.setSize(60, 50);
        contentPane.add(weight);

        JButton button = new JButton("Add Edge");
        button.setLocation(200, 100);
        button.setSize(160, 50);
        contentPane.add(button);

        JLabel srcText = new JLabel("Source");
        srcText.setBounds(10,75, 60,25);
        srcText.setHorizontalAlignment(JTextField.CENTER);
        srcText.setForeground(new Color(0, 0, 0));
        srcText.setFont(new Font("Serif", Font.BOLD, 18));
        contentPane.add(srcText);

        JLabel dstText = new JLabel("Destination");
        dstText.setBounds(75,75, 60,25);
        dstText.setHorizontalAlignment(JTextField.CENTER);
        dstText.setForeground(new Color(0, 0, 0));
        dstText.setFont(new Font("Serif", Font.BOLD, 18));
        contentPane.add(dstText);

        JLabel weightText = new JLabel("Weight");
        weightText.setBounds(140,75, 60,25);
        weightText.setHorizontalAlignment(JTextField.CENTER);
        weightText.setForeground(new Color(0, 0, 0));
        weightText.setFont(new Font("Serif", Font.BOLD, 18));
        contentPane.add(weightText);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String srcNode = src.getText();
                String dstNode = dst.getText();
                String weightGet = weight.getText();

                int srcKey = Integer.parseInt(srcNode);
                int dstKey = Integer.parseInt(dstNode);
                double weightVal = Double.parseDouble(weightGet);

                gui.addEdge(srcKey, dstKey, weightVal);

                frame.dispose();

            }
        });

        frame.setVisible(true);
    }

}
