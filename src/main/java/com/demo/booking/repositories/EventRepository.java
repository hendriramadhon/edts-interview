package com.demo.booking.repositories;

import com.demo.booking.entities.Event;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, String> {
  @Lock(LockModeType.PESSIMISTIC_WRITE)
  public Optional<Event> findById(Long id);
}
