package org.koenighotze.wiremocktryout;

import static java.util.Optional.ofNullable;
import static java.util.UUID.randomUUID;
import static java.util.stream.Collectors.toConcurrentMap;
import static java.util.stream.IntStream.range;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author David Schmitz
 */
@RestController
@RequestMapping(path = "/sample")
public class SampleController {
    private static final Map<String, String> RANDOM_SAMPLES =
        // @formatter:off
        range(1, 10).mapToObj(i -> new SimpleImmutableEntry<>(i + "", randomUUID().toString()))
                    .collect(toConcurrentMap(SimpleImmutableEntry::getKey, SimpleImmutableEntry::getValue));
        // @formatter:on

    
    public ResponseEntity<String> fetchOneSample(String id) {
        // @formatter:off
        return ofNullable(RANDOM_SAMPLES.get(id))
                .flatMap(sample -> Optional.of(new ResponseEntity<>(sample, OK)))
                .orElse(new ResponseEntity<>(NOT_FOUND));
        // @formatter:on
    }
}
