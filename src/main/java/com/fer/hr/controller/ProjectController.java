package com.fer.hr.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.fer.hr.keys.UPKey;
import com.fer.hr.model.Project;
import com.fer.hr.model.Role;
import com.fer.hr.model.UserProject;
import com.fer.hr.model.defaultIds.DefaultRole;
import com.fer.hr.model.displaydata.ProjectDisplay;
import com.fer.hr.model.managers.ProjectManager;
import com.fer.hr.model.managers.RoleManager;
import com.fer.hr.model.managers.UPManager;
import com.fer.hr.model.managers.UserManager;
import com.fer.hr.model.post.ProjectPost;

@Controller
public class ProjectController {
	
	private static final UserManager userManager = new UserManager();
	
	private static final ProjectManager projectManager = new ProjectManager();
	
	private static final UPManager upManager = new UPManager();
	
	private static final RoleManager roleManager = new RoleManager();
	
	@GetMapping("/project")
	public String project(Model model){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();
		
		List<Project> rawProjects = projectManager.getAll();
		List<ProjectDisplay> projects = new ArrayList<>();
		
		if (rawProjects != null) {
			for (Project p : rawProjects){
				UPKey up = new UPKey(p.getId(), userName);
				
				String role = roleManager.get(upManager.get(up).getRoleId()).getName();
				String name = p.getName();
				String leaderFirst = userManager.get(p.getLeaderId()).getFirstName();
				String leaderLast = userManager.get(p.getLeaderId()).getLastName();
				
				ProjectDisplay pd = new ProjectDisplay(p.getId(), name, leaderFirst, leaderLast, role);
				projects.add(pd);
			}
		}
		model.addAttribute("projects", projects);
		
		return "/project";
	}
	
	@GetMapping("/project-add")
	public String addProject(Model model){
		model.addAttribute("project", new ProjectPost());
		
		return "project-add";
	}
	
	@PostMapping("/project-add-new")
	public String add(@ModelAttribute ProjectPost proj, BindingResult result, Model model){
		int roleId = DefaultRole.LEADER;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();
		
		Project p = new Project(proj.getName(), userName);
		Integer pid = projectManager.add(p);
		UserProject up = new UserProject(userName, roleId, pid);
		upManager.add(up);
		
		return "redirect:/project";
	}
	
	

}
