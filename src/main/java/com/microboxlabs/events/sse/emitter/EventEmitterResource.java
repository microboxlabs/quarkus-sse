package com.microboxlabs.events.sse.emitter;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

@Path("/api/v1/events")
@Produces(MediaType.SERVER_SENT_EVENTS)
@Consumes(MediaType.APPLICATION_JSON)
public class EventEmitterResource {

    @POST
    @Path("/emit")
    @Operation(summary = "Emit a new event", description = "Post new events for SSE broadcasting to clients. This endpoint allows sending a single event that will be broadcast to all connected SSE clients.")
    @APIResponses(value = {
            @APIResponse(responseCode = "202", description = "Event accepted for broadcasting"),
            @APIResponse(responseCode = "400", description = "Invalid event data")
    })
    public void emitEvent(
            @RequestBody(description = "The event data to be broadcast", required = true) EventData eventData) {
        // Implementation for emitting a single event
    }

    @GET
    @Path("/stream")
    public void streamEvents() {
        // Implementation for streaming events from a single emitter
    }

    @POST
    @Path("/broadcast")
    public void broadcastEvent() {
        // Implementation for broadcasting events to multiple subscribers
    }

    @GET
    @Path("/broadcast-stream")
    public void streamBroadcastEvents() {
        // Implementation for clients to subscribe to broadcasted events
    }

    @POST
    @Path("/broadcast/event/{eventId}")
    public void broadcastEventById(@PathParam("eventId") String eventId) {
        // Implementation for broadcasting an event by its event ID
    }

    @GET
    @Path("/broadcast/event/{eventId}")
    public void streamEventById(@PathParam("eventId") String eventId) {
        // Implementation for clients to subscribe to a specific event ID
    }

    @POST
    @Path("/broadcast/tenant/{tenantId}")
    public void broadcastEventForTenant(@PathParam("tenantId") String tenantId) {
        // Implementation for broadcasting events in a multi-tenant environment
    }

    @GET
    @Path("/broadcast/tenant/{tenantId}/stream")
    public void streamEventsForTenant(@PathParam("tenantId") String tenantId) {
        // Implementation for clients to subscribe to events for a specific tenant
    }
}
