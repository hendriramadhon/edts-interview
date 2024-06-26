package com.demo.booking.controllers;

import com.demo.booking.dto.EventBookingRequest;
import com.demo.booking.dto.EventBookingResponse;
import com.demo.booking.dto.EventRequest;
import com.demo.booking.dto.EventResponse;
import com.demo.booking.services.EventService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/event")
@RequiredArgsConstructor
public class EventController {

  private final EventService eventService;

  @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Add event")
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<EventResponse> addEvent(@Valid @RequestBody EventRequest request) {
    return ResponseEntity.ok(eventService.addEvent(request));
  }

  @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "List event")
  @ResponseStatus(HttpStatus.CREATED)
  public Page<EventResponse> listEvent(
    @Valid @RequestParam(name = "page", required = false, defaultValue = "0") int page,
    @Valid @RequestParam(name = "limit", required = false, defaultValue = "10") int limit
  ) {
    return eventService.listEvent(PageRequest.of(page, limit));
  }

  @PostMapping(value = "/booking", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Add event booking")
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<EventBookingResponse> addEventBooking(@Valid @RequestBody EventBookingRequest request) {
    return ResponseEntity.ok(eventService.addEventBooking(request));
  }
}
