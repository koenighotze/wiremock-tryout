package org.koenighotze.wiremocktryout.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

import static org.hamcrest.collection.IsCollectionWithSize.hasSize
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup

/**
 * @author David Schmitz
 */
@SpringBootTest(classes = SampleApplication.class)
@WebAppConfiguration
@Rollback
class SampleControllerSpec extends Specification {
    @Autowired
    WebApplicationContext webApplicationContext
    MockMvc mockMvc

    def setup() {
        mockMvc = webAppContextSetup(webApplicationContext).build()
    }

    def "a known sample can be GET"() {
        given: "A sample with an id"

        when: "we try to fetch a sample with that id"
        def perform = mockMvc.perform(get("/sample/1"))

        then: "we expect a successful JSON response"
        perform.andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
    }

    def "The list of samples can be fetched via GET"() {
        given: "A GET request to the controller URL"
        def perform = mockMvc.perform(get("/sample/"))

        when: "We read the returned JSON data"

        then: "We expect a list of 11 elements"
        perform.andExpect(status().isOk())
               .andExpect(content().contentType(APPLICATION_JSON_UTF8))
               .andExpect(jsonPath('$', hasSize(11)))
    }
}