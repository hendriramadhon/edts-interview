package com.demo.booking.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalTime;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EventResponse {

  private Long id;
  private String name;
  private Integer seat;

  @JsonInclude(Include.NON_NULL)
  private String status;

  private LocalTime startTime;
  private LocalTime endTime;
}
