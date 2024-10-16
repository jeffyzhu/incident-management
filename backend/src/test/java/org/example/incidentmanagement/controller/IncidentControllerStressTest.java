package org.example.incidentmanagement.controller;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.FeederBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class IncidentControllerStressTest extends Simulation {

    private final int port = 8080;
    private final int MAX_RESPONSE_TIME = 1000; // Max acceptable response time in milliseconds
    private final String USERNAME = "admin";
    private final String PASSWORD = "adminpassword";

    // Feeder to supply dynamic data for each request
    FeederBuilder.Batchable<String> feeder = csv("data/incidents.csv").circular();

    ChainBuilder addIncident =
            feed(feeder)
                    .exec(
                            http("Add Incident")
                                    .post("/incidents")
                                    .basicAuth(USERNAME, PASSWORD)
                                    .body(StringBody("{\"title\": \"Incident #{title}\", \"description\": \"Description #{description}\", \"status\": \"Open\"}")).asJson()
                                    .check(status().is(201))
                                    .check(responseTimeInMillis().lte(MAX_RESPONSE_TIME))
                    );

    ChainBuilder getIncidents =
            exec(
                    http("Get Incidents")
                            .get("/incidents")
                            .basicAuth(USERNAME, PASSWORD)
                            .check(status().is(200))
                            .check(responseTimeInMillis().lte(MAX_RESPONSE_TIME))
            );

    ChainBuilder updateIncident =
            exec(
                    http("Update Incident")
                            .put("/incidents/2") // Assuming we're updating incident with ID 2
                            .basicAuth(USERNAME, PASSWORD)
                            .body(StringBody("{\"title\": \"Updated Incident\", \"description\": \"Updated Description\", \"status\": \"Closed\"}")).asJson()
                            .check(status().is(204))
                            .check(responseTimeInMillis().lte(MAX_RESPONSE_TIME))
            );

    ChainBuilder deleteIncident =
            exec(
                    http("Delete Incident")
                            .delete("/incidents/1") // Assuming we're deleting incident with ID 1
                            .basicAuth(USERNAME, PASSWORD)
                            .check(status().is(204))
                            .check(responseTimeInMillis().lte(MAX_RESPONSE_TIME))
            );

    HttpProtocolBuilder httpProtocol =
            http.baseUrl("http://localhost:" + port)
                    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                    .acceptLanguageHeader("en-US,en;q=0.5")
                    .acceptEncodingHeader("gzip, deflate")
                    .userAgentHeader(
                            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:109.0) Gecko/20100101 Firefox/119.0"
                    );

    {
        ScenarioBuilder scn = scenario("Incident Controller Stress Test")
                .exec(addIncident)
                .pause(1)
                .exec(getIncidents)
                .pause(1)
                .exec(updateIncident)
                .pause(1)
                .exec(deleteIncident);

        setUp(
                scn.injectOpen(
                        atOnceUsers(1), // Start with one user
                        rampUsers(10).during(20) // Ramp up to ten users over 20 seconds
                )
        ).protocols(httpProtocol);
    }
}