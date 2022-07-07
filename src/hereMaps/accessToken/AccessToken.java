package hereMaps.accessToken;

import java.time.Duration;
import java.time.LocalDateTime;

public class AccessToken {

    //region Class variables
    //---------------------------------------------------------------------------------------

    private String accessToken;

    private LocalDateTime lastGenerated;

    private final AccessTokenAPIClient accessTokenClient;


    //endregion
    //region Constructor
    //---------------------------------------------------------------------------------------

    public AccessToken(String accessTokenUrl, String accessTokenUsername, String accessTokenPassword) {
        accessTokenClient = new AccessTokenAPIClient(accessTokenUrl, accessTokenUsername, accessTokenPassword);

        load();
    }


    //endregion
    //region Utils
    //---------------------------------------------------------------------------------------

    /**
     * Sends a request to the internal IoT server and stores the response HERE Maps OAuth-1.0a token in {@link AccessToken#accessToken}
     */
    public void load() {
        accessToken = accessTokenClient.get();
        lastGenerated = LocalDateTime.now();
    }

    /**
     * Sends a request to the internal IoT server to obtain the HERE Maps OAuth-1.0a access token.
     * @return String: Access token generated
     */
    public String get() {
        if (lastGenerated == null || Duration.between(lastGenerated, LocalDateTime.now()).toHours() > 24) {
            load();
        }
        return accessToken;
    }

    //endregion

}
