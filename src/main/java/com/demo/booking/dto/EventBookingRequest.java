package com.demo.booking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EventBookingRequest {

  @NotNull(message = "event_id is required")
  @Schema(type = "long", example = "1")
  @JsonProperty("event_id")
  private Long eventId;

  @NotNull(message = "booked_by is required")
  @Schema(type = "string", example = "budi")
  @JsonProperty("booked_by")
  private String bookedBy;
}
