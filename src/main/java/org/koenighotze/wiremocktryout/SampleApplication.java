package org.koenighotze.wiremocktryout;

import static org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;

/**
 * @author David Schmitz
 */
@SpringBootApplication
@ComponentScan(excludeFilters = @Filter(type = ASSIGNABLE_TYPE, classes = SampleControllerClient.class))
public class SampleApplication {
    public static void main(String[] args) {
        new SpringApplication(SampleApplication.class).run(args);
    }
}
