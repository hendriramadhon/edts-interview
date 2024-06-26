package com.demo.booking.services;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.demo.booking.dto.EventRequest;
import com.demo.booking.entities.Event;
import com.demo.booking.repositories.EventBookingRepository;
import com.demo.booking.repositories.EventRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class EventServiceTest {

  @InjectMocks
  private EventService eventService;

  @Mock
  private EventRepository eventRepository;

  @Mock
  private EventBookingRepository eventBookingRepository;

  @Test
  void test_addEvent_success() {
    EventRequest request = EventRequest.builder().startTime("10:00:00").endTime("12:00:00").build();
    Event result = Event.builder().id(0l).build();
    when(eventRepository.save(any())).thenReturn(result);
    eventService.addEvent(request);
    verify(eventRepository, times(1)).save(any());
  }

  @Test
  void test_listEvent_success() {
    Pageable pagingRequest = Pageable.ofSize(10);
    Event event = Event.builder().id(0l).build();
    when(eventRepository.findAll(pagingRequest)).thenReturn(new PageImpl<>(List.of(event)));
    var expected = eventService.listEvent(pagingRequest);
    assertTrue(!expected.getContent().isEmpty());
  }
}
