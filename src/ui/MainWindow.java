package ui;

import javax.swing.JFrame;

public class MainWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	
	public MainWindow() {
		super("Wycle");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setSize(394, 700);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public static void main(String args[]) {
		new MainWindow();
	}
}