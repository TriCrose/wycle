package app;

import apixu.WeatherForecast;
import ui.AppParams;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

/**
 * Main class which holds the home screen, this includes: day; icons; rain and wind; detailed view;
 */
public class AppWindow extends JFrame {

    private static final long serialVersionUID = 1L;
    // store the weather forecast to reduce api calls
    private static WeatherForecast mWeatherForecast = new WeatherForecast();
    private final AppWindow thisInstance = this;
    // index to keep track of which day we are displaying
    private int mDayIndex = 0; // change when switching days
    // current day to display
    private String mCurrentDay;
    private Color fontColor;
    // 0 for main page, 1 for week window, 2 for location
    private int currentPage = 0;
    private JPanel panel;


    public AppWindow() {

        super("Wycle");

        // set the background image depending on the time of day
        setBackgroundImage();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setSize(AppParams.WIDTH, AppParams.HEIGHT);
        setLocationRelativeTo(null);
        setLayout(new GridLayout());

        // Set font colour depending on day or night
        if (mWeatherForecast.getWeather().getIsDay() == 1) fontColor = Color.black;
        else fontColor = Color.white;

        goToMainPage(0);
        setVisible(true);

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {

            boolean ignorePress = true;


            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                if (ignorePress) {
                    ignorePress = false;
                    return false;
                }

                int key = e.getKeyCode();
                System.out.println(key);

                if (key == KeyEvent.VK_RIGHT) {
                    if (mDayIndex < 5) {
                        mDayIndex += 1;
                        System.out.println(mDayIndex);
                        goToMainPage(getDayIndex());
                    }
                    // new main window
                    // with next day
                } else if (key == KeyEvent.VK_LEFT) {
                    if (mDayIndex > 0) {
                        mDayIndex -= 1;
                        System.out.println(mDayIndex);
                        goToMainPage(getDayIndex());
                    }
                } else if (key == KeyEvent.VK_UP) {
                    System.out.println("up");
                    goToWeekPage();
                }

                ignorePress = true;
                return false;
            }
        });
    }


    /**
     * @return WeatherForecast object containing the current weather and forecast
     */

    public static WeatherForecast getmWeatherForecast() {

        return mWeatherForecast;
    }


    public static void main(String args[]) {

        new AppWindow();
    }
    

    public int getDayIndex() {
    	return mDayIndex;
    }


    public String getCurrentDay() {
    	return mCurrentDay;
    }


    public void setCurrentDay(String day) {
    	mCurrentDay = day;
    }


    public Color getFontColor() {
    	return fontColor;
    }


    // Functions for navigation
    public void goToMainPage(int dayIndex) {
    	mDayIndex = dayIndex;
    	if (panel != null) remove(panel);
    	add(panel = new MainPanel(this));
    	currentPage = 0;
    	panel.repaint();
    	panel.revalidate();
    	repaint();
    	revalidate();
    }


    public void goToWeekPage() {
    	if (panel != null) remove(panel);
    	add(panel = new WeekPanel(this));
    	currentPage = 1;
    	panel.repaint();
    	panel.revalidate();
    	repaint();
    	revalidate();
    }


    public void goToLocationPage() {
    	if (panel != null) remove(panel);
    	add(panel = new LocationPanel(this));
    	currentPage = 2;
    	panel.repaint();
    	panel.revalidate();
    	repaint();
    	revalidate();
    }


    public int getCurrentPage() {
    	return currentPage;
    }


    /**
     * Get the appropriate background image and then display it
     */
    private void setBackgroundImage() {
        boolean isDay = mWeatherForecast.getWeather().getIsDay() == 1;

        String path = "art/use_these/backgrounds/";
        if (isDay) path += "background_day[bg].png";
        else path += "background_night[bg].png";

        try {
            Image backgroundImage = ImageIO.read(new File(path));
            JLabel background = new JLabel(new ImageIcon(backgroundImage));
            this.setContentPane(background);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}