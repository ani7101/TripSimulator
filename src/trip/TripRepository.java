package trip;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Collections;

public class TripRepository {
    private ArrayList<ArrayList<Double>> sources = new ArrayList<>();
    private ArrayList<ArrayList<Double>> destinations = new ArrayList<>();
    private ArrayList<ArrayList<Double>> stops = new ArrayList<>();

    public TripRepository(ArrayList<ArrayList<Double>> sources, ArrayList<ArrayList<Double>> destinations, ArrayList<ArrayList<Double>> stops) {
        this.sources = sources;
        this.destinations = destinations;
        this.stops = stops;
    }

    public TripRepository() {}

    public ArrayList<Double> getRandomSource() {
        return sources.get(getRandomNumber(0, sources.size()));
    }

    public ArrayList<Double> getRandomDestination() {
        return destinations.get(getRandomNumber(0, destinations.size()));
    }

    public ArrayList<Double> getRandomStop() {
        return stops.get(getRandomNumber(0, stops.size()));
    }

    public ArrayList<ArrayList<Double>> getRandomSource(int count) {
        return getRandomShuffle(sources, count);
    }

    public ArrayList<ArrayList<Double>> getRandomStop(int count) {
        return getRandomShuffle(stops, count);
    }

    public ArrayList<ArrayList<Double>> getRandomDestination(int count) {
        return getRandomShuffle(sources, count);
    }

    // Setters
    public void addSource(ArrayList<Double> source) { sources.add(source); }
    public void addDestination(ArrayList<Double> destination) { destinations.add(destination); }
    public void addStop(ArrayList<Double> stop) { stops.add(stop); }

    public void populateSources(ArrayList<ArrayList<Double>> sources) { this.sources = sources; }
    public void populateDestinations(ArrayList<ArrayList<Double>> destinations) { this.destinations = destinations; }
    public void populateStops(ArrayList<ArrayList<Double>> stops) { this.stops = stops; }


    // Utils
    private ArrayList<ArrayList<Double>> getRandomShuffle(ArrayList<ArrayList<Double>> list, int count) {
        ArrayList<ArrayList<Double>> res = new ArrayList<>();
        Collections.shuffle(list);
        for (int i = 0; i < count; i++) { res.add(list.get(i)); }
        return res;
    }

    private int getRandomNumber(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}
