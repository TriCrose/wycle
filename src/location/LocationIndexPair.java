package location;

public class LocationIndexPair {

    private LocationObject locationObject;
    private int index;


    public LocationIndexPair(LocationObject locationObject, int index) {

        this.locationObject = locationObject;
        this.index = index;
    }


    public LocationObject getLocationObject() {

        return locationObject;
    }


    public int getIndex() {

        return index;
    }
}
