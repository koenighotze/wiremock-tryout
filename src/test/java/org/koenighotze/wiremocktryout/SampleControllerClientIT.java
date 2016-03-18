package org.koenighotze.wiremocktryout;

import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author David Schmitz
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SampleApplication.class)
public class SampleControllerClientIT {
    @Autowired
    private SampleControllerClient sampleControllerClient;

    @Test
    public void fetch_all_samples() {
        assertThat(sampleControllerClient.fetchAllSamples(), not(empty()));
    }

}