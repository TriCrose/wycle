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
 * <p>
 * Recommend getting the first instance as part of app start-up process
 */
public class LocationStore {

    // Store cities as LocationObjects
    private static ArrayList<LocationObject> cities;
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
        }
        return instance;
    }


    /**
     * @return The list of cities, this is sorted in alphabetical order
     */
    public static ArrayList<LocationObject> getCities() {

        getInstance();

        return cities;
    }


    /**
     * @param input the string input to be found in the city names
     * @return an ArrayList of the results, the results are sorted so that the cities which matched the input string
     * earlier in their name are given first
     */
    public static ArrayList<LocationObject> search(String input) {

        // Start with a linear search to return basic results
        ArrayList<LocationObject> results = new ArrayList<>();

        String lowerInput = input.toLowerCase();
        Map<LocationObject, Integer> cityIndices = new TreeMap<>();

        // for each city which contains the given string, put it with the index of the substring in the map
        for (LocationObject city : cities) {
            int substringIndex = city.getCity().toLowerCase().indexOf(lowerInput);
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
    private static ArrayList<LocationObject> getAllMatchingValues(Map<LocationObject, Integer> map, int value) {

        ArrayList<LocationObject> result = new ArrayList<>();
        for (Iterator<Map.Entry<LocationObject, Integer>> it = map.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<LocationObject, Integer> entry = it.next();
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

        String fileName = "data/simplemaps-worldcities-basic.csv";

        cities = new ArrayList<>();

        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.skip(1).forEach(l -> {
                String[] locations = l.split(",");
                LocationObject lo;
                if (locations.length >= 9) {
                    lo = new LocationObject(locations[0], locations[2], locations[3], locations[5], locations[8]);
                } else {
                    lo = new LocationObject(locations[0], locations[2], locations[3], locations[5], "");
                }
                cities.add(lo);
            });
        } catch (IOException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
        // Calculate this now to avoid recalculation as this will not change
        maxLength = 0;
        for (LocationObject s : cities) {
            if (s.getCity().length() > maxLength) {
                maxLength = s.getCity().length();
            }
        }
        // Have the list sorted so that if used to display the
        // possible places then it is easily searchable by humans
        cities.sort(Comparator.comparing(LocationObject::getCity));
    }


    public static void main(String[] args) {

        getInstance();
//        for (LocationObject s : cities) {
//            System.out.println(s);
//        }
        System.out.println(search("B"));
    }
}
