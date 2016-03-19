package org.koenighotze.wiremocktryout;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.givenThat;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

import java.util.List;

import javax.inject.Inject;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.koenighotze.wiremocktryout.client.SampleControllerClient;
import org.koenighotze.wiremocktryout.client.SampleControllerClientApplication;
import org.koenighotze.wiremocktryout.domain.Sample;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.tomakehurst.wiremock.junit.WireMockRule;

/**
 * Test that demonstrates the Stubbing-features of Wiremock.
 *
 * @author David Schmitz
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SampleControllerClientApplication.class)
public class SampleControllerStubbingTest {
    @Inject
    private SampleControllerClient sampleControllerClient;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8080);

    @Test
    public void stub_returns_empty_list() {
        // @formatter:off
        givenThat(get(urlEqualTo("/sample/"))
            .willReturn(
                aResponse()
                    .withHeader("Content-Type", "application/json")
                    .withBody("[]")));
        // @formatter:on
        assertThat(sampleControllerClient.fetchAllSamples(), is(empty()));
    }

    @Test
    public void stub_returns_samples() {
        // @formatter:off
        givenThat(get(urlEqualTo("/sample/"))
            .willReturn(
                aResponse()
                    .withHeader("Content-Type", "application/json")
                    .withBody("[{\"publicId\": \"23\", \"data\":\"123\"}]")));
        // @formatter:on

        List<Sample> samples = sampleControllerClient.fetchAllSamples();
        assertThat(samples, not(empty()));
        assertThat(samples.get(0), is(equalTo(Sample.of("123").setPublicId("23"))));
    }

    /**
     * Test 503
     */
    @Test
    public void if_the_server_request_fails_we_return_an_empty_list() {
        // @formatter:off
        givenThat(get(urlEqualTo("/sample/"))
            .willReturn(
                aResponse()
                .withStatus(SERVICE_UNAVAILABLE.value())));
        // @formatter:on
        List<Sample> samples = sampleControllerClient.fetchAllSamples();
        assertThat(samples, is(empty()));
    }

    /**
     * Test 204
     */
    @Test
    public void if_the_server_finds_no_content_we_return_an_empty_list() {
        // @formatter:off
        givenThat(get(urlEqualTo("/sample/"))
            .willReturn(
                aResponse()
                .withStatus(NO_CONTENT.value())));
        // @formatter:on
        List<Sample> samples = sampleControllerClient.fetchAllSamples();
        assertThat(samples, is(empty()));
    }
}
