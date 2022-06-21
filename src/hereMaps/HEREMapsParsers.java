package hereMaps;

import java.util.ArrayList;
import java.util.List;

public class HEREMapsParsers {
    public static List<Long> parseHEREMapsSummary(ArrayList<HEREMapsRouteSection> route) {
        long totalDuration = 0; // Maintained in case necessary later on
        long totalLength = 0;
        long totalBaseDuration = 0;

        for (HEREMapsRouteSection section : route) {
            totalDuration += section.getDuration();
            totalBaseDuration += section.getBaseDuration();
            totalLength += section.getLength();
        }

        return List.of(totalDuration, totalLength, totalBaseDuration);
    }

    public static ArrayList<String> parseHEREMapsPolyline(ArrayList<HEREMapsRouteSection> route) {
        ArrayList<String> polyline = new ArrayList<>();

        for (HEREMapsRouteSection section : route) {
            polyline.add(section.getPolyline());
        }
        return polyline;
    }
}
