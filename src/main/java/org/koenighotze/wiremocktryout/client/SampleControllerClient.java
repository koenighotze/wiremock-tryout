package org.koenighotze.wiremocktryout.client;

import static java.util.Collections.emptyList;
import static javaslang.API.$;
import static javaslang.API.Case;
import static javaslang.API.Match;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.OK;

import java.util.*;
import javax.inject.*;

import javaslang.control.*;
import org.koenighotze.wiremocktryout.domain.*;
import org.slf4j.*;
import org.springframework.core.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;

/**
 * Basic client that uses the REST controller.
 * This class is used for trying out the Wiremock features.
 *
 * @author David Schmitz
 */
@Component
public class SampleControllerClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(SampleControllerClient.class);

    private final RestTemplate restTemplate;

    @Inject
    public SampleControllerClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Sample> fetchAllSamples() {
        // @formatter:off
        return Try.of(this::getSamples)
            .recover(t -> {
                LOGGER.warn("Cannot handle response!", t);
                return emptyList();
            })
            .get();
        // @formatter:on
    }

    private List<Sample> getSamplesClassic() {
        // @formatter:off
        ResponseEntity<List<Sample>> exchange =
            restTemplate.exchange("http://localhost:8080/sample/", GET, null, new ParameterizedTypeReference<List<Sample>>() {});

        if (OK.equals(exchange.getStatusCode())) {
            return exchange.getBody();
        }
        else {
            return emptyList();
        }

    }

    private List<Sample> getSamples() {
        // @formatter:off
        ResponseEntity<List<Sample>> exchange =
            restTemplate.exchange("http://localhost:8080/sample/", GET, null, new ParameterizedTypeReference<List<Sample>>() {});

        return Match(exchange.getStatusCode())
                .of(
                    Case($(OK), exchange.getBody()),
                    Case($(), Collections.<Sample>emptyList())
                );
        // @formatter:on
    }
}
