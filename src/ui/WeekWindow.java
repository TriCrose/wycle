package ui;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;

public class WeekWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 394;
	private static final int HEIGHT = 700;
	private static final Color backColour = new Color(138, 192, 239);
    private static GridBagConstraints constraints = new GridBagConstraints();
    private JPanel header;
	private JPanel dayIcons;
	
	public WeekWindow() {
		super("Wycle");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
		setLayout(new GridBagLayout());
		getContentPane().setBackground(backColour);

		header = addPanel_Header(0, 0.04);
		JLabel labelLoc = new JLabel("Cambridge (placeholder)"); // to be set dynamically
		labelLoc.setFont(new Font("Trebuchet MS", Font.PLAIN, 24));
		labelLoc.setHorizontalAlignment(JLabel.CENTER);
		header.add(labelLoc);

		dayIcons = addPanel_DayIcons(1, 1.0);
		for (int i = 1; i <= 6; i++)
		{
			//int x = i % 2;
			//int y = (i + 1) / 2;
			
			dayIcons.add(createDayIcon(i));
		}
        setVisible(true);
	}


    public static void main(String args[]) {

        new WeekWindow();
    }

    // this just for testing, because the days will have to be chosen dynamically
    private String dayName(int day)
    {
    	switch(day)
    	{
    	case 0:
    		return "NOW";
    	case 1:
    		return "MON";
    	case 2:
    		return "TUE";
    	case 3:
    		return "WED";
    	case 4:
    		return "THU";
    	case 5: 
    		return "FRI";
    	case 6:
    		return "SAT";
    	case 7:
    		return "SUN";
    		default:
    			return "OOPS";
    	}
    }

    private JPanel createDayIcon(int day)
    {
    	return createPanelWithText(dayName(day));
    }
    
    private JPanel createPanelWithText(String text)
    {
    	JPanel panel = new JPanel(new BorderLayout());
    	panel.setBackground(Color.WHITE);
    	
    	JLabel label = new JLabel(text);
    	panel.add(label, BorderLayout.CENTER);
    	
    	label.setHorizontalAlignment(JLabel.CENTER);
    	
    	panel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

    	return panel;
    }
    
    
	private JPanel addPanel_Header(int gridY, double weight) {
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


    private JPanel addPanel_DayIcons(int gridY, double weight) {

        JPanel panel = new JPanel(new GridLayout(2, 3));
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
    
}