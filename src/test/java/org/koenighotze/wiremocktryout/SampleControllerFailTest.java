package org.koenighotze.wiremocktryout;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.addRequestProcessingDelay;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.givenThat;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.Assert.assertThat;
import static org.koenighotze.wiremocktryout.RequestUtils.withValidResponse;

import javax.inject.Inject;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;
import org.koenighotze.wiremocktryout.client.SampleControllerClient;
import org.koenighotze.wiremocktryout.client.SampleControllerClientApplication;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.tomakehurst.wiremock.http.Fault;
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
                withValidResponse(aResponse())
                    .withFixedDelay(4000)));
        // @formatter:on
        assertThat(sampleControllerClient.fetchAllSamples(), is(empty()));
    }

    @Test
    public void connect_timeouts_must_fail_fast() {
        // @formatter:off
        addRequestProcessingDelay(2000);
        givenThat(get(urlEqualTo("/sample/"))
            .willReturn(withValidResponse(aResponse())));
        // @formatter:on
        assertThat(sampleControllerClient.fetchAllSamples(), is(empty()));
    }

    @Test
    public void bad_response() {
        // @formatter:off
        givenThat(get(urlEqualTo("/sample/"))
            .willReturn(aResponse().withFault(Fault.MALFORMED_RESPONSE_CHUNK)));
        // @formatter:on
        assertThat(sampleControllerClient.fetchAllSamples(), is(empty()));

    }

}
