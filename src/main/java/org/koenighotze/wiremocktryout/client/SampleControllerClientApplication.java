package org.koenighotze.wiremocktryout.client;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.EmbeddedServletContainerAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.devtools.autoconfigure.LocalDevToolsAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Basic client that uses the REST controller.
 * This class is used for trying out the Wiremock features.
 *
 * @author David Schmitz
 */
@SpringBootApplication(scanBasePackageClasses = ClientPackage.class)
@EnableAutoConfiguration(exclude = {DispatcherServletAutoConfiguration.class, EmbeddedServletContainerAutoConfiguration.class,
                                    ErrorMvcAutoConfiguration.class, JmxAutoConfiguration.class, LocalDevToolsAutoConfiguration.class})
public class SampleControllerClientApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(SampleControllerClientApplication.class).web(false).run(args);
    }

    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory clientHttpRequestFactory) {
        return new RestTemplate(clientHttpRequestFactory);
    }

    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setReadTimeout(2000);
        //        factory.setConnectTimeout(2000);
        return factory;
    }
}
