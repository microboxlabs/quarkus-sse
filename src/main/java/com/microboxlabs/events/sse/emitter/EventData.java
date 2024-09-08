package com.microboxlabs.events.sse.emitter;

import java.time.Instant;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Represents the data structure of an event to be emitted")
public class EventData {

    @Schema(description = "Unique identifier for the event", example = "evt_123456")
    private String id;

    @NotBlank
    @Schema(description = "The type of the event", example = "user.created", required = true)
    private String eventType;

    @NotNull
    @Schema(description = "The payload of the event", required = true)
    private Object payload;

    @Schema(description = "Timestamp when the event occurred", example = "2023-04-15T10:30:00Z")
    private Instant timestamp;

    @Schema(description = "Optional metadata associated with the event")
    private Object metadata;

    // Constructors
    public EventData() {
    }

    public EventData(String eventType, Object payload) {
        this.eventType = eventType;
        this.payload = payload;
        this.timestamp = Instant.now();
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public Object getMetadata() {
        return metadata;
    }

    public void setMetadata(Object metadata) {
        this.metadata = metadata;
    }

    @Override
    public String toString() {
        return "EventData{" +
                "id='" + id + '\'' +
                ", eventType='" + eventType + '\'' +
                ", payload=" + payload +
                ", timestamp=" + timestamp +
                ", metadata=" + metadata +
                '}';
    }

}