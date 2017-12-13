package org.koenighotze.wiremocktryout.client

import com.github.tomakehurst.wiremock.junit.WireMockRule
import org.junit.Rule
import org.koenighotze.wiremocktryout.domain.Sample
import org.koenighotze.wiremocktryout.service.SampleApplication
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import spock.lang.Specification
import spock.lang.Timeout

import javax.inject.Inject
import java.util.concurrent.TimeUnit

import static com.github.tomakehurst.wiremock.client.WireMock.*
import static org.koenighotze.wiremocktryout.RequestUtils.withValidResponse
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE

/**
 * @author David Schmitz
 */


@Timeout(value = 3, unit = TimeUnit.SECONDS)
@SpringBootTest(webEnvironment = WebEnvironment.NONE, classes = [ SampleControllerClientApplication.class, SampleApplication.class ])
class SampleControllerClientStubbingSpec extends Specification {
    @Inject
    SampleControllerClient sampleControllerClient

    @Rule
    WireMockRule wireMockRule = new WireMockRule(9090)

    def "the stub returns an empty list"() {
        given: "A server that returns an empty JSON array"
        givenThat(get(urlEqualTo("/sample/"))
                .willReturn(
                aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("[]")))

        when: "we fetch all samples"
        def result = sampleControllerClient.fetchAllSamples()

        then: "we expect an empty list"
        result == []
    }


    def "the stub returns samples"() {
        given:
        givenThat(get(urlEqualTo("/sample/"))
                .willReturn(
                withValidResponse(aResponse())))

        when:
        def result = sampleControllerClient.fetchAllSamples()

        then:
        result != []
        result[0] == Sample.of("123").setPublicId("23")
    }

    def "503 results in an empty list"() {
        given:
        givenThat(get(urlEqualTo("/sample/"))
                .willReturn(
                aResponse()
                        .withStatus(SERVICE_UNAVAILABLE.value())))

        when:
        def result = sampleControllerClient.fetchAllSamples()

        then:
        result == []
    }

    def "204 results in an empty list"() {
        given:
        givenThat(get(urlEqualTo("/sample/"))
                .willReturn(
                aResponse()
                        .withStatus(NO_CONTENT.value())))

        when:
        def result = sampleControllerClient.fetchAllSamples()

        then:
        result == []
    }
}
