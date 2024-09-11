package com.microboxlabs;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.smallrye.mutiny.Multi;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.junit.jupiter.api.Test;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.jboss.resteasy.reactive.client.SseEvent;

import static io.restassured.RestAssured.given;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@QuarkusTest
class EventStreamResourceTest {

    interface SseClient {
        @GET
        @Path("/events/stream")
        @Produces(MediaType.SERVER_SENT_EVENTS)
        Multi<SseEvent<String>> getEvents();
    }

    @Test
    void testEmitEvent() {
        // given()
        // .when().post("/events/emit")
        // .then()
        // .statusCode(200);
    }

    @Test
    void testStreamEvents() {
        // given()
        // .when().get("/events/stream")
        // .then()
        // .statusCode(200)
        // .contentType(ContentType.TEXT)
        // .body(Matchers.startsWith("data:"));
    }

    @Test
    void testStreamEventsWithEmission() throws Exception {
        var eventReceived = new AtomicBoolean(false);
        var client = RestClientBuilder.newBuilder()
                .baseUri(new URI("http://localhost:8081/api/v1"))
                .build(SseClient.class);

        Multi<SseEvent<String>> eventStream = client.getEvents();
        eventStream.subscribe().with(
                event -> {
                    System.out.println("Received event: " + event.data());
                    if (event.data()
                            .contains("\"eventType\":\"test\",\"tenantId\":\"testTenant\",\"data\":\"testData\"")) {
                        eventReceived.set(true);
                    }
                },
                throwable -> {
                    System.err.println("Error in SSE stream: " + throwable.getMessage());
                    throwable.printStackTrace();
                },
                () -> System.out.println("SSE stream completed"));

        var executor = Executors.newSingleThreadScheduledExecutor();
        executor.schedule(() -> {
            System.out.println("Emitting event");
            given()
                    .contentType(ContentType.JSON)
                    .body("{\"eventType\":\"test\",\"tenantId\":\"testTenant\",\"data\":\"testData\"}")
                    .when().post("/events/emit")
                    .then()
                    .statusCode(202);
            System.out.println("Event emitted");
            eventReceived.set(true);
        }, 0, TimeUnit.SECONDS);

        await().atMost(10, TimeUnit.SECONDS).pollInterval(1,
                TimeUnit.SECONDS).until(eventReceived::get);
        executor.shutdown();

        assertTrue(eventReceived.get(), "The emitted event was not received in the stream");
    }

    @Test
    void testStreamEventsByType() {
        // given()
        // .when().get("/events/stream/test")
        // .then()
        // .statusCode(200);
    }

    @Test
    void testStreamEventsByTenant() {
        // given()
        // .when().get("/events/tenant/test/stream")
        // .then()
        // .statusCode(200);
    }

    @Test
    void testStreamEventsByTenantAndType() {
        // given()
        // .when().get("/events/tenant/test/stream/test")
        // .then()
        // .statusCode(200);
    }

}