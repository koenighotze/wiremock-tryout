package org.koenighotze.wiremocktryout.service;

import static java.util.Optional.ofNullable;
import static java.util.UUID.randomUUID;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toConcurrentMap;
import static java.util.stream.IntStream.range;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.koenighotze.wiremocktryout.domain.Sample;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Simple REST controller that exposes some services that we'll mock and stub
 * and test using Wiremock.
 *
 * @author David Schmitz
 */
@RestController
@RequestMapping(path = "/sample", produces = APPLICATION_JSON_UTF8_VALUE)
public class SampleController {
    private static final Map<String, Sample> RANDOM_SAMPLES =
        // @formatter:off
        range(1, 11).mapToObj(i -> Sample.of(randomUUID().toString()))
                    .collect(toConcurrentMap(Sample::getPublicId, identity()));
        // @formatter:on

    static {
        // add one known item
        RANDOM_SAMPLES.putIfAbsent("1", Sample.of("foo"));
    }

    @RequestMapping(method = GET, path = "/{id}")
    public ResponseEntity<Sample> fetchOneSample(@PathVariable("id") String id) {
        // @formatter:off
        return ofNullable(RANDOM_SAMPLES.get(id))
                .flatMap(sample -> Optional.of(new ResponseEntity<>(sample, OK)))
                .orElse(new ResponseEntity<>(NOT_FOUND));
        // @formatter:on
    }

    @RequestMapping(method = GET)
    public List<Sample> fetchAllSamples() {
        return new ArrayList<>(RANDOM_SAMPLES.values());
    }
}
