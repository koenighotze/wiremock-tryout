package org.koenighotze.wiremocktryout;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.koenighotze.wiremocktryout.service.SampleApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import com.jayway.jsonpath.JsonPath;

/**
 * Spring integration test for checking the behaviour of the controller.
 *
 * @author David Schmitz
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SampleApplication.class)
@WebAppConfiguration
public class SampleControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void a_known_sample_can_be_get() throws Exception {
        ResultActions perform = mockMvc.perform(get("/sample/1"));

        // @formatter:off
        perform.andExpect(status().isOk())
               .andExpect(content().contentType(APPLICATION_JSON_UTF8));
        // @formatter:on
    }

    @Test
    public void the_list_of_samples_can_be_fetched_via_get() throws Exception {
        ResultActions perform = mockMvc.perform(get("/sample/"));

        System.out.println(perform.andReturn().getResponse().getContentAsString());
        Object read = JsonPath.parse(perform.andReturn().getResponse().getContentAsString()).read("$.*");
        // @formatter:off
        perform.andExpect(status().isOk())
               .andExpect(content().contentType(APPLICATION_JSON_UTF8))
               .andExpect(jsonPath("$", hasSize(11)));
        // @formatter:on

    }

}