package com.aditya.Movie_Ticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aditya.Movie_Ticket.dto.Screen;

public interface ScreenRepository extends JpaRepository<Screen, Integer>{

	boolean existsByName(String name);
}
