package com.microboxlabs.events.sse.emitter;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/api/v1/events")
@Produces(MediaType.SERVER_SENT_EVENTS)
@Consumes(MediaType.APPLICATION_JSON)
public class EventEmitterResource {

    @POST
    @Path("/emit")
    public void emitEvent() {
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
