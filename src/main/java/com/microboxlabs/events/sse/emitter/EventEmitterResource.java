package com.microboxlabs.events.sse.emitter;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.subscription.MultiEmitter;
import io.smallrye.reactive.messaging.MutinyEmitter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.sse.Sse;
import jakarta.ws.rs.sse.SseBroadcaster;
import jakarta.ws.rs.sse.SseEventSink;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.jboss.resteasy.reactive.RestStreamElementType;

@ApplicationScoped
@Path("/events")
public class EventEmitterResource {

    @Inject
    @Channel("events")
    Multi<EventData> events;

    @Channel("events")
    MutinyEmitter<EventData> eventEmitter;

    @POST
    @Path("/emit")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation( //
            summary = "Emit a new event", //
            description = "Post new events for SSE broadcasting to clients. This endpoint allows sending a single "
                    + "event that will be broadcast to all connected SSE clients." //
    )
    @APIResponses(value = {
            @APIResponse(responseCode = "202", description = "Event accepted for broadcasting"),
            @APIResponse(responseCode = "400", description = "Invalid event data")
    })
    public void emitEvent(
            @RequestBody(description = "The event data to be broadcast", required = true) EventData eventData) {
        eventEmitter.sendAndForget(eventData);
    }

    @GET
    @Path("/stream")
    @RestStreamElementType(MediaType.APPLICATION_JSON)
    public Multi<EventData> streamEvents() {
        return events;
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
