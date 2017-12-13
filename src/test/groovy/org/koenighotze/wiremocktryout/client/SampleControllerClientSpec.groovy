package org.koenighotze.wiremocktryout.client

import org.koenighotze.wiremocktryout.service.SampleApplication
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT

@SpringBootTest(webEnvironment = DEFINED_PORT, classes = [SampleControllerClientApplication.class, SampleApplication.class])
@DirtiesContext
class SampleControllerClientSpec extends Specification {

    @Autowired
    SampleControllerClient sampleControllerClient

    def "The Samples can be fetched using REST"() {
        setup: "a running application"

        when: "we fetch the samples"
        def result = sampleControllerClient.fetchAllSamples()

        then: "we receive a non empty list"
        result != null
        result.size() > 0
    }
}
