package com.aditya.Movie_Ticket.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aditya.Movie_Ticket.dto.Theatre;
import com.aditya.Movie_Ticket.helper.AES;
import com.aditya.Movie_Ticket.helper.EmailSendingHelper;
import com.aditya.Movie_Ticket.repository.CustomerRepository;
import com.aditya.Movie_Ticket.repository.TheatreRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/theatre")
public class TheatreController {

	@Autowired
	Theatre theatre;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	TheatreRepository theatreRepository;

	@Autowired
	EmailSendingHelper emailSendingHelper;

	@GetMapping("/signup")
	public String loadSignup(ModelMap map) {
		map.put("theatre", theatre);
		return "theatre-signup.html";
	}

	@PostMapping("/signup")
	public String signup(@Valid Theatre theatre, BindingResult result, HttpSession session) {
		if (!theatre.getPassword().equals(theatre.getConfirmpassword())) {
			result.rejectValue("confirmpassword", "error.confirmpassword", "* Password Missmatch");
		}
		if (customerRepository.existsByEmail(theatre.getEmail())
				|| theatreRepository.existsByEmail(theatre.getEmail())) {
			result.rejectValue("email", "error.email", "* Account Already Exists");
		}
		if (customerRepository.existsByMobile(theatre.getMobile())
				|| theatreRepository.existsByMobile(theatre.getMobile())) {
			result.rejectValue("mobile", "error.mobile", "* Account Already Exists");
		}

		if (result.hasErrors()) {
			return "theatre-signup.html";
		} else {
			theatre.setPassword(AES.encrypt(theatre.getPassword(), "123"));
			theatre.setOtp(new Random().nextInt(100000, 1000000));
			emailSendingHelper.sendMailToTheatre(theatre);
			theatreRepository.save(theatre);
			session.setAttribute("success", "Otp Sent Success!!!");
			session.setAttribute("id", theatre.getId());
			return "redirect:/theatre/enter-otp";
		}
	}

	@GetMapping("/enter-otp")
	public String enterOtp(ModelMap map) {
		map.put("user", "theatre");
		return "enter-otp.html";
	}

	@PostMapping("/verify-otp")
	public String verifyOtp(@RequestParam int id, @RequestParam int otp, HttpSession session) {
		Theatre theatre = theatreRepository.findById(id).orElseThrow();
		if (theatre.getOtp() == otp) {
			theatre.setVerified(true);
			theatreRepository.save(theatre);
			session.setAttribute("success", "Account Created Success");
			return "redirect:/login";
		} else {
			session.setAttribute("failure", "Invalid OTP! Try Again");
			return "redirect:/theatre/enter-otp";
		}
	}

}