package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

public class MainWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 394;
	private static final int HEIGHT = 700;
	private static final Color backColour = new Color(138, 192, 239);
	
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
				
		day = addPanel(0, 0.04);
		JLabel labelDay = new JLabel("Today");
		labelDay.setFont(new Font("Trebuchet MS", Font.PLAIN, 24));
		labelDay.setHorizontalAlignment(JLabel.CENTER);
		day.add(labelDay);
		
		icons = addPanel(1, 0.1);
		rainWind = addPanel(2, 0.1);
		detailed = addPanel(3, 0.5);
		
		setVisible(true);
	}
	
	private static GridBagConstraints constraints = new GridBagConstraints();
	private JPanel addPanel(int gridY, double weight) {
		JPanel panel = new JPanel(new BorderLayout());
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

	public static void main(String args[]) {
		new MainWindow();
	}
}