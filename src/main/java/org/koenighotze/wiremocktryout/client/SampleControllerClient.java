package org.koenighotze.wiremocktryout.client;

import static java.util.Collections.emptyList;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.OK;

import java.util.List;

import javax.inject.Inject;

import org.koenighotze.wiremocktryout.domain.Sample;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javaslang.control.Match;
import javaslang.control.Try;

/**
 * Basic client that uses the REST controller.
 * This class is used for trying out the Wiremock features.
 *
 * @author David Schmitz
 */
@Component
public class SampleControllerClient {
    private final RestTemplate restTemplate;

    @Inject
    public SampleControllerClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Sample> fetchAllSamples() {
        // @formatter:off
        return Try.of(this::getSamples)
            .recover(t -> emptyList())
            .get();
        // @formatter:on
    }

    private List<Sample> getSamples() {
        // @formatter:off
        ResponseEntity<List<Sample>> exchange =
            restTemplate.exchange("http://localhost:8080/sample/", GET, null, new ParameterizedTypeReference<List<Sample>>() {});
        return Match.of(exchange.getStatusCode())
                    .whenIs(OK).then(exchange.getBody())
                    .getOrElse(emptyList());
        // @formatter:on
    }
}
