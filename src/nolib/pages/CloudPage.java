package nolib.pages;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import nolib.AppPage;
import nolib.AppWindow;

public class CloudPage implements AppPage {
	private String cloudText;
	private BufferedImage cloud;
	
	public CloudPage() {
		cloudText = "Here is a cloud";
		try {  cloud = ImageIO.read(new File("img/cloud.png")); }
		catch (IOException e) { e.printStackTrace(); }
	}
	
	@Override
	public void update(long dt) {
		
	}

	@Override
	public void render(Graphics2D g) {
		g.setFont(AppPage.DAY_FONT);
		AppPage.drawString(g, cloudText, AppWindow.width()/2, 30);
		g.drawImage(cloud, 50, 50, null);
		g.drawLine(0, 0, 0, AppWindow.height());
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