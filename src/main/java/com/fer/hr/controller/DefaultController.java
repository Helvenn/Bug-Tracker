package com.fer.hr.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultController {
	
	@GetMapping("/error")
	public String error(){
		return "error/404";
	}

	@GetMapping("/")
	public String home1() {
		return "home";
	}

	@GetMapping("/home")
	public String home() {
		return "home";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/403")
	public String error403() {
		return "error/403";
	}

	@GetMapping("/list")
	public String list(Model model){
		List<Integer> list = new ArrayList<>();
		list.add(3);
		list.add(5);
		list.add(767);
		
		model.addAttribute("integers", list);
		return "list";
	}
}
