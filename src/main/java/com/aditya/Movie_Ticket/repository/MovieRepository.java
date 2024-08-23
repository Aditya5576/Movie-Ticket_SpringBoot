package com.aditya.Movie_Ticket.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aditya.Movie_Ticket.dto.Movie;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
	
	List<Movie> findByReleaseDate(LocalDate movieDate);
}