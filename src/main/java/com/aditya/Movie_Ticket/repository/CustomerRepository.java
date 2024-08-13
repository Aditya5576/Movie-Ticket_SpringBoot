package com.aditya.Movie_Ticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aditya.Movie_Ticket.dto.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	public boolean existsByEmail(String email);

	public boolean existsByMobile(long Mobile);

	public Customer findByMobile(long mobile);

	public Customer findByEmail(String email);
}
