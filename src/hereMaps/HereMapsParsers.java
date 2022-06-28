package hereMaps;

import java.util.ArrayList;
import java.util.List;

public class HereMapsParsers {

    /**
     * Parses and returns the total duration, total base duration and the total length for the input route
     * @param route Section wise response obtained from the here maps route request
     * @return List(Long): total duration, total length, total base duration
     */
    public static List<Long> parseHEREMapsSummary(ArrayList<HereMapsRouteSection> route) {
        long totalDuration = 0; // Maintained in case necessary later on
        long totalLength = 0;
        long totalBaseDuration = 0;

        for (HereMapsRouteSection section : route) {
            totalDuration += section.getDuration();
            totalBaseDuration += section.getBaseDuration();
            totalLength += section.getLength();
        }

        return List.of(totalDuration, totalLength, totalBaseDuration);
    }


    /**
     * Parses and retrieves the route as a polyline from HereMapsRouteSection
     * @see HereMapsRouteSection
     * @param route Section wise response obtained from the here maps route request
     * @return ArrayList(String): Routes in polyline
     */
    public static ArrayList<String> parseHEREMapsPolyline(ArrayList<HereMapsRouteSection> route) {
        ArrayList<String> polyline = new ArrayList<>();

        for (HereMapsRouteSection section : route) {
            polyline.add(section.getPolyline());
        }
        return polyline;
    }
}
