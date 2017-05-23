package nolib;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

public interface AppPage {
	public static final Font LARGE_FONT = new Font("Trebuchet MS", Font.PLAIN, 28);
	public static final Font MEDIUM_FONT = new Font("Trebuchet MS", Font.PLAIN, 20);
	public static final Font SMALL_FONT = new Font("Arial", Font.BOLD, 15);
	
	// Different ways to align text
	public static final int LEFT = 0;
	public static final int CENTRE = 1;
	public static final int RIGHT = 2;
	public static final int TOP = 3;
	public static final int BOTTOM = 4;
	
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
	
	// Image drawing function
	public static void drawImage(Graphics2D g, ImageContainer i) {
		g.drawImage(i.getImage().getImage(), i.getX(), i.getY(), null);
	}
	
	// Updates the page given the number of milliseconds since the last update
	public abstract void update(long dt);
	
	// Draws the page to the app window
	public abstract void render(Graphics2D g);
	
	// Called when the mouse is released
	public void mouseClick(int x, int y);
}