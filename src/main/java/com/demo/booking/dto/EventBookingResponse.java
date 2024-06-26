package com.demo.booking.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EventBookingResponse {

  private Long eventBookingId;
  private String message;
}
