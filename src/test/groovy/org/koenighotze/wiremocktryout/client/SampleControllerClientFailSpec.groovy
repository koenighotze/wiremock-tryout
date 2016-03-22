package org.koenighotze.wiremocktryout.client

import com.github.tomakehurst.wiremock.junit.WireMockRule
import org.junit.Rule
import org.springframework.boot.test.SpringApplicationConfiguration
import spock.lang.Specification
import spock.lang.Timeout

import javax.inject.Inject

import static com.github.tomakehurst.wiremock.client.WireMock.*
import static com.github.tomakehurst.wiremock.http.Fault.MALFORMED_RESPONSE_CHUNK
import static java.util.concurrent.TimeUnit.SECONDS
import static org.koenighotze.wiremocktryout.RequestUtils.withValidResponse

/**
 * @author David Schmitz
 */

@Timeout(value = 3, unit = SECONDS)
@SpringApplicationConfiguration(classes = SampleControllerClientApplication.class)
class SampleControllerClientFailSpec extends Specification {
    @Inject
    SampleControllerClient sampleControllerClient

    @Rule
    WireMockRule wireMockRule = new WireMockRule(8080)

    def "the wait time for responses is max 2 seconds"() {
        given: "A server that answers after 4 seconds"
        givenThat(get(urlEqualTo("/sample/"))
                .willReturn(
                withValidResponse(aResponse())
                        .withFixedDelay(4000)))

        when: "I request all samples"
        def response = sampleControllerClient.fetchAllSamples()

        then: "I receive an empty list"
        response == []
    }

    def "a conntect timeout must fail fast"() {
        given: "A server that takes to long to connect"
        addRequestProcessingDelay(2000)
        givenThat(get(urlEqualTo("/sample/"))
                .willReturn(withValidResponse(aResponse())))

        when: "I request all samples"
        def response = sampleControllerClient.fetchAllSamples()

        then: "I receive an empty list"
        response == []
    }


    def "bad responses result in empty lists"() {
        given: "A server that serves a bad response"
        givenThat(get(urlEqualTo("/sample/"))
                .willReturn(aResponse().withFault(MALFORMED_RESPONSE_CHUNK)))

        when: "I request all samples"
        def response = sampleControllerClient.fetchAllSamples()

        then: "I receive an empty list"
        response == []
    }
}
