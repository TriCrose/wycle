package ui;


/**
 * This class stores parameters common for many classes of the app.
 * It is not to be instantiated, but used as merely a static, constant data store.
 */
public final class AppParams {

	private AppParams()
	{} // can't have a final abstract class but can have this to achieve the same
	
	public static int WIDTH = 394;  // screen width
	public static int HEIGHT = 700; // screen height

}
