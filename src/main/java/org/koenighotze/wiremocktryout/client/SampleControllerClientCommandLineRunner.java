package org.koenighotze.wiremocktryout.client;

import static java.lang.System.out;

import javax.inject.Inject;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Basic client that uses the REST controller.
 * This class is used for trying out the Wiremock features.
 *
 * @author David Schmitz
 */
@Component
public class SampleControllerClientCommandLineRunner implements CommandLineRunner {
    private final SampleControllerClient sampleControllerClient;

    @Inject
    public SampleControllerClientCommandLineRunner(SampleControllerClient sampleControllerClient) {
        this.sampleControllerClient = sampleControllerClient;
    }

    /**
     * Fetches all {@code Sample}s and prints them to stdout.
     *
     * @param args the args
     */
    @Override
    public void run(String... args) {
        sampleControllerClient.fetchAllSamples().forEach(sample -> {
            out.println("Found sample: " + sample);
        });
    }
}
