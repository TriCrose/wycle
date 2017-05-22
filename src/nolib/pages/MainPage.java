package nolib.pages;

import java.awt.Graphics2D;

import nolib.*;

public class MainPage implements AppPage {
	private String day;
	
	public MainPage() {
		day = "Today";
	}
	
	@Override
	public void update(long dt) {
		
	}

	@Override
	public void render(Graphics2D g) {
		g.setFont(AppPage.DAY_FONT);
		AppPage.drawString(g, day, AppWindow.width()/2, 60);
		g.drawLine(0, 100, AppWindow.width(), 100);
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