package org.koenighotze.wiremocktryout;

import static java.lang.System.out;
import static java.util.Collections.emptyList;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.OK;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.EmbeddedServletContainerAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.devtools.autoconfigure.LocalDevToolsAutoConfiguration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javaslang.control.Match;
import javaslang.control.Try;

/**
 * @author David Schmitz
 */
@SpringBootApplication
@EnableAutoConfiguration(exclude = {DispatcherServletAutoConfiguration.class, EmbeddedServletContainerAutoConfiguration.class,
                                    ErrorMvcAutoConfiguration.class, WebMvcAutoConfiguration.class, JmxAutoConfiguration.class,
                                    LocalDevToolsAutoConfiguration.class})
public class SampleControllerClient implements CommandLineRunner {
    public static void main(String[] args) {
        new SpringApplicationBuilder(SampleControllerClient.class).web(false).run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        fetchAllSamples().forEach(sample -> {
            out.println("Found sample: " + sample);
        });
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
            new RestTemplate().exchange("http://localhost:8080/sample/", GET, null, new ParameterizedTypeReference<List<Sample>>() {});
        return Match.of(exchange.getStatusCode())
                    .whenIs(OK).then(exchange.getBody())
                    .getOrElse(emptyList());
        // @formatter:on
    }
}
