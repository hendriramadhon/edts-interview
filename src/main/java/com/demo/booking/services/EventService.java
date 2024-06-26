package com.demo.booking.services;

import com.demo.booking.dto.EventBookingRequest;
import com.demo.booking.dto.EventBookingResponse;
import com.demo.booking.dto.EventRequest;
import com.demo.booking.dto.EventResponse;
import com.demo.booking.entities.Event;
import com.demo.booking.entities.EventBooking;
import com.demo.booking.repositories.EventBookingRepository;
import com.demo.booking.repositories.EventRepository;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class EventService {

  private final EventRepository eventRepository;

  private final EventBookingRepository eventBookingRepository;

  public EventResponse addEvent(EventRequest request) {
    Event event = eventRepository.save(
      Event
        .builder()
        .name(request.getName())
        .seat(request.getSeat())
        .startTime(LocalTime.parse(request.getStartTime(), DateTimeFormatter.ofPattern("HH:mm:ss")))
        .endTime(LocalTime.parse(request.getEndTime(), DateTimeFormatter.ofPattern("HH:mm:ss")))
        .build()
    );
    return EventResponse
      .builder()
      .id(event.getId())
      .name(event.getName())
      .seat(event.getSeat())
      .startTime(event.getStartTime())
      .endTime(event.getEndTime())
      .status("OK")
      .build();
  }

  public Page<EventResponse> listEvent(Pageable pagingRequest) {
    Page<Event> eventPages = eventRepository.findAll(pagingRequest);
    List<EventResponse> result = eventPages
      .getContent()
      .stream()
      .map(e ->
        EventResponse
          .builder()
          .id(e.getId())
          .name(e.getName())
          .seat(e.getSeat())
          .startTime(e.getStartTime())
          .endTime(e.getEndTime())
          .build()
      )
      .collect(Collectors.toList());
    return new PageImpl<>(result, pagingRequest, eventPages.getTotalElements());
  }

  @Transactional
  @RateLimiter(name = "booking-service", fallbackMethod = "fallbackMethod")
  public EventBookingResponse addEventBooking(EventBookingRequest request) {
    Optional<Event> opEvent = eventRepository.findById(request.getEventId());
    if (opEvent.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Event not found");
    }
    LocalDateTime now = LocalDateTime.now();
    if (
      opEvent.get().getSeat() == 0 ||
      now.toLocalTime().isBefore(opEvent.get().getStartTime()) ||
      now.toLocalTime().isAfter(opEvent.get().getEndTime())
    ) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Event not available");
    }
    EventBooking booked = eventBookingRepository.save(
      EventBooking.builder().eventId(request.getEventId()).bookedBy(request.getBookedBy()).build()
    );

    opEvent.get().setSeat(opEvent.get().getSeat() - 1);

    return EventBookingResponse.builder().eventBookingId(booked.getId()).message("OK").build();
  }

  @SuppressWarnings("unused")
  private EventBookingResponse fallbackMethod(RequestNotPermitted requestNotPermitted) {
    return EventBookingResponse.builder().message("service is busy. Please try again later.").build();
  }
}
