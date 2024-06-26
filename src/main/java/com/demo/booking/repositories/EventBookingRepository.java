package com.demo.booking.repositories;

import com.demo.booking.entities.EventBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventBookingRepository extends JpaRepository<EventBooking, String> {}
