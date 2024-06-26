package com.demo.booking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EventRequest {

  @NotNull(message = "name is required")
  @Schema(type = "string", example = "java jaz")
  private String name;

  @Schema(type = "integer", example = "100")
  private Integer seat;

  @NotNull(message = "start_time is required")
  @JsonProperty("start_time")
  @Schema(type = "string", example = "00:00:00")
  private String startTime;

  @NotNull(message = "end_time is required")
  @JsonProperty("end_time")
  @Schema(type = "string", example = "10:00:00")
  private String endTime;
}
