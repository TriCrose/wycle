package nolib;

import javax.swing.ImageIcon;

public class ImageContainer {
	private ImageIcon image;
	private int x;
	private int y;
	
	public ImageIcon getImage() {
		return image;
	}
	
	public void setImage(ImageIcon image) {
		this.image = image;
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public ImageContainer(ImageIcon image, int x, int y, int xAlign, int yAlign) {
		this.image = image;
		if (xAlign == AppPage.LEFT) this.x = x;
		else if (xAlign == AppPage.CENTRE) this.x = x - image.getIconWidth()/2;
		else if (xAlign == AppPage.RIGHT) this.x = x - image.getIconWidth();
		
		if (yAlign == AppPage.TOP) this.y = y;
		else if (yAlign == AppPage.CENTRE) this.y = y - image.getIconHeight()/2;
		else if (yAlign == AppPage.BOTTOM) this.y = y - image.getIconHeight();
	}
	
	public ImageContainer(ImageIcon image, int x, int y) {
		this(image, x, y, AppPage.LEFT, AppPage.TOP);
	}
	
	public boolean inside(int testX, int testY) {
		if (x <= testX && testX <= x + image.getIconWidth()) {
			if (y <= testY && testY <= y + image.getIconHeight()) {
				return true;
			}
		}
		return false;
	}
}
