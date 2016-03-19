package org.koenighotze.wiremocktryout;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.givenThat;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.Assert.assertThat;

import javax.inject.Inject;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;
import org.koenighotze.wiremocktryout.client.SampleControllerClient;
import org.koenighotze.wiremocktryout.client.SampleControllerClientApplication;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.tomakehurst.wiremock.junit.WireMockRule;

/**
 * Test that demonstrates the features for testing Failures.
 *
 * @author David Schmitz
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SampleControllerClientApplication.class)
public class SampleControllerFailTest {
    @Inject
    private SampleControllerClient sampleControllerClient;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8080);

    @Rule
    public Timeout timeout = new Timeout(3, SECONDS);

    /**
     * Test that we wait max 2 secs for a Server response.
     */
    @Test
    public void server_side_timeout() {
        // @formatter:off
        givenThat(get(urlEqualTo("/sample/"))
            .willReturn(
                aResponse()
                    .withFixedDelay(4000)));
        // @formatter:on
        assertThat(sampleControllerClient.fetchAllSamples(), is(empty()));
    }

    @Test
    public void socket_timeout() {

    }

    @Test
    public void bad_response() {
    }

}
