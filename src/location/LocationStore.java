package location;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

/**
 * A singleton class to store all the locations which can be chosen by the user.
 * Provides functionality to read in a list of cities from a text file; get the list
 * of cities and also search the list for cities containing a given string.
 * 
 * Recommend getting the first instance as part of app start-up process
 */
public class LocationStore {

    // Store cities
    private static ArrayList<String> cities;
    // Singleton class so store the current instance
    private static LocationStore instance;
    // Store the maximum length of all the city strings
    private static int maxLength;


    private LocationStore() {

        readInCities();
    }


    /**
     * This class is a singleton so this method is used to access the current copy.
     *
     * @return The single instance to the LocationStore
     */
    public static LocationStore getInstance() {

        if (null == instance) {
            instance = new LocationStore();
            return instance;
        }
        return instance;
    }


    /**
     * @return The list of cities, this is sorted in alphabetical order
     */
    public static ArrayList<String> getCities() {

        return cities;
    }


    /**
     * @param input the string input to be found in the city names
     * @return an ArrayList of the results, the results are sorted so that the cities which matched the input string
     * earlier in their name are given first
     */
    public static ArrayList<String> search(String input) {

        // Start with a linear search to return basic results
        ArrayList<String> results = new ArrayList<>();

        String lowerInput = input.toLowerCase();
        Map<String, Integer> cityIndices = new TreeMap<>();

        // for each city which contains the given string, put it with the index of the substring in the map
        for (String city : cities) {
            int substringIndex = city.toLowerCase().indexOf(lowerInput);
            if (substringIndex != -1) {
                cityIndices.put(city, substringIndex);
            }
        }
        int i = 0;
        while (cityIndices.size() > 0 && i < maxLength) {
            results.addAll(getAllMatchingValues(cityIndices, i));
            i++;
        }

        return results;
    }


    /**
     * @param map   the map of (city name, index of matching) pairs
     * @param value the current value which we care about
     * @return A list of all the city names which have the match position at the value position
     */
    private static ArrayList<String> getAllMatchingValues(Map<String, Integer> map, int value) {

        ArrayList<String> result = new ArrayList<>();
        for (Iterator<Map.Entry<String, Integer>> it = map.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, Integer> entry = it.next();
            if (entry.getValue() == value) {
                result.add(entry.getKey());
                it.remove();
            }
        }
        return result;
    }


    /**
     * Read in the list of cities to a list from a file, assuming one city name per line in the file
     */
    private static void readInCities() {

        String fileName = "data/cities.txt";

        cities = new ArrayList<>();

        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.forEach(c -> cities.add(c));
        } catch (IOException e) {
            // Expect file to be there (Bad, I know)
            System.out.println("File not found");
            e.printStackTrace();
        }
        // Calculate this now to avoid recalculation as this will not change
        maxLength = 0;
        for (String s : cities) {
            if (s.length() > maxLength) {
                maxLength = s.length();
            }
        }
        // Have the list sorted so that if used to display the
        // possible places then it is easily searchable by humans
        Collections.sort(cities);
    }


    public static void main(String[] args) {

        getInstance();
        for (String s : cities) {
            System.out.println(s);
        }
        System.out.println(search("B"));
    }
}
