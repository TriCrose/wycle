import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class LocationStore {

    //TODO: Store all the cities which can be used for locations and make them easily searchable.

    // Store cities
    private static ArrayList<String> cities;
    // Singleton class so store the current instance
    private static LocationStore instance;

    private LocationStore() {
        cities = new ArrayList<>();
        readInCities();
    }

    public static LocationStore getInstance() {
        if (null == instance){
            instance = new LocationStore();
            return instance;
        }
        return instance;
    }

    private void readInCities() {

        String fileName = "data/cities.txt";

        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.forEach(c -> cities.add(c));
        } catch (IOException e) {
            //TODO: change the handle
            System.out.println("File not here");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        LocationStore ls = getInstance();
        for (String s : cities) {
            System.out.println(s);
        }
    }
}
