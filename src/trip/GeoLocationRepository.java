package trip;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Collections;

/**
 * Stores geolocations suitable for the source, stops and the destinations which can be retrieved easily for the simulation
 */
public class GeoLocationRepository {
    private ArrayList<ArrayList<Double>> sources = new ArrayList<>();
    private ArrayList<ArrayList<Double>> destinations = new ArrayList<>();
    private ArrayList<ArrayList<Double>> stops = new ArrayList<>();


    //endregion
    //region Constructors
    //---------------------------------------------------------------------------------------


    public GeoLocationRepository(ArrayList<ArrayList<Double>> sources, ArrayList<ArrayList<Double>> destinations, ArrayList<ArrayList<Double>> stops) {
        this.sources = sources;
        this.destinations = destinations;
        this.stops = stops;
    }

    public GeoLocationRepository() {}



    //endregion
    //region Randomized getters
    //---------------------------------------------------------------------------------------


    /**
     * Generates a randomized source from the list of source locations
     * @return ArrayList of geoCoordinates inclusive of [Latitude, longitude, ...]
     */
    public ArrayList<Double> getRandomSource() {
        return sources.get(getRandomNumber(0, sources.size() - 1));
    }

    /**
     * Generates a randomized destination from the list of destination locations
     * @return ArrayList of geoCoordinates inclusive of [Latitude, longitude, ...]
     */
    public ArrayList<Double> getRandomDestination() {
        return destinations.get(getRandomNumber(0, destinations.size() - 1));
    }

    /**
     * Generates a randomized stop from the list of stop locations
     * @return ArrayList of geoCoordinates inclusive of [Latitude, longitude, ...]
     */
    public ArrayList<Double> getRandomStop() {
        return stops.get(getRandomNumber(0, stops.size() - 1));
    }

    /**
     * Generates multiple randomized sources from the list of source locations
     * @param count Number of geoLocations required
     * @return Arraylist of geo coordinates which are each given by another ArrayList of the form [Latitude, Longitude, ...]
     */
    public ArrayList<ArrayList<Double>> getRandomSource(int count) {
        return getRandomShuffle(sources, count);
    }

    /**
     * Generates multiple randomized destinations from the list of destination locations
     * @param count Number of geoLocations required
     * @return Arraylist of geo coordinates which are each given by another ArrayList of the form [Latitude, Longitude, ...]
     */
    public ArrayList<ArrayList<Double>> getRandomStop(int count) {
        return getRandomShuffle(stops, count);
    }

    /**
     * Generates multiple randomized stops from the list of stop locations
     * @param count Number of geoLocations required
     * @return Arraylist of geo coordinates which are each given by another ArrayList of the form [Latitude, Longitude, ...]
     */
    public ArrayList<ArrayList<Double>> getRandomDestination(int count) {
        return getRandomShuffle(sources, count);
    }



    //endregion
    //region Setters
    //---------------------------------------------------------------------------------------


    public void addSource(ArrayList<Double> source) { sources.add(source); }


    public void addDestination(ArrayList<Double> destination) { destinations.add(destination); }


    public void addStop(ArrayList<Double> stop) { stops.add(stop); }

    public void populateSources(ArrayList<ArrayList<Double>> sources) { this.sources = sources; }

    public void populateDestinations(ArrayList<ArrayList<Double>> destinations) { this.destinations = destinations; }

    public void populateStops(ArrayList<ArrayList<Double>> stops) { this.stops = stops; }

    // Getting number of elements
    public int destinationsCount() {
        return stops.size() - 1;
    }

    public int sourcesCount() {
        return stops.size() - 1;
    }

    public int stopsCount() {
        return stops.size() - 1;
    }

    public ArrayList<ArrayList<Double>> getSources() {
        return sources;
    }

    public ArrayList<ArrayList<Double>> getDestinations() {
        return destinations;
    }

    public ArrayList<ArrayList<Double>> getStops() {
        return stops;
    }


    //endregion
    //region Utils
    //---------------------------------------------------------------------------------------

    /**
     * Shuffles the whole array and returns the required number of elements
     * @param list ArrayList of all the elements
     * @param count Number of elements required
     * @return ArrayList of randomly selected elements
     */
    private ArrayList<ArrayList<Double>> getRandomShuffle(ArrayList<ArrayList<Double>> list, int count) {
        ArrayList<ArrayList<Double>> res = new ArrayList<>();
        Collections.shuffle(list);
        for (int i = 0; i < count; i++) { res.add(list.get(i)); }
        return res;
    }

    private int getRandomNumber(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    //endregion

}
