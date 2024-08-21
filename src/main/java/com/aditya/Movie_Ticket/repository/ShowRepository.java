package com.aditya.Movie_Ticket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aditya.Movie_Ticket.dto.Screen;
import com.aditya.Movie_Ticket.dto.Show;

public interface ShowRepository extends JpaRepository<Show, Integer> {
	List<Show> findByScreenIn(List<Screen> screens);

}