package ui;

import javax.swing.*;
import java.awt.*;

public class DetailedRow {

    private JPanel row;


    public DetailedRow() {

    }


    public JPanel getPanel() {

        row = new JPanel(new GridLayout(1, 0));

        JLabel labelTime = new JLabel("time");
        JLabel labelrain = new JLabel("rain");
        JLabel labeltemp = new JLabel("temp");
        JLabel labelwind = new JLabel("wind");
        JLabel labelicon = new JLabel("icon");

        labelTime.setHorizontalAlignment(JLabel.CENTER);
        labelrain.setHorizontalAlignment(JLabel.CENTER);
        labeltemp.setHorizontalAlignment(JLabel.CENTER);
        labelwind.setHorizontalAlignment(JLabel.CENTER);
        labelicon.setHorizontalAlignment(JLabel.CENTER);

        row.add(labelTime);
        row.add(labelrain);
        row.add(labeltemp);
        row.add(labelwind);
        row.add(labelicon);

        row.setBackground(new Color(138, 192, 239));

        return row;
    }
}
