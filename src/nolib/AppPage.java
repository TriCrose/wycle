package nolib;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public interface AppPage {
	public static final Font DAY_FONT = new Font("Trebuchet MS", Font.BOLD, 30);
	
	// Different ways to align text
	public static final int LEFT = 0;
	public static final int CENTRE = 1;
	public static final int RIGHT = 2;
	
	// Better text-drawing method because it allows for arbitrary alignment
	public static void drawString(Graphics2D g, String str, int x, int y, int align) {
		FontMetrics metrics = g.getFontMetrics();
		
		int newX;
		if (align == LEFT) newX = x;
		else if (align == CENTRE) newX = x - metrics.stringWidth(str)/2;
		else newX = x - metrics.stringWidth(str);
		
		g.drawString(str, newX, y);
	}
	
	public static void drawString(Graphics2D g, String str, int x, int y) {
		drawString(g, str, x, y, CENTRE);
	}
	
	// Scalable image-drawing function
	public static void drawScaledImage(Graphics2D g, BufferedImage img, int x, int y, float scale) {
		g.drawImage(img, x, y, (int) scale * img.getWidth(), (int) scale * img.getHeight(), null);
	}
	
	// Updates the page given the number of milliseconds since the last update
	public abstract void update(long dt);
	
	// Draws the page to the app window
	public abstract void render(Graphics2D g);
	
	// Called when the mouse is pressed
	public void mouseDown(int x, int y);
	
	// Called when the mouse is released
	public void mouseUp(int x, int y);
	
	// Clean up any resources used by the page
	public abstract void destroy();
}