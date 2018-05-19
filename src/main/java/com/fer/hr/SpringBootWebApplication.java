package com.fer.hr;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fer.hr.model.AppUser;
import com.fer.hr.model.Bug;
import com.fer.hr.model.Category;
import com.fer.hr.model.Project;
import com.fer.hr.model.Role;
import com.fer.hr.model.Severity;
import com.fer.hr.model.State;
import com.fer.hr.model.UserProject;
import com.fer.hr.model.defaultIds.DefaultState;
import com.fer.hr.model.managers.BugManager;
import com.fer.hr.model.managers.CategoryManager;
import com.fer.hr.model.managers.ProjectManager;
import com.fer.hr.model.managers.RoleManager;
import com.fer.hr.model.managers.SeverityManager;
import com.fer.hr.model.managers.StateManager;
import com.fer.hr.model.managers.UPManager;
import com.fer.hr.model.managers.URBManager;
import com.fer.hr.model.managers.UserManager;

//http://www.thymeleaf.org/doc/articles/layouts.html
@SpringBootApplication
public class SpringBootWebApplication {

	public static void main(String[] args) throws Exception {
		/*
		StateManager sm = new StateManager();
		List<State> states = sm.getAll();
		if (states == null) {
			initDbData();
		} else {
			Lifecycle.initStates(states);
		}
		*/

		SpringApplication.run(SpringBootWebApplication.class, args);
	}

	public static void initDbData() throws InterruptedException {
		BugManager bm = new BugManager();
		CategoryManager cm = new CategoryManager();
		// ImageManager im = new ImageManager();
		ProjectManager pm = new ProjectManager();
		RoleManager rm = new RoleManager();
		SeverityManager sm = new SeverityManager();
		StateManager stm = new StateManager();
		URBManager urbm = new URBManager();
		UPManager upm = new UPManager();
		UserManager um = new UserManager();

		initSeverity(sm);
		initState(stm);
		initRole(rm);
		initCategory(cm);
		inituser(um);
		Integer pid = initProject(pm, um);
		initBug(bm);
		initUP(upm, um, rm, pid);
		initURB(urbm);
	}

	private static void initURB(URBManager urbm) {
		// TODO Auto-generated method stub

	}

	private static void initUP(UPManager upm, UserManager um, RoleManager rm, Integer projectId) {
		List<AppUser> users = um.getAll();
		List<Role> roles = rm.getAll();

		if (users == null || roles == null)
			return;

		for (AppUser user : users) {
			String userName = user.getUserName();
			int roleId = 0;
			if (userName.equals("user"))
				for (Role role : roles)
					if (role.getName().equals("user"))
						roleId = role.getId();

			if (userName.equals("developer"))
				for (Role role : roles)
					if (role.getName().equals("developer"))
						roleId = role.getId();

			if (userName.equals("leader"))
				for (Role role : roles)
					if (role.getName().equals("leader"))
						roleId = role.getId();

			UserProject up = new UserProject(user.getUserName(), roleId, projectId);
			upm.add(up);
		}

	}

	private static void initBug(BugManager bm) {
		Bug bug1 = new Bug("UI bug", "UI crashed on button click", new Timestamp(System.currentTimeMillis()), 0, 0, 0,
				0, "user");
		Bug bug2 = new Bug("UI bug 2", "UI crashed on button click", new Timestamp(System.currentTimeMillis()), 0, 0, 0,
				0, "developer");
		bug2.setTimeResolved(new Timestamp(System.currentTimeMillis() + 4342522212L));
		bug2.setId(DefaultState.OPEN);

		bm.add(bug1);
		bm.add(bug2);
	}

	private static void inituser(UserManager um) {
		AppUser user1 = new AppUser("user", "password", "user", "one", "user@btracker.com");
		AppUser user2 = new AppUser("developer", "password", "developer", "one", "developer@btracker.com");

		um.add(user2);
		um.add(user1);

	}

	private static void initCategory(CategoryManager cm) {
		Category cat1 = new Category("User interface");
		Category cat2 = new Category("Crash");
		Category cat3 = new Category("Connection issue");

		cm.add(cat3);
		cm.add(cat2);
		cm.add(cat1);

	}

	private static Integer initProject(ProjectManager pm, UserManager um) {
		AppUser leader = new AppUser("leader", "password", "Project", "Leader", "projleader@btracker.com");
		String id = um.add(leader);

		Project proj = new Project("Project", id);
		Integer idp = pm.add(proj);

		return idp;
	}

	private static void initRole(RoleManager rm) {
		Role r1 = new Role("user");
		Role r2 = new Role("leader");
		Role r3 = new Role("developer");

		rm.add(r1);
		rm.add(r2);
		rm.add(r3);

	}

	private static void initState(StateManager stm) throws InterruptedException {
		State s1 = new State("New");
		State s2 = new State("Assigned");
		State s3 = new State("Open");
		State s4 = new State("Fixed");
		State s5 = new State("Pending retest");
		State s6 = new State("Retest");
		State s7 = new State("Reopened");
		State s8 = new State("Duplicate");
		State s9 = new State("Rejected");
		State s10 = new State("Deffered");
		State s11 = new State("Not a bug");
		State s12 = new State("Verified");
		State s13 = new State("Closed");

		Integer i1 = stm.add(s1);
		Integer i2 = stm.add(s2);
		Integer i3 = stm.add(s3);
		Integer i4 = stm.add(s4);
		Integer i5 = stm.add(s5);
		Integer i6 = stm.add(s6);
		Integer i7 = stm.add(s7);
		Integer i8 = stm.add(s8);
		Integer i9 = stm.add(s9);
		Integer i10 = stm.add(s10);
		Integer i11 = stm.add(s11);
		Integer i12 = stm.add(s12);
		Integer i13 = stm.add(s13);

		

	}

	public static void initSeverity(SeverityManager sm) {
		Severity low = new Severity("Trivial");
		Severity lowmid = new Severity("Minor");
		Severity highmid = new Severity("Major");
		Severity high = new Severity("Critical");

		sm.add(high);
		sm.add(highmid);
		sm.add(lowmid);
		sm.add(low);
	}

}