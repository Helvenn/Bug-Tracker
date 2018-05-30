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

import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.fer.hr.model.AppUser;
import com.fer.hr.model.Bug;
import com.fer.hr.model.Category;
import com.fer.hr.model.History;
import com.fer.hr.model.Project;
import com.fer.hr.model.Severity;
import com.fer.hr.model.UserProject;
import com.fer.hr.model.UserResolvingBug;
import com.fer.hr.model.defaultIds.DefaultCategory;
import com.fer.hr.model.defaultIds.DefaultRole;
import com.fer.hr.model.defaultIds.DefaultSeverity;
import com.fer.hr.model.defaultIds.DefaultState;
import com.fer.hr.model.displaydata.BugDisplay;
import com.fer.hr.model.displaydata.HistoryDisplay;
import com.fer.hr.model.managers.BugManager;
import com.fer.hr.model.managers.CategoryManager;
import com.fer.hr.model.managers.HistoryManager;
import com.fer.hr.model.managers.ProjectManager;
import com.fer.hr.model.managers.SeverityManager;
import com.fer.hr.model.managers.StateManager;
import com.fer.hr.model.managers.UPManager;
import com.fer.hr.model.managers.URBManager;
import com.fer.hr.model.managers.UserManager;
import com.fer.hr.model.post.AssignPost;
import com.fer.hr.model.post.BugPost;

@Controller
public class BugController {
	
	private static final UPManager upManager = new UPManager();
	
	private static final URBManager urbManager = new URBManager();

	private static final BugManager bugManager = new BugManager();
	
	private static final HistoryManager historyManager = new HistoryManager();

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

		return "bug";
	}

	@PostMapping(value = "/bug/delete/{id}")
	public String delete(@PathVariable int id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();
		int bugId = bugManager.get(id).getProjectId();
		History his = new History(id, new Timestamp(System.currentTimeMillis()), DefaultState.DELETED, userName, bugId);
		bugManager.delete(id);
		historyManager.add(his);

		return "redirect:/bug";
	}
	
	@PostMapping(value = "/bug/reopen/{id}")
	public String reopen(@PathVariable int id){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();
		Bug bug = bugManager.get(id);
		bug.setStateId(DefaultState.REOPENED);
		bugManager.update(bug);
		int bugId = bug.getId();
		History his = new History(id, new Timestamp(System.currentTimeMillis()), DefaultState.REOPENED, userName, bugId);
		historyManager.add(his);
		
		return "redirect:/bug"; 
	}
	
	@PostMapping(value = "/bug/close/{id}")
	public String close(@PathVariable int id){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();
		Bug bug = bugManager.get(id);
		bug.setStateId(DefaultState.CLOSED);
		bugManager.update(bug);
		int bugId = bug.getId();
		History his = new History(id, new Timestamp(System.currentTimeMillis()), DefaultState.REOPENED, userName, bugId);
		historyManager.add(his);
		
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

		return "bug-add";
	}

	@PostMapping("/bug-add-new")
	public String Add(@ModelAttribute BugPost bug, BindingResult result, Model model) {

		Timestamp timeAdded = new Timestamp(System.currentTimeMillis());
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();

		Bug bugAdd = new Bug(bug.getName(), bug.getDesc(), timeAdded, bug.getProjId(), 0, bug.getCatId(),
				bug.getSevId(), userName);
		
		Integer bugId = bugManager.add(bugAdd);
		History his = new History(bugId, new Timestamp(System.currentTimeMillis()), DefaultState.NEW, userName, bug.getProjId());
		historyManager.add(his);
		
		return "redirect:/bug";
	}
	
	@PostMapping("/bug/change-state/{bugId}/{newState}/{projectId}")
	public String changeState(@PathVariable int bugId, @PathVariable int newState, @PathVariable int projectId){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();
		Bug bug = bugManager.get(bugId);
		bug.setStateId(newState);
		if (newState == DefaultState.CLOSED){
			bug.setTimeResolved(new Timestamp(System.currentTimeMillis()));
		}
		bugManager.update(bug);
		
		History his = new History(bugId, new Timestamp(System.currentTimeMillis()), newState, userName, projectId);
		historyManager.add(his);
		
		return "redirect:/project-details/" + projectId;
	}
	
	@GetMapping("/bug/assign/{bugId}/{projectId}")
	public String assign(@PathVariable int bugId, @PathVariable int projectId, Model model){
		List<UserProject> all = upManager.getAllByProject(projectId);
		List<AppUser> developers = new ArrayList<>();
		
		for (UserProject up : all){
			if (up.getRoleId() == DefaultRole.DEVELOPER){
				developers.add(userManager.get(up.getId().getUserName()));
			}
		}
		
		AssignPost ap = new AssignPost();
		ap.setProjectId(projectId);
		ap.setBugId(bugId);
		
		model.addAttribute("assign", ap);
		model.addAttribute("developers", developers);
		
		return "bug-assign";
	}
	
	@PostMapping("/bug/assign")
	public String assignBugToDeveloper(@ModelAttribute AssignPost ap, BindingResult result, Model model){
		UserResolvingBug urb = new UserResolvingBug(ap.getBugId(), ap.getUserName());
		urbManager.add(urb);
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();
		Bug bug = bugManager.get(ap.getBugId());
		bug.setStateId(DefaultState.ASSIGNED);
		bugManager.update(bug);
		
		History his = new History(ap.getBugId(), new Timestamp(System.currentTimeMillis()), DefaultState.ASSIGNED, ap.getUserName(), ap.getProjectId());
		historyManager.add(his);
		
		
		return "redirect:/project-details/" + ap.getProjectId();
	}
	
	@GetMapping("/bug-details/{id}")
	public String history(@PathVariable int id, Model model){
		List<History> his = historyManager.getByBug(id);
		List<HistoryDisplay> hisDisp = new ArrayList<>();
		Bug bug = bugManager.get(id);
		for (History h : his) {
			Timestamp time = h.getTime();
			String state = DefaultState.getById(h.getNewState());
			String personFirstName = userManager.get(h.getPersonInCharge()).getFirstName();
			String personLastName = userManager.get(h.getPersonInCharge()).getLastName();
			HistoryDisplay hd = new HistoryDisplay(bug.getName(), time, state, personFirstName, personLastName);
			hisDisp.add(hd);
		}
		
		String bugName = "\"" + bugManager.get(id).getName() + "\" (id: " + id + ")"; 
		List<UserResolvingBug> urb = urbManager.getAll();
		List<AppUser> users = new ArrayList<>();
		for (UserResolvingBug u : urb){
			if (u.getId().getBugId() == id){
				users.add(userManager.get(u.getId().getUserName()));
			}
		}
		
		model.addAttribute("state", DefaultState.getById(bug.getStateId()));
		model.addAttribute("severity", DefaultSeverity.getById(bug.getSeverityId()));
		model.addAttribute("category", DefaultCategory.getById(bug.getCategoryId()));
		model.addAttribute("users", users);
		model.addAttribute("bug", bug);
		model.addAttribute("name", bugName);
		model.addAttribute("history", hisDisp);
		
		return "bug-details";
	}

}
