package org.koenighotze.wiremocktryout;

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;

/**
 * @author David Schmitz
 */
public class RequestUtils {
    public static ResponseDefinitionBuilder withValidResponse(ResponseDefinitionBuilder builder) {
        // @formatter:off
        return builder.withHeader("Content-Type", "application/json")
                      .withBody("[{\"publicId\": \"23\", \"data\":\"123\"}]");
        // @formatter:on
    }
}
