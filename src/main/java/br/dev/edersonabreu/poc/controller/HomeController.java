package br.dev.edersonabreu.poc.controller;

import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

	@GetMapping
	public ModelAndView homePage(ModelAndView mView, Authentication authentication) {
		mView.setViewName("home");
		String username = authentication.getName();
        String roles = authentication.getAuthorities().stream()
		                .map(GrantedAuthority::getAuthority)
		                .collect(Collectors.joining(", "));
        
        mView.addObject("username", username);
        mView.addObject("roles", roles);
        
		return mView;
	}
	
	@GetMapping("/login")
	public ModelAndView loginPage(ModelAndView mView) {
		mView.setViewName("login");
		
		return mView;
	}
}
