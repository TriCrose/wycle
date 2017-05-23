package nolib;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.util.List;
import java.util.ArrayList;

import nolib.pages.*;

public class AppWindow extends JFrame implements MouseListener {
    private static final long serialVersionUID = 1L;
    
    // Track mouse position
    private Point lastMousePosition;
    private boolean mouseDown = false;
    private int swipe;
    
    // Singleton
    private static AppWindow instance;
    public static AppWindow get() {
    	if (instance == null) instance = new AppWindow();
    	return instance;
    }
    
    private static final int WIDTH = 394;
    private static final int HEIGHT = 700;
    private static final Color BACKGROUND_COLOUR = new Color(138, 192, 239);

    // Get inner width
    public static int width() {
    	return instance.getContentPane().getWidth();
    }
    
    // Get inner height
    public static int height() {
    	return instance.getContentPane().getHeight();
    }
    
    // Panel where drawing happens
    private JPanel drawingPanel;
    
    // List of pages (one for each day)
    private List<AppPage> days;
    
    // Current day
    private int currentPage = 0;
    
    private AppWindow() {
        super("Wycle");
        days = new ArrayList<>();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        addMouseListener(this);
        
        drawingPanel = new JPanel() {
        	private static final long serialVersionUID = 1L;
        	
        	{ setBackground(BACKGROUND_COLOUR); }
        	
        	@Override
        	public void paintComponent(Graphics g) {
        		super.paintComponent(g);
        		Graphics2D g2 = (Graphics2D) g;
        		
        		// Turn on antialiasing
        		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        		
        		for (int i = 0; i < days.size(); i++) {
        			AffineTransform translate = new AffineTransform();
        			translate.translate((i - currentPage) * width() + swipe, 0);
        			g2.setTransform(translate);
        			days.get(i).render(g2);
        		}
        	}
        };
        add(drawingPanel);
        
        setVisible(true);
    }

    // Begin the update-render loop
    public void startLoop() {
    	for (int i = 0; i < 6; i++) days.add(new MainPage(i));
    	long t = System.currentTimeMillis();
    	
    	while (isShowing()) {
    		// Calculate the delta time since the last update
    		long dt = System.currentTimeMillis() - t;
    		t = System.currentTimeMillis();
    		
    		if (mouseDown) swipe = lastMousePosition.x - MouseInfo.getPointerInfo().getLocation().x;
    		
    		// Draw page
    		drawingPanel.repaint();
    		
    		// Cap the framerate at 60
    		if (dt < 50) try { Thread.sleep(50 - dt); } catch (InterruptedException e) { e.printStackTrace(); }
    	}
    }

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		lastMousePosition = MouseInfo.getPointerInfo().getLocation();
		mouseDown = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseDown = false;
		if (lastMousePosition.equals(MouseInfo.getPointerInfo().getLocation())) {
			JOptionPane.showMessageDialog(null, "");
			days.get(currentPage).mouseClick(e.getX(), e.getY());
		}
	}
	
	// Start the app
    public static void main(String[] args) {
        AppWindow window = AppWindow.get();
        window.startLoop();
    }
}