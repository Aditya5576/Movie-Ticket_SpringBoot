package com.aditya.Movie_Ticket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GeneralController {

	@GetMapping("/")
	public String loadMain(ModelMap map) {
		return "index.html";
	}

}
