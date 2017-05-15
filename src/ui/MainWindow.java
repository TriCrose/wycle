package ui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class MainWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	private static int WIDTH = 394;
	private static int HEIGHT = 700;
	
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
				
		day = addPanel(0, 0.04);
		icons = addPanel(1, 0.1);
		rainWind = addPanel(2, 0.1);
		detailed = addPanel(3, 0.5);
		
		setVisible(true);
	}
	
	private static GridBagConstraints constraints = new GridBagConstraints();
	private JPanel addPanel(int gridY, double weight) {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		
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