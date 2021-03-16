package com.softech.tripnow.controllers;

import java.net.http.HttpClient.Redirect;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.softech.tripnow.models.ConfirmationToken;
import com.softech.tripnow.models.User;
import com.softech.tripnow.services.ConfirmationTokenService;
import com.softech.tripnow.services.UserService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class UserController {

	private final UserService userService;
	private final ConfirmationTokenService confirmationTokenService;

//	@GetMapping()
//	String signIn(Model model) {
//		User user = new User();
//		
//		model.addAttribute(user);
//		return "html/login";
//		
//	}
//
//	@PostMapping("/login")
//	String signUp(User user) {
//		
//		userService.signUpUser(user);
//		
//		return "Redirect::html/home-page";
//	}
//
//	@PostMapping("/sign-up/confirm")
//	String confirmMail(@RequestParam("token") String token) {
//	
//		Optional<ConfirmationToken> optional = confirmationTokenService.findConfirmationTokenByToken(token);
//		
//		optional.ifPresent(userService::confirmUser);
//		
//		return "Redirect::html/login";
//
//	}
	
	@GetMapping("/login/sign-in")
	String signIn(Model model) {
		User user = new User();
		model.addAttribute(user);
		return "html/login";
	}

	@GetMapping("/login/sign-up")
	String signUpPage(User user) {

		return "html/login";
	}
	
	@PostMapping("/login/sign-up")
	String signUp(User user) {

		userService.signUpUser(user);

		return "redirect:/html/home-page";
	}
	
	@GetMapping("/login/sign-up/confirm")
	String confirmMail(@RequestParam("token") String token) {

		Optional<ConfirmationToken> optionalConfirmationToken = confirmationTokenService.findConfirmationTokenByToken(token);

		optionalConfirmationToken.ifPresent(userService::confirmUser);

		return "redirect:/html/login";
	}

	
}
