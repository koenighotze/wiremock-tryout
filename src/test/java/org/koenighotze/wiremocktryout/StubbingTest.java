package org.koenighotze.wiremocktryout;

import org.junit.Rule;

import com.github.tomakehurst.wiremock.junit.WireMockRule;

/**
 * @author David Schmitz
 */
public class StubbingTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8081);
}
