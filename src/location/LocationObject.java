package location;

/**
 * Class to store relevant location data
 */
public class LocationObject implements Comparable<LocationObject> {

    private String city;
    private String lat;
    private String lng;
    private String country;
    private String province;


    public LocationObject(String city, String lat, String lng, String country, String province) {

        this.city = city;
        this.lat = lat;
        this.lng = lng;
        this.country = country;
        this.province = province;
    }


    @Override
    public String toString() {

        return "LocationObject{" + "city='" + city + '\'' + ", lat='" + lat + '\'' + ", lng='" + lng + '\'' + ", " + "country='" + country + '\'' + ", province='" + province + '\'' + '}';
    }


    public String getCity() {

        return city;
    }


    public String getLat() {

        return lat;
    }


    public String getLng() {

        return lng;
    }


    public String getCountry() {

        return country;
    }


    public String getProvince() {

        return province;
    }


    @Override
    public int compareTo(LocationObject o) {

        return this.city.compareTo(o.city);
    }
}
