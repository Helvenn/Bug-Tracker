package com.fer.hr.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fer.hr.ClassReload;
import com.fer.hr.model.Bug;
import com.fer.hr.model.Project;
import com.fer.hr.model.defaultIds.DefaultCategory;
import com.fer.hr.model.defaultIds.DefaultSeverity;
import com.fer.hr.model.defaultIds.DefaultState;
import com.fer.hr.model.displaydata.BugDisplay;
import com.fer.hr.model.managers.BugManager;
import com.fer.hr.model.managers.CategoryManager;
import com.fer.hr.model.managers.ProjectManager;
import com.fer.hr.model.managers.SeverityManager;
import com.fer.hr.model.managers.StateManager;
import com.fer.hr.model.managers.UserManager;

@Controller
public class BugController {
	
	private static final BugManager bugManager = new BugManager();

	private static final UserManager userManager = new UserManager();

	private static final StateManager stateManager = new StateManager();

	private static final SeverityManager severityManager = new SeverityManager();

	private static final CategoryManager categoryManager = new CategoryManager();
	
	private static final ProjectManager projectManager = new ProjectManager();

	@GetMapping("/bug")
	public String bug(Model model){
		//Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//String userName = auth.getName();
		
		List<Bug> rawBugs = ClassReload.reloadList(bugManager.getAll());
		
		for (Bug bug : rawBugs) {
			System.out.println(bug.getName());
		}
		
		List<BugDisplay> bugs = new ArrayList<>();
		
		
		if (rawBugs != null){
			for (Bug bug : rawBugs) {
				String state = DefaultState.getById(bug.getStateId());
				String category = DefaultCategory.getById(bug.getCategoryId());
				String severity = DefaultSeverity.getById(bug.getSeverityId());
				List<Project> projects = ClassReload.reloadList(projectManager.getAll());
				
				String project = null;
				for (Project proj : projects) {
					if (proj.getId() == bug.getProjectId()){
						project = proj.getName();
						break;
					}
				}
				BugDisplay bd = new BugDisplay(bug.getId(), bug.getName(), bug.getDescription(),
						bug.getTimeAdded(), bug.getTimeResolved(), category, severity, state, project);
				bugs.add(bd);
			}
		}
			
		model.addAttribute("bugs", bugs);
		
		return "/bug";
	}

}
