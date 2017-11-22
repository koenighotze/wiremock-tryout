package org.koenighotze.wiremocktryout.client

import org.koenighotze.wiremocktryout.service.SampleApplication
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

/**
 * @author David Schmitz
 */
@SpringBootTest(classes = SampleControllerClientApplication.class)
class SampleControllerClientSpec extends Specification {

    @Autowired
    SampleControllerClient sampleControllerClient

    def "The Samples can be fetched using REST"() {
        setup: "a running application"
        def configurableApplicationContext = new SpringApplication(SampleApplication.class).run();

        when: "we fetch the samples"
        def result = sampleControllerClient.fetchAllSamples()

        then: "we receive a non empty list"
        result != null
        result.size() > 0

        cleanup:
        configurableApplicationContext.close();
    }
}
