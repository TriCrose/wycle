package nolib.pages;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import nolib.*;

public class MainPage implements AppPage {
	private String day;
	private BufferedImage compass, bike;
	
	public MainPage() {
		day = "Today";
		try { 
			compass = ImageIO.read(new File("img/compass.png"));
			bike = ImageIO.read(new File("img/bike.png"));
		} catch (IOException e) { e.printStackTrace(); }
	}
	
	@Override
	public void update(long dt) {
		
	}

	@Override
	public void render(Graphics2D g) {
		g.setFont(AppPage.DAY_FONT);
		AppPage.drawString(g, day, AppWindow.width()/2, 60);
		g.drawImage(compass, AppWindow.width() - compass.getWidth() - 5, 5, null);
		g.drawLine(0, 100, AppWindow.width(), 100);
		g.drawImage(bike, 0, 100, null);
	}
	
	@Override
	public void mouseDown(int x, int y) {
		
	}

	@Override
	public void mouseUp(int x, int y) {
		
	}

	@Override
	public void destroy() {
	}
}