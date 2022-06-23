package tests;

import hereMaps.HEREMapsRouteSection;
import hereMaps.HEREMapsAPIClient;
import org.testng.annotations.Test;
import utils.PolylineEncoderDecoder;

import java.util.List;
import java.util.ArrayList;

public class TestHereMaps {
    private HEREMapsAPIClient client;

    @Test(priority = 1)
    public void testTokenGeneration() {
        String accessTokenUrl = "https://gdhanani2-dev.internal.iot.ocs.oraclecloud.com/iotapps/privateclientapi/v2/oauth/hereMapToken";
        String accessTokenUsername = "iot-cloudops_ww_grp";
        String accessTokenPassword = "Welcome1234#";
        client = new HEREMapsAPIClient(accessTokenUrl, accessTokenUsername, accessTokenPassword);
    }

    @Test(priority = 2)
    public void testRoutesNoStops() {
        // To note that origin and source refer to the starting point

        ArrayList<HEREMapsRouteSection> response = client.getRoute(
                52.51375,13.42462,
                48.21815,16.38995,
                true
        );

        HEREMapsRouteSection section1 = response.get(0);

        assert section1.getPolyline() != null;
        assert section1.getBaseDuration() != 0 && section1.getDuration() != 0 && section1.getLength() != 0;
    }

    @Test(priority = 2)
    public void testPolylineDecoder() {
        String polyline = client.getRoute(
                52.51375,13.42462,
                48.21815,16.38995,
                true
        ).get(0).getPolyline();

        List<PolylineEncoderDecoder.LatLngZ> decodedRoute = PolylineEncoderDecoder.decode(polyline);

        PolylineEncoderDecoder.LatLngZ first = decodedRoute.get(0);
        System.out.println("In testing polyline decode,\n" + first.toString());
    }

    @Test(priority = 2)
    public void testGeoLocation() {
        String response = client.getGeoLocation(48.2181679,16.3899064);
        assert response != null;
    }

    @Test(priority = 3)
    public void testRoutes() {
        // To note that origin and source refer to the starting point

        ArrayList<Double> stopLats = new ArrayList<>();
        ArrayList<Double> stopLongs = new ArrayList<>();

        // Stop 1
        stopLats.add(52.517871);
        stopLongs.add(13.434175);

        // Stop 2
        stopLats.add(52.52426);
        stopLongs.add(13.43);

        ArrayList<HEREMapsRouteSection> response = client.getRoute(
                52.51375,13.42462,
                48.21815,16.38995,
                stopLats, stopLongs,
                true
        );

        HEREMapsRouteSection section1 = response.get(0);

        assert section1.getPolyline() != null;
        assert section1.getBaseDuration() != 0 && section1.getDuration() != 0 && section1.getLength() != 0;
    }
}
