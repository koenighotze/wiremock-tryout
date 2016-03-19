package org.koenighotze.wiremocktryout.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author David Schmitz
 */
@SpringBootApplication(scanBasePackageClasses = ServicePackage.class)
public class SampleApplication {
    public static void main(String[] args) {
        new SpringApplication(SampleApplication.class).run(args);
    }
}
