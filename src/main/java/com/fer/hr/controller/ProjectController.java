package com.fer.hr.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
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

import com.fer.hr.keys.UPKey;
import com.fer.hr.model.Bug;
import com.fer.hr.model.History;
import com.fer.hr.model.Project;
import com.fer.hr.model.Role;
import com.fer.hr.model.UserProject;
import com.fer.hr.model.UserResolvingBug;
import com.fer.hr.model.defaultIds.DefaultCategory;
import com.fer.hr.model.defaultIds.DefaultRole;
import com.fer.hr.model.defaultIds.DefaultSeverity;
import com.fer.hr.model.defaultIds.DefaultState;
import com.fer.hr.model.displaydata.BarEntry;
import com.fer.hr.model.displaydata.BugDisplay;
import com.fer.hr.model.displaydata.HistoryDisplay;
import com.fer.hr.model.displaydata.ProjectDetails;
import com.fer.hr.model.displaydata.ProjectDisplay;
import com.fer.hr.model.displaydata.UserDisplay;
import com.fer.hr.model.managers.BugManager;
import com.fer.hr.model.managers.HistoryManager;
import com.fer.hr.model.managers.ProjectManager;
import com.fer.hr.model.managers.RoleManager;
import com.fer.hr.model.managers.UPManager;
import com.fer.hr.model.managers.URBManager;
import com.fer.hr.model.managers.UserManager;
import com.fer.hr.model.post.ProjectPost;
import com.fer.hr.model.post.UserPost;
import com.google.gson.Gson;

@Controller
public class ProjectController {

	private static final URBManager urbManager = new URBManager();

	private static final UserManager userManager = new UserManager();

	private static final ProjectManager projectManager = new ProjectManager();

	private static final UPManager upManager = new UPManager();

	private static final RoleManager roleManager = new RoleManager();

	private static final HistoryManager historyManager = new HistoryManager();

	private static final BugManager bugManager = new BugManager();

	@GetMapping("/project")
	public String project(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();

		List<Project> rawProjects = projectManager.getAll();
		List<ProjectDisplay> projects = new ArrayList<>();

		if (rawProjects != null) {
			for (Project p : rawProjects) {
				UPKey up = new UPKey(p.getId(), userName);
				UserProject usp = upManager.get(up);
				if (usp == null) {
					continue;
				}

				String role = DefaultRole.getById(usp.getRoleId());
				String name = p.getName();
				String leaderFirst = userManager.get(p.getLeaderId()).getFirstName();
				String leaderLast = userManager.get(p.getLeaderId()).getLastName();

				ProjectDisplay pd = new ProjectDisplay(p.getId(), name, leaderFirst, leaderLast, role);
				projects.add(pd);
			}
		}
		model.addAttribute("projects", projects);

		return "project";
	}

	@GetMapping("/project-add")
	public String addProject(Model model) {
		model.addAttribute("project", new ProjectPost());

		return "project-add";
	}

	@PostMapping("/project-add-new")
	public String add(@ModelAttribute ProjectPost proj, BindingResult result, Model model) {
		int roleId = DefaultRole.LEADER;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();

		Project p = new Project(proj.getName(), userName);
		Integer pid = projectManager.add(p);
		UserProject up = new UserProject(userName, roleId, pid);
		upManager.add(up);

		return "redirect:/project";
	}

	@GetMapping("/project-details/{id}")
	public String details(@PathVariable int id, Model model) {
		Project proj = projectManager.get(id);
		List<HistoryDisplay> hisDisp = getHistory(id);
		List<BugDisplay> bugDisp = getBugs(id);
		List<UserDisplay> userDisp = getUsers(id);

		ProjectDetails pd = new ProjectDetails(id, proj.getName(), hisDisp, bugDisp, userDisp);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();
		int role = upManager.get(new UPKey(id, userName)).getRoleId();
		if (role == DefaultRole.LEADER) {
			pd.setLeader(true);
		}

		model.addAttribute("islead", role == DefaultRole.LEADER);
		model.addAttribute("project", pd);

		return "project-details";
	}

	@PostMapping("/project-remove-user/{projectId}/{userName}")
	public String removeUser(@PathVariable int projectId, @PathVariable String userName, Model model) {
		UPKey key = new UPKey(projectId, userName);
		upManager.delete(key);

		return "redirect:/project-details/" + projectId;
	}

	@GetMapping("/project-add-user/{projectId}")
	public String addUser(@PathVariable int projectId, Model model) {
		List<Role> roles = new ArrayList<>();
		roles.add(roleManager.get(DefaultRole.USER));
		roles.add(roleManager.get(DefaultRole.DEVELOPER));

		UserPost up = new UserPost();
		up.setProjectId(projectId);

		model.addAttribute("roles", roles);
		model.addAttribute("user", up);

		return "project-add-user";
	}

	@PostMapping("/proj-add-new")
	public String add(@ModelAttribute UserPost user, BindingResult result, Model model) {
		if (userManager.get(user.getUserName()) != null) {
			UserProject up = new UserProject(user.getUserName(), user.getRoleId(), user.getProjectId());
			upManager.add(up);
		}

		return "redirect:/project-details/" + user.getProjectId();
	}

	@GetMapping("statistic/{projectId}")
	public String statistic(@PathVariable int projectId, Model model) {
		Gson gson = new Gson();

		// history of reopened vs non-reopened bugs
		int reopened = 0;
		List<Bug> all = bugManager.getByProject(projectId);
		for (Bug b : all) {
			List<History> hist = historyManager.getByBug(b.getId());
			for (History h : hist) {
				if (h.getNewState() == DefaultState.REOPENED) {
					reopened++;
					break;
				}
			}
		}
		int nonReopened = all.size() - reopened;

		// number of reported bugs by month, in the past 12 months
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		
		List<BarEntry> months = new ArrayList<>();
		List<String> labels = new ArrayList<>();
		List<Integer> values = new ArrayList<>();
		
		int currentYear = cal.get(Calendar.YEAR);
		int currentMonth = cal.get(Calendar.MONTH);
		List<History> projHist = historyManager.getByProject(projectId);
		for (int i = currentMonth + 2; i <= currentMonth + 13; i++) {
			int month;
			int year = currentYear;
			if (i > 12) {
				month = i - 12;
			} else {
				year--;
				month = i;
			}
			int monthEndDay = daysInMonth(year, month);
			
			Timestamp monthStart = Timestamp.valueOf(year + "-" + month + "-01 00:00:00.1");
			Timestamp monthEnd = Timestamp.valueOf(year + "-" + month + "-" + monthEndDay + " 23:59:59.9");
			
			int bugCount = 0;
			for (History h : projHist) {
				if (h.getNewState() == DefaultState.NEW){
					if (h.getTime().before(monthEnd) && h.getTime().after(monthStart)){
						bugCount++;
					}
				}
			}
			
			String monthName = month + ". " + year + ".";
			BarEntry bar = new BarEntry(monthName, bugCount);
			months.add(bar);
			labels.add(monthName);
			values.add(bugCount);
		}
		String monthChartData = gson.toJson(months);
		
		model.addAttribute("monthChart", monthChartData);
		model.addAttribute("labels", labels);
		model.addAttribute("values", values);
		model.addAttribute("reopened", reopened);
		model.addAttribute("nonReopened", nonReopened);

		return "statistic";
	}

	private List<HistoryDisplay> getHistory(int projectId) {
		List<History> hist = historyManager.getByProject(projectId);
		List<HistoryDisplay> hisDisp = new ArrayList<>();
		for (History h : hist) {
			Timestamp time = h.getTime();
			String state = DefaultState.getById(h.getNewState());
			String personFirstName = userManager.get(h.getPersonInCharge()).getFirstName();
			String personLastName = userManager.get(h.getPersonInCharge()).getLastName();
			Bug b = bugManager.get(h.getBugId());
			String bugName = "Deleted bug";
			if (b != null) {
				bugName = bugManager.get(h.getBugId()).getName();
			}
			HistoryDisplay hd = new HistoryDisplay(bugName, time, state, personFirstName, personLastName);
			hisDisp.add(hd);
		}
		return hisDisp;
	}

	private List<BugDisplay> getBugs(int projectId) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();
		UserProject bp = upManager.get(new UPKey(projectId, userName));
		int roleId = bp.getRoleId();
		boolean isLead = roleId == DefaultRole.LEADER;
		boolean isDev = roleId == DefaultRole.DEVELOPER;
		boolean isUser = roleId == DefaultRole.USER;

		System.err.println(roleId + " " + DefaultRole.LEADER + " " + DefaultRole.DEVELOPER + " " + DefaultRole.USER);
		System.err.println(" islead " + Boolean.toString(isLead) + " isdev " + Boolean.toString(isDev) + " isuser "
				+ Boolean.toString(isUser));

		List<Bug> list = bugManager.getByProject(projectId);
		List<BugDisplay> bugs = new ArrayList<>();
		for (Bug bug : list) {
			int id = bug.getId();
			String name = bug.getName();
			String description = bug.getDescription();
			Timestamp timeAdded = bug.getTimeAdded();
			Timestamp timeResolved = bug.getTimeResolved();
			String state = DefaultState.getById(bug.getStateId());
			String category = DefaultCategory.getById(bug.getCategoryId());
			String severity = DefaultSeverity.getById(bug.getSeverityId());

			BugDisplay bd = new BugDisplay(id, name, description, timeAdded, timeResolved, category, severity, state,
					null);
			List<UserResolvingBug> urb = urbManager.getAll();
			boolean devCanEdit = urb.contains(new UserResolvingBug(bug.getId(), userName));

			if (isUser || isLead) {
				bd.setCanDelete(false);
				if (bug.getStateId() == DefaultState.RETEST) {
					bd.setCanReopen(true);
					bd.setCanVerify(true);
				}

			}
			if ((isDev && devCanEdit) || isLead) {
				bd.setCanDelete(false);
				if (bug.getStateId() == DefaultState.ASSIGNED)
					bd.setCanOpen(true);
				else if (bug.getStateId() == DefaultState.OPEN)
					bd.setCanFix(true);
				else if (bug.getStateId() == DefaultState.FIXED)
					bd.setCanRetest(true);
			}
			if (isLead) {
				if (bug.getStateId() == DefaultState.ASSIGNED)
					bd.setCanReject(true);
				else if (bug.getStateId() == DefaultState.VERIFIED)
					bd.setCanClose(true);
				else if (bug.getStateId() == DefaultState.REOPENED || bug.getStateId() == DefaultState.NEW)
					bd.setCanAssign(true);
			}

			bugs.add(bd);
		}

		return bugs;
	}

	private List<UserDisplay> getUsers(int projectId) {
		List<UserProject> list = upManager.getAllByProject(projectId);
		List<UserDisplay> users = new ArrayList<>();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String currentUserName = auth.getName();
		boolean isLeader = upManager.get(new UPKey(projectId, currentUserName)).getRoleId() == DefaultRole.LEADER;

		for (UserProject up : list) {
			String userName = up.getId().getUserName();
			String role = DefaultRole.getById(up.getRoleId());
			String firstName = userManager.get(up.getId().getUserName()).getFirstName();
			String lastName = userManager.get(up.getId().getUserName()).getLastName();
			boolean canBeRemoved = false;
			if (isLeader && up.getRoleId() != DefaultRole.LEADER) {
				canBeRemoved = true;
			}

			UserDisplay ud = new UserDisplay(userName, firstName, lastName, role, canBeRemoved);
			users.add(ud);
		}

		return users;
	}
	
	private int daysInMonth(int year, int month){
		int monthEnd = 30;
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12){
			monthEnd = 31;
		} else if (month == 2){
			if (year % 4 == 0){
				monthEnd = 29;
			} else {
				monthEnd = 28;
			}
		}
		return monthEnd;
	}
}
