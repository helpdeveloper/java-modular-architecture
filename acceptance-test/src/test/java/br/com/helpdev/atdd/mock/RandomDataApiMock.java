package br.com.helpdev.atdd.mock;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;

import com.github.tomakehurst.wiremock.WireMockServer;

public class RandomDataApiMock {

  public static void mockRandomIdNumber(final WireMockServer mockServer) {
    mockServer.stubFor(get(urlPathMatching("/api/id_number/random_id_number"))
        .willReturn(aResponse()
            .withBodyFile("randomIdNumberResponse.json")
            .withStatus(200)
            .withHeader("Content-Type", "application/json")));
  }

}
