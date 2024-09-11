package com.microboxlabs.events.sse.emitter;

import io.smallrye.mutiny.Multi;
import io.smallrye.reactive.messaging.MutinyEmitter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.jboss.resteasy.reactive.RestStreamElementType;

@ApplicationScoped
@Path("/events")
public class EventStreamResource {
    private static final Logger logger = Logger.getLogger(EventStreamResource.class.getName());

    @Inject
    @Channel("events")
    Multi<EventData> events;

    @Channel("events")
    MutinyEmitter<EventData> eventEmitter;

    Map<String, MutinyEmitter<EventData>> tenantEventEmitters = new ConcurrentHashMap<>();

    @POST
    @Path("/emit")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Emit a new event", //
            description = "Post new events for SSE broadcasting to clients. This endpoint allows sending a single "
                    + "event that will be broadcast to all connected SSE clients." //
    )
    @APIResponses(value = {
            @APIResponse(responseCode = "202", description = "Event accepted for broadcasting"),
            @APIResponse(responseCode = "400", description = "Invalid event data")
    })
    public Response emitEvent(
            @RequestBody(description = "The event data to be broadcast", required = true) EventData eventData) {
        if (eventEmitter.hasRequests()) {
            eventEmitter.sendAndForget(eventData);
        } else {
            logger.warning("No subscribers for event: " + eventData);
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.accepted().build();
    }

    @GET
    @Path("/stream")
    @Operation(summary = "Stream all events", description = "Stream all events that have been emitted to the server.")
    @RestStreamElementType(MediaType.APPLICATION_JSON)
    public Multi<EventData> streamEvents() {
        return events;
    }

    @GET
    @Path("/stream/{eventType}")
    @Operation(summary = "Stream events by type", description = "Stream events that match a specific event type.")
    @RestStreamElementType(MediaType.APPLICATION_JSON)
    public Multi<EventData> streamEventByType(@PathParam("eventType") String eventType) {
        return events.filter(event -> event.getEventType().equals(eventType));
    }

    @GET
    @Path("/tenant/{tenantId}/stream")
    @Operation(summary = "Stream events for a specific tenant", description = "Stream events for a specific tenant.")
    public Multi<EventData> streamEventsForTenant(@PathParam("tenantId") String tenantId) {
        return events.filter(event -> event.getTenantId().equals(tenantId));
    }

    @GET
    @Path("/tenant/{tenantId}/stream/{eventType}")
    @Operation(summary = "Stream events for a specific tenant and type", //
            description = "Stream events for a specific tenant and type.")
    public Multi<EventData> streamEventsForTenant( //
            @PathParam("tenantId") String tenantId,
            @PathParam("eventType") String eventType) {
        return events.filter(event -> event.getTenantId().equals(tenantId) && event.getEventType().equals(eventType));
    }
}
