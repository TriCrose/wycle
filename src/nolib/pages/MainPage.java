package nolib.pages;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import apixu.WeatherDay;
import apixu.WeatherForecast;
import apixu.WeatherHour;
import nolib.*;

public class MainPage implements AppPage {
	private String currentDay;
	private int dayIndex = 0;
	private Color fontColour;
	private static WeatherForecast weatherForecast;
	private ImageContainer compassIcon, cyclingCoefficientIcon, weatherIcon, windIcon;
	private double rainForecast, tempForecast, windForecast;
	
	public MainPage(int dayIndex) {
		this.dayIndex = dayIndex;
		
		if (dayIndex != 0) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, dayIndex);
            currentDay = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault());
        } else {
            currentDay = "Now";
        }
		
		weatherForecast = new WeatherForecast();
		fontColour = (weatherForecast.getWeather().getIsDay() == 1) ? Color.BLACK : Color.WHITE;
		
		compassIcon = new ImageContainer(getCompassIcon(), AppWindow.width() - 5, 5, RIGHT, TOP);
		cyclingCoefficientIcon = new ImageContainer(getCyclingCoefficientIcon(getCyclingCoefficient()), AppWindow.width()/4, 100, CENTRE, CENTRE);
		weatherIcon = new ImageContainer(weatherForecast.getWeather().getIcon(100, 100), 3*AppWindow.width()/4, 100, CENTRE, CENTRE);

        // Get current weather for today or get average day weather for other days
        if (dayIndex == 0) {

            WeatherHour currentWeather = weatherForecast.getWeather();

            rainForecast = currentWeather.getRain();
            tempForecast = currentWeather.getTemp();
            windForecast = currentWeather.getWind();
        } else {
            WeatherDay currentWeather = weatherForecast.getDaySummary(dayIndex);

            rainForecast = currentWeather.getTotalRain()/24;
            tempForecast = currentWeather.getAvgTemp();
            windForecast = currentWeather.getMaxWind();
        }
        
		windIcon = new ImageContainer(getWindIcon(windForecast, 85, 50), 5*AppWindow.width()/6, 165, CENTRE, CENTRE);
	}
	
	@Override
	public void update(long dt) {
		
	}

	@Override
	public void render(Graphics2D g) {
		g.setFont(LARGE_FONT);
		g.setColor(fontColour);
		
		// Draw the icons for the main weather display, bike coefficient and symbol
		AppPage.drawString(g, currentDay, 15, 40, LEFT);
		AppPage.drawString(g, weatherForecast.getLocation(), AppWindow.width()/2, 40, CENTRE);
		AppPage.drawImage(g, compassIcon);
		AppPage.drawImage(g, weatherIcon);
		AppPage.drawImage(g, cyclingCoefficientIcon);
		
		g.setFont(MEDIUM_FONT);
		
		// Draw the rain, wind and temperature
		AppPage.drawString(g, rainForecast + " mm", AppWindow.width()/6, 175, CENTRE);
		AppPage.drawString(g, tempForecast + " °C", AppWindow.width()/2, 175, CENTRE);
		AppPage.drawImage(g, windIcon);
		
		// Detailed rows will be drawn for each hour of the current day
		ArrayList<WeatherHour> weatherHour = weatherForecast.getWeather(dayIndex);
        ArrayList<WeatherHour> tomorrowWeatherHour = null;		// May be needed so leave as null for now

        // Current hour of the day
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        
        // Size of each panel
        int panelSize = 46;
        
        // Set font
        g.setFont(SMALL_FONT);
        
        for (int i = 0; i < 10; i++) {
        	g.setColor(i % 2 == 0 ? new Color(255, 255, 255, 100) : new Color(195, 195, 195, 100));
        	int yPosition = 200 + i * panelSize;
        	g.fillRect(10, yPosition, AppWindow.width() - 20, panelSize);
        	g.setColor(fontColour);
        	
        	if (i + hour < weatherHour.size()) { // still today
        		AppPage.drawString(g, weatherHour.get(i + hour).getTime(), 50, yPosition + 29, CENTRE);
        		AppPage.drawString(g, weatherHour.get(i + hour).getRain() + " mm", 125, yPosition + 29, CENTRE);
        		AppPage.drawString(g, weatherHour.get(i + hour).getTemp() + " °C", 200, yPosition + 29, CENTRE);
        		AppPage.drawImage(g, new ImageContainer(getWindIcon(weatherHour.get(i + hour).getWind(), 50, 50), 275, yPosition + 25, CENTRE, CENTRE));
        		AppPage.drawImage(g, new ImageContainer(weatherHour.get(i + hour).getIcon(), 340, yPosition + 25, CENTRE, CENTRE));
        	} else if (i + hour == weatherHour.size() && i != 9) { // enough room for tomorrow
        		g.setFont(MEDIUM_FONT);
        		AppPage.drawString(g, "Tomorrow", AppWindow.width()/2, yPosition + 29, CENTRE);
        		tomorrowWeatherHour = weatherForecast.getWeather(dayIndex + 1);
        		g.setFont(SMALL_FONT);
            } else if (i + hour > weatherHour.size()) { // tomorrow
            	assert tomorrowWeatherHour != null;
            	AppPage.drawString(g, tomorrowWeatherHour.get((i - 1 + hour) % 24).getTime(), 50, yPosition + 29, CENTRE);
        		AppPage.drawString(g, tomorrowWeatherHour.get((i - 1 + hour) % 24).getRain() + " mm", 125, yPosition + 29, CENTRE);
        		AppPage.drawString(g, tomorrowWeatherHour.get((i - 1 + hour) % 24).getTemp() + " °C", 200, yPosition + 29, CENTRE);
        		AppPage.drawImage(g, new ImageContainer(getWindIcon(tomorrowWeatherHour.get((i - 1 + hour) % 24).getWind(), 50, 50), 275, yPosition + 25, CENTRE, CENTRE));
        		AppPage.drawImage(g, new ImageContainer(tomorrowWeatherHour.get((i - 1 + hour) % 24).getIcon(), 340, yPosition + 25, CENTRE, CENTRE));
            }
        }
	}
	
	@Override
	public void mouseClick(int x, int y) {
		if (cyclingCoefficientIcon.inside(x, y)) JOptionPane.showMessageDialog(null, getCyclingCoefficientMessage(getCyclingCoefficient()));
	}
	
	private ImageIcon getCompassIcon() {
        //construct filepath for the icon
        String filepath = "art/use_these/other_icons/compass.png";

        ImageIcon icon = new ImageIcon(filepath); //convert png to ImageIcon
        Image image = icon.getImage(); // transform it
        Image newimg = image.getScaledInstance(40, 40, Image.SCALE_SMOOTH); // scale it the smooth way
        icon = new ImageIcon(newimg);  // transform it back
        return icon;
    }
	
	/**
     * Get the cycling coefficient for the current weather
     *
     * @return cycling coefficient as a double
     */
    private double getCyclingCoefficient() {
        //all weather data referenced in README
        double wind = weatherForecast.getWeather().getWind();
        double temp = weatherForecast.getWeather().getTemp();
        double rain = weatherForecast.getWeather().getRain();
        //standardized temperature, subtracting the mean of 12 and measuring increments per 2.5 degrees
        temp = (temp - 15) / 2.5;
        //standardized wind, subtracting the mean of 9 and measuring increments per 5mph
        wind = (wind - 9) / 5;
        if (wind < 0) wind = 0;
        //units of rain are fairly small, so scale them up to match
        rain = rain * 5;

        //calculate coefficient
        double coeff = 1 + temp * 0.1 - (Math.pow(wind, 2)) * 0.075 - rain * 0.15;
        if (coeff > 1) coeff = 1;
        if (coeff < 0) coeff = 0;

        return coeff;
    }
    
    /**
     * @param coeff the cycling coefficient value
     * @return the relevant description for that value
     */
    private String getCyclingCoefficientMessage(double coeff) {
        String message;
        if (coeff < 0.2) {
            message = "<html><div style='text-align: center;'>Terrible conditions for cycling, be careful!</html>";
        } else if (coeff < 0.4) {
            message = "<html><div style='text-align: center;'>Poor conditions for cycling</html>";
        } else if (coeff < 0.6) {
            message = "<html><div style='text-align: center;'>Okay conditions for cycling</html>";
        } else if (coeff < 0.8) {
            message = "<html><div style='text-align: center;'>Good conditions for cycling</html>";
        } else {
            message = "<html><div style='text-align: center;'>Great conditions for cycling, have fun!</html>";
        }
        return message;
    }

    /**
     * Get the corresponding image for the coefficient value given and scale it
     * @param coeff value to use to select image
     * @return icon of cycling coefficient
     */
    private ImageIcon getCyclingCoefficientIcon(double coeff) {
        //begin constructing filepath for icon
        String filepath = "art/use_these/bike_coeffs/";
        //access appropriate bike icon depending on given coefficient
        if (coeff < 0.2) {
            filepath += "bike1x.png";
        } else if (coeff < 0.4) {
            filepath += "bike2x.png";
        } else if (coeff < 0.6) {
            filepath += "bike3x.png";
        } else if (coeff < 0.8) {
            filepath += "bike4x.png";
        } else {
            filepath += "bike5x.png";
        }

        ImageIcon icon = new ImageIcon(filepath); //convert png to ImageIcon
        Image image = icon.getImage(); // transform it
        Image newimg = image.getScaledInstance(85, 50, Image.SCALE_SMOOTH); // scale it the smooth way
        icon = new ImageIcon(newimg);  // transform it back
        return icon;
    }
    
    /**
     * Returns the wind icon scaled to the desired values and the icon depends on the wind value
     *
     * @param wind   current wind value
     * @param width  desired width of the icon
     * @param height desired height of the icon
     * @return ImageIcon of wind of desired size and strength
     */
    public static ImageIcon getWindIcon(double wind, int width, int height) {

        //begin constructing filepath for icon
        String filepath = "art/use_these/wind_icons/";
        //access appropriate wind icon depending on wind strength
        if (wind < 6) {
            filepath += "wind0.png";
        } else if (wind < 8) {
            filepath += "wind1.png";
        } else if (wind < 10) {
            filepath += "wind2.png";
        } else {
            filepath += "wind3.png";
        }
        // get, scale and return the icon
        ImageIcon icon = new ImageIcon(filepath); //convert png to ImageIcon
        Image image = icon.getImage(); // transform it
        Image newimg = image.getScaledInstance(width, height, Image.SCALE_SMOOTH); // scale it the smooth way
        icon = new ImageIcon(newimg);  // transform it back
        return icon;
    }
}