package ui;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;

public class MainWindow extends JFrame {

    private static final long serialVersionUID = 1L;
    private static final int WIDTH = 394;
    private static final int HEIGHT = 700;
    private static final Color backColour = new Color(138, 192, 239);
    private static GridBagConstraints constraints = new GridBagConstraints();

    private JPanel day;
    private JPanel icons;
    private JPanel rainWind;
    private JPanel detailed;


    public MainWindow() {

        super("Wycle");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(backColour);

        day = addPanelBorderLayout(0, 0.04);
        JLabel labelDay = new JLabel("Today");
        labelDay.setFont(new Font("Trebuchet MS", Font.PLAIN, 24));
        labelDay.setHorizontalAlignment(JLabel.CENTER);
        day.add(labelDay);

        icons = addPanelGridLayout(1, 0.1, 1, 2);
        drawIcons();
        rainWind = addPanelGridLayout(2, 0.1, 1, 3);
        drawRainWind();
        detailed = addPanelGridLayout(3, 0.5, 0, 1);
        drawDetailed();

        setVisible(true);
    }


    public static void main(String args[]) {

        new MainWindow();
    }


    private JPanel addPanel(JPanel panel, int gridY, double weight) {

        panel.setBackground(backColour);
        panel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = gridY;
        constraints.weightx = 1.0;
        constraints.weighty = weight;

        add(panel, constraints);
        return panel;
    }


    private JPanel addPanelBorderLayout(int gridY, double weight) {

        JPanel panel = new JPanel(new BorderLayout());
        return addPanel(panel, gridY, weight);
    }


    private JPanel addPanelGridLayout(int gridY, double weight, int rows, int columns) {

        JPanel panel = new JPanel(new GridLayout(rows, columns));
        return addPanel(panel, gridY, weight);
    }


    private void drawIcons() {

        JLabel labelBikeCoefficient = new JLabel("bike coefficient");
        JLabel labelWeatherIcon = new JLabel("weather icon");

        labelBikeCoefficient.setHorizontalAlignment(JLabel.CENTER);
        labelWeatherIcon.setHorizontalAlignment(JLabel.CENTER);

        icons.add(labelBikeCoefficient);
        icons.add(labelWeatherIcon);
    }

    private void drawRainWind() {

        JLabel labelRain = new JLabel("Rain");
        JLabel labelTemp = new JLabel("temp here");
        JLabel labelWind = new JLabel("wind here");

        labelRain.setHorizontalAlignment(JLabel.CENTER);
        labelTemp.setHorizontalAlignment(JLabel.CENTER);
        labelWind.setHorizontalAlignment(JLabel.CENTER);

        rainWind.add(labelRain);
        rainWind.add(labelTemp);
        rainWind.add(labelWind);
    }


    private void drawDetailed() {

        for (int i = 0; i < 10; i++) {
            DetailedRow dr = new DetailedRow();
            detailed.add(dr.getPanel());
        }
    }
}