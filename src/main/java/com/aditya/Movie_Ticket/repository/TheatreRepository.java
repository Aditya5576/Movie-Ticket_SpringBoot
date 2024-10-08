package com.aditya.Movie_Ticket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aditya.Movie_Ticket.dto.Theatre;

public interface TheatreRepository extends JpaRepository<Theatre, Integer> {

	boolean existsByEmail(String email);

	boolean existsByMobile(long mobile);

	Theatre findByMobile(long mobile);

	public Theatre findByEmail(String email);

	List<Theatre> findByApprovedFalseAndVerifiedTrue();

}
