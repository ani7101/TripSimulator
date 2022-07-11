package tests;

import hereMaps.HereMapsAPIClient;
import hereMaps.accessToken.AccessToken;
import hereMaps.deserializerClasses.HereMapsRouteSection;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.PolylineEncoderDecoder;

import java.util.List;
import java.util.ArrayList;

public class TestHereMaps {

    AccessToken accessToken;

    @Test(priority = 1)
    @Parameters({"accessTokenUrl", "username", "password"})
    public void testTokenGeneration(String accessTokenUrl, String username, String password) {
        accessToken = new AccessToken(accessTokenUrl, username, password);

        System.out.println(accessToken.get());
    }

    @Test(priority = 2)
    public void testRoutesNoStops() {

        ArrayList<HereMapsRouteSection> response = HereMapsAPIClient.getRoute(
                52.51375,13.42462,
                48.21815,16.38995,
                accessToken.get()
        );

        HereMapsRouteSection section1 = response.get(0);

        assert section1.getPolyline() != null;
        assert section1.getBaseDuration() != 0 && section1.getDuration() != 0 && section1.getLength() != 0;
    }

    @Test(priority = 2)
    public void testPolylineDecoder() {
        String polyline = HereMapsAPIClient.getRoute(
                52.51375,13.42462,
                48.21815,16.38995,
                accessToken.get()
        ).get(0).getPolyline();

        List<PolylineEncoderDecoder.LatLngZ> decodedRoute = PolylineEncoderDecoder.decode(polyline);

        PolylineEncoderDecoder.LatLngZ first = decodedRoute.get(0);
        System.out.println("In testing polyline decode,\n" + first.toString());
    }

    @Test(priority = 2)
    public void testGeoLocation() {
        String response = HereMapsAPIClient.getGeoLocation(48.2181679,16.3899064, accessToken.get());
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

        ArrayList<HereMapsRouteSection> response = HereMapsAPIClient.getRoute(
                52.51375,13.42462,
                48.21815,16.38995,
                stopLats, stopLongs,
                accessToken.get()
        );

        HereMapsRouteSection section1 = response.get(0);

        assert section1.getPolyline() != null;
        assert section1.getBaseDuration() != 0 && section1.getDuration() != 0 && section1.getLength() != 0;
    }
}
