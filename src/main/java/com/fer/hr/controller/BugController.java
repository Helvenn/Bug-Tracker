package com.fer.hr.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.fer.hr.ClassReload;
import com.fer.hr.model.Bug;
import com.fer.hr.model.Category;
import com.fer.hr.model.Project;
import com.fer.hr.model.Severity;
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
import com.fer.hr.model.post.BugPost;

@Controller
public class BugController {

	private static final BugManager bugManager = new BugManager();

	private static final UserManager userManager = new UserManager();

	private static final StateManager stateManager = new StateManager();

	private static final SeverityManager severityManager = new SeverityManager();

	private static final CategoryManager categoryManager = new CategoryManager();

	private static final ProjectManager projectManager = new ProjectManager();

	@GetMapping("/bug")
	public String bug(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();

		List<Bug> rawBugs =  bugManager.getByUser(userName);//bugManager.getAll();

		List<BugDisplay> bugs = new ArrayList<>();

		if (rawBugs != null) {
			for (Bug bug : rawBugs) {
				String state = DefaultState.getById(bug.getStateId());
				String category = DefaultCategory.getById(bug.getCategoryId());
				String severity = DefaultSeverity.getById(bug.getSeverityId());
				List<Project> projects = projectManager.getAll();

				String project = null;
				for (Project proj : projects) {
					if (proj.getId() == bug.getProjectId()) {
						project = proj.getName();
						break;
					}
				}
				BugDisplay bd = new BugDisplay(bug.getId(), bug.getName(), bug.getDescription(), bug.getTimeAdded(),
						bug.getTimeResolved(), category, severity, state, project);
				bugs.add(bd);
			}
		}

		model.addAttribute("bugs", bugs);

		return "/bug";
	}

	@PostMapping(value = "/bug/delete/{id}")
	public String delete(@PathVariable int id) {
		bugManager.delete(id);

		return "redirect:/bug";
	}

	@GetMapping("/bug-add")
	public String AddBug(Model model) {
		List<Category> cat = categoryManager.getAll();
		List<Severity> sev = severityManager.getAll();
		List<Project> proj = projectManager.getAll();

		model.addAttribute("categories", cat);
		model.addAttribute("severities", sev);
		model.addAttribute("projects", proj);
		model.addAttribute("bug", new BugPost());

		return "/bug-add";
	}

	@PostMapping("/bug-add-new")
	public String Add(@ModelAttribute BugPost bug, BindingResult result, Model model) {

		Timestamp timeAdded = new Timestamp(System.currentTimeMillis());
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();

		Bug bugAdd = new Bug(bug.getName(), bug.getDesc(), timeAdded, bug.getProjId(), 0, bug.getCatId(),
				bug.getSevId(), userName);
		bugManager.add(bugAdd);

		return "redirect:/bug";
	}

}
