package com.appspot.i_can_do.server.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appspot.i_can_do.master.model.Event;
import com.appspot.i_can_do.master.model.EventCalendar;
import com.appspot.i_can_do.master.model.Task;
import com.appspot.i_can_do.master.model.TaskList;
import com.appspot.i_can_do.master.security.State;
import com.appspot.i_can_do.master.security.User;
import com.appspot.i_can_do.service.CanDOSecurityService;
import com.appspot.i_can_do.service.CanDOService;
import com.appspot.i_can_do.service.EMF;
import com.appspot.i_can_do.service.exceptions.LoginNameExistException;

@SuppressWarnings("serial")
public class InitTestBaseServlet extends HttpServlet {
	private static final Logger log = Logger
			.getLogger(InitTestBaseServlet.class.getName());

	private User testUser;// TODO remove test user when complete registration
							// and etc.
	private CanDOService canDOService = CanDOService.inctance();

	@Override
	public void init(ServletConfig config) {
	}
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse res)
			throws ServletException, IOException {
		dropAll();
		initUser();
		initTest();
		
		res.setContentType("text/html");

		// Always set the Content Type before data is printed
		PrintWriter out = res.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Init database</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>Dropped</h1>");
		out.println("</body>");
		out.println("</html>");
		out.close();
	}

	public void dropAll() {
		/*EntityManager em = EMF.get().createEntityManager();
		EntityTransaction txn = em.getTransaction();
		txn.begin();
		try {
			em.createQuery("DELETE FROM Event t").executeUpdate();
			em.createQuery("DELETE FROM EventCalendar t").executeUpdate();
			em.createQuery("DELETE FROM CalendarKeeper t").executeUpdate();
			em.createQuery("DELETE FROM Task t").executeUpdate();
			em.createQuery("DELETE FROM TaskList t").executeUpdate();
			em.createQuery("DELETE FROM TaskKeeper t").executeUpdate();
			em.createQuery("DELETE FROM User t").executeUpdate();

			txn.commit();
			log.warning("DataBase dropped");
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}
		}*/
		dropWithQuery("DELETE FROM Event t");
		dropWithQuery("DELETE FROM EventCalendar t");
		dropWithQuery("DELETE FROM CalendarKeeper t");
		dropWithQuery("DELETE FROM Task t");
		dropWithQuery("DELETE FROM TaskList t");
		dropWithQuery("DELETE FROM TaskKeeper t");
		log.warning("DataBase dropped");
	}
	
	private void dropWithQuery(String dropQuery){
		EntityManager em = EMF.get().createEntityManager();
		EntityTransaction txn = em.getTransaction();
		txn.begin();
		try {
			em.createQuery(dropQuery).executeUpdate();
			txn.commit();
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}
		}
	}
	
	public void initUser() {
		log.info("Servlet created");

		String email = "user@email.com";
		testUser = CanDOSecurityService.instance().findUser(email);
		if (testUser == null) {
			// for test create new user
			testUser = new User();
			testUser.setEmail(email);
			testUser.setName("Mario");
			testUser.setSername("Gavani");
			try {
				CanDOSecurityService.instance()
						.addNewUser(testUser, "password");
				log.warning("Test user created");
			} catch (LoginNameExistException e) {
				log.warning("Test user exist");
			}
		} else {
			log.warning("Test user exist");
		}
	}

	public void initTest() {

		EventCalendar calendar = new EventCalendar();
		calendar.setName("Trip calendar");
		calendar.setColor("#EAEAEA");
		Date c1 = new Date();
		Event event = new Event("Train from Lviv", "Meet Natik from Lviv", c1,
				c1, c1);
		Event event2 = new Event("Trip to Kiev", "New Year in Kiev!!", c1, c1,
				c1);
		calendar.getEvents().add(event);
		calendar.getEvents().add(event2);
		canDOService.addCalendar(calendar, testUser.getKey());

		EventCalendar calendar1 = new EventCalendar();
		calendar1.setName("Gifts");
		calendar1.setColor("#EAEAEA");
		Date c2 = new Date();
		Event event3 = new Event("Moms birthday", "Buy a gift", c2, c2, c2);
		Event event4 = new Event("Natik birthday", "Buy a gift", c2, c2, c2);
		calendar1.getEvents().add(event3);
		calendar1.getEvents().add(event4);
		canDOService.addCalendar(calendar1, testUser.getKey());

		Task task = new Task();
		task.setName("First task");
		task.setState(State.Undone);
		task.setDate(new Date());

		Task task2 = new Task();
		task2.setName("Second task");
		task2.setState(State.Done);
		task2.setDate(new Date());

		TaskList taskList = new TaskList();
		taskList.setColor("red");
		taskList.setName("My tasks");
		taskList.getTasks().add(task);
		taskList.getTasks().add(task2);

		canDOService.addTaskList(taskList, testUser.getKey());

		log.warning("Database created");
	}
}
