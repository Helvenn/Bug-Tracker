package tracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import tracker.model.State;
import tracker.model.managers.*;

@RestController
public class BugController {
	/*
	@Autowired
	private BugManager bugManager;
	@Autowired
	private CategoryManager categoryManager;
	@Autowired
	private ImageManager imageManager;
	@Autowired
	private SeverityManager severityManager;
	@Autowired
	private UserManager userManager;
	*/
	@GetMapping("/")
	public State index(){
		return new State("Work please?");
	}

}
