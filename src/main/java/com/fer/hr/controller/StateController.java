package com.fer.hr.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fer.hr.model.State;
import com.fer.hr.model.managers.StateManager;


@Controller
public class StateController {
	
	private static final StateManager sm = new StateManager();

	@GetMapping("/state")
	public String state(Model model){
		List<State> states = sm.getAll();
		model.addAttribute("states", states);
		
		return "/state";
	}
	
}
