package Trip;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Collections;

public class TripTemplates {
    private ArrayList<GeoPosition> sources = new ArrayList<GeoPosition>();
    private ArrayList<GeoPosition> destinations = new ArrayList<GeoPosition>();
    private ArrayList<GeoPosition> stops = new ArrayList<GeoPosition>();

    public GeoPosition getRandomSource() {
        return sources.get(getRandomNumber(0, sources.size()));
    }

    public GeoPosition getRandomDestination() {
        return destinations.get(getRandomNumber(0, destinations.size()));
    }

    public GeoPosition getRandomStop() {
        return stops.get(getRandomNumber(0, stops.size()));
    }

    public ArrayList<GeoPosition> getRandomSource(int count) {
        return getRandomShuffle(sources, count);
    }

    public ArrayList<GeoPosition> getRandomStop(int count) {
        return getRandomShuffle(sources, count);
    }

    public ArrayList<GeoPosition> getRandomDestination(int count) {
        return getRandomShuffle(sources, count);
    }

    // Setters
    public void addSource(GeoPosition source) { sources.add(source); }
    public void addDestination(GeoPosition destination) { destinations.add(destination); }
    public void addStop(GeoPosition stop) { stops.add(stop); }

    public void populateSources(ArrayList<GeoPosition> sources) { this.sources = sources; }
    public void populateDestinations(ArrayList<GeoPosition> destinations) { this.destinations = destinations; }
    public void populateStops(ArrayList<GeoPosition> stops) { this.stops = stops; }

    // Utils
    private ArrayList<GeoPosition> getRandomShuffle(ArrayList<GeoPosition> list, int count) {
        ArrayList<GeoPosition> res = new ArrayList<GeoPosition>();
        Collections.shuffle(list);
        for (int i = 0; i < count; i++) { res.add(list.get(i)); }
        return res;
    }

    private int getRandomNumber(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}
