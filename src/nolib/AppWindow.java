package nolib;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import nolib.pages.*;

public class AppWindow extends JFrame implements MouseListener {
    private static final long serialVersionUID = 1L;
    
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
    
    // Current page to display
    private AppPage currentPage;
    
    // Next page (used when transitioning)
    private AppPage nextPage;
    
    // Pending page change
    public void setPage(AppPage nextPage) {
    	this.nextPage = nextPage;
    }
    
    private AppWindow() {
        super("Wycle");
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
        		
        		// Draw the current page
        		if (currentPage != null) currentPage.render(g2);
        	}
        };
        add(drawingPanel);
        
        setVisible(true);
    }

    // Begin the update-render loop
    public void startLoop() {
    	long t = System.currentTimeMillis();
    	
    	while (isShowing()) {
    		// Calculate the delta time since the last update
    		long dt = System.currentTimeMillis() - t;
    		t = System.currentTimeMillis();
    		
    		// If a page change is pending then do it now
    		if (nextPage != null) {
    			if (currentPage != null) currentPage.destroy();		// First, destroy the old one
    			currentPage = nextPage;								// Now select the new one
    			nextPage = null;									// Set the next one to null
    		}
    		
    		// Now update and render providing we have a valid page
    		if (currentPage != null) {
    			currentPage.update(dt);
    			drawingPanel.repaint();
    		}
    		
    		// Cap the framerate at 60
    		if (dt < 17) try { Thread.sleep(17 - dt); } catch (InterruptedException e) { e.printStackTrace(); }
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
		currentPage.mouseDown(e.getX(), e.getY());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		currentPage.mouseUp(e.getX(), e.getY());
	}
	
	// Start the app
    public static void main(String[] args) {
        AppWindow window = AppWindow.get();
        window.setPage(new MainPage());
        window.startLoop();
    }
}