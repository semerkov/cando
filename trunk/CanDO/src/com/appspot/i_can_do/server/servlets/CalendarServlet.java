package com.appspot.i_can_do.server.servlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.appspot.i_can_do.master.model.Event;
import com.appspot.i_can_do.master.model.EventCalendar;
import com.appspot.i_can_do.master.model.Task;
import com.appspot.i_can_do.master.model.TaskList;
import com.appspot.i_can_do.master.security.Permission;
import com.appspot.i_can_do.master.security.State;
import com.appspot.i_can_do.master.security.User;
import com.appspot.i_can_do.service.CanDOSecurityService;
import com.appspot.i_can_do.service.CanDOService;
import com.appspot.i_can_do.service.exceptions.LoginNameExistException;
import com.appspot.i_can_do.service.utils.ServletUtils;
import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.KeyFactory;
import org.apache.commons.io.IOUtils;

@SuppressWarnings("serial")
public class CalendarServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(CalendarServlet.class
            .getName());
    private static final List<String> SECURITY_ACTIONS = Arrays
            .asList("retrieveCalenderTable",  "retrieveDayCalender", "retrieveCalenderWeek",
                    "addCalendar", "retrieveEventCalendarMenu", "removeCalendar",
                    "updateCalendar", "addEvent", "updateEvent", "removeEvent",
                    "viewEvent", "getEvent", "viewTasks", "addTask", "removeTask", "updateTask", "changeTaskState");
    private static SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    private CanDOService canDOService;
    private User user;

    private static final List<String> SECURITY_MONTH_ACTIONS = Arrays
            .asList("this", "next", "previous");
    private static final List<Integer> SECURITY_MONTH = Arrays
            .asList(Calendar.JANUARY, Calendar.FEBRUARY,
                    Calendar.MARCH, Calendar.APRIL, Calendar.MAY,
                    Calendar.JUNE, Calendar.JULY, Calendar.AUGUST,
                    Calendar.SEPTEMBER, Calendar.OCTOBER, Calendar.NOVEMBER,
                    Calendar.DECEMBER);

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        canDOService = CanDOService.inctance();
        log.info("Servlet created");
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        user = (User)request.getSession().getAttribute("user");

        request.setAttribute("user", user);
        request.getRequestDispatcher("calendar.jsp").forward(request,response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String action = request.getParameter("action");

        if (!SECURITY_ACTIONS.contains(action)) {
            response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED,
                    "Not existing action!");
            return;
        }

        if (action.equals("retrieveEventCalendarMenu")) {
            fillEventCalendarMenu(request, response);
        } else if (action.equals("retrieveCalenderTable")) {
            retrieveCalenderTable(request, response);
        } else if (action.equals("addCalendar")) {
            addCalendar(request, response);
        } else if (action.equals("removeCalendar")) {
            removeCalendar(request, response);
        } else if (action.equals("updateCalendar")) {
            updateCalendar(request, response);
        } else if (action.equals("addEvent")) {
            addEvent(request, response);
        } else if (action.equals("updateEvent")) {
            updateEvent(request, response);
        } else if (action.equals("removeEvent")) {
            removeEvent(request, response);
        } else if (action.equals("viewEvent")) {
            viewEvent(request, response);
        } else if (action.equals("getEvent")) {
            getEvent(request, response);
        } else if (action.equals("viewTasks")) {
            viewTasks(request, response);
        } else if (action.equals("addTask")) {
            addTask(request, response);
        } else if (action.equals("removeTask")) {
            removeTask(request, response);
        } else if (action.equals("changeTaskState")) {
            changeTaskState(request, response);
        } else if (action.equals("updateTask")) {
            updateTask(request, response);
        } else if(action.equals("retrieveCalenderWeek")){
            retrieveCalenderWeek(request,response);
        } else if(action.equals("retrieveDayCalender")) {
            retrieveDayCalender(request,response);
        }
    }

    private void retrieveCalenderWeek(HttpServletRequest request,
                                       HttpServletResponse response) throws IOException, ServletException {
    	Integer month = Integer.parseInt(request.getParameter("currentMonth")
                .trim());
        Integer year = Integer.parseInt(request.getParameter("currentYear")
                .trim());
        Integer day = Integer.parseInt(request.getParameter("currentDay").trim());
        
        String action = request.getParameter("weekAction");
        String calendarKeys = request.getParameter("selectedCalendars");
        List<EventCalendar> calendars = new ArrayList<EventCalendar>();
        if (calendarKeys != null) {
            String[] k = calendarKeys.split(",");
            for (String key : k) {
                if (!key.isEmpty()) {
                    calendars.add(canDOService.getCalendarByKey(key));
                }
            }
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month,day,0,0,0);
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);
        if (SECURITY_MONTH_ACTIONS.contains(action)
                && SECURITY_MONTH.contains(month)) {
        	
                int offset = 0;
                if (action.equals("next")) {
                    offset=7;
                } else if (action.equals("previous")) {
                    offset=-7;
                }
            Calendar d_start = Calendar.getInstance();
            d_start.set(year,month,day+offset-calendar.get(Calendar.DAY_OF_WEEK)+1,0,0,0);
		
            Calendar d_finish = Calendar.getInstance();
            d_finish.setTime(d_start.getTime());
            d_finish.set(Calendar.DATE, d_start.get(Calendar.DATE)+7);
            
            //int dayOfMonth = d_start.get(Calendar.DATE);
            /*SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");*/
        	
            ArrayList<Event> events = new ArrayList<Event>();
            for (EventCalendar c : calendars) {
                for (Event e : c.getEvents())
                	if(e.getStart().after(d_start.getTime())&&e.getStart().before(d_finish.getTime()))
                		events.add(e);
            }
            Collections.sort(events, new Event());
 
            request.setAttribute("selectedDate", day);
            request.setAttribute("events", events);
            request.setAttribute("calendar", d_start);
            request.getRequestDispatcher("/WEB-INF/pages/createWeek.jsp")
                    .forward(request, response);
        }

    }

	private void retrieveDayCalender(HttpServletRequest request,
                                      HttpServletResponse response) throws IOException, ServletException {
        Integer month = Integer.parseInt(request.getParameter("currentMonth")
                .trim());
        Integer year = Integer.parseInt(request.getParameter("currentYear")
                .trim());
        Integer day = Integer.parseInt(request.getParameter("currentDay").trim());

        String dayAction = request.getParameter("dayAction");
        String calendarKeys = request.getParameter("selectedCalendars");
        List<EventCalendar> calendars = new ArrayList<EventCalendar>();
        if (calendarKeys != null) {
            String[] k = calendarKeys.split(",");
            for (String key : k) {
                if (!key.isEmpty()) {
                    calendars.add(canDOService.getCalendarByKey(key));
                }
            }
        }
        //Ok
        if (SECURITY_MONTH_ACTIONS.contains(dayAction)
                && SECURITY_MONTH.contains(month)) {

            int offset = 0;
            if (dayAction.equals("next")) {
                offset=1;
            } else if (dayAction.equals("previous")) {
                offset=-1;
            }
            Calendar d_start = Calendar.getInstance();
            d_start.set(year,month,day+offset,0,0,0);
            Calendar d_finish = Calendar.getInstance();
            d_finish.set(year,month,day+offset+1,0,0,0);
            int dayOfMonth = d_start.get(Calendar.SUNDAY);
            /*SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");*/
        	
            ArrayList<Event> events = new ArrayList<Event>();
            for (EventCalendar c : calendars) {
                for (Event e : c.getEvents())
                	if(e.getStart().after(d_start.getTime())&&e.getStart().before(d_finish.getTime()))
                		events.add(e);
            }
            Collections.sort(events, new Event());
            request.setAttribute("events", events);
            request.setAttribute("calendar", d_start);
            request.getRequestDispatcher("/WEB-INF/pages/createDay.jsp")
                    .forward(request, response);
        }

    }

    private void retrieveCalenderTable(HttpServletRequest request,
                                       HttpServletResponse response) throws IOException, ServletException {
        Integer month = Integer.parseInt(request.getParameter("currentMonth")
                .trim());
        Integer year = Integer.parseInt(request.getParameter("currentYear")
                .trim());
        Integer day = Integer.parseInt(request.getParameter("currentDay")
                .trim());
        
        String monthAction = request.getParameter("monthAction");
        String calendarKeys = request.getParameter("selectedCalendars");
        List<EventCalendar> calendars = new ArrayList<EventCalendar>();
        if (calendarKeys != null) {
            String[] k = calendarKeys.split(",");
            for (String key : k) {
                if (!key.isEmpty()) {
                    calendars.add(canDOService.getCalendarByKey(key));
                }
            }
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(0);
        if (SECURITY_MONTH_ACTIONS.contains(monthAction)
                && SECURITY_MONTH.contains(month)) {
            if (month.equals(Calendar.DECEMBER) && monthAction.equals("next")) {
                // next year case
                calendar.set(Calendar.YEAR, year + 1);
                calendar.set(Calendar.MONTH, Calendar.JANUARY);

            } else if (month.equals(Calendar.JANUARY)
                    && monthAction.equals("previous")) {
                // previous year case
                calendar.set(Calendar.YEAR, year - 1);
                calendar.set(Calendar.MONTH, Calendar.DECEMBER);

            } else {
                // common case
                calendar.set(Calendar.YEAR, year);
                int offset = 0;// if "this" case
                if (monthAction.equals("next")) {
                    offset++;
                } else if (monthAction.equals("previous")) {
                    offset--;
                }
                calendar.set(Calendar.MONTH, month + offset);
            }
            calendar.set(Calendar.DATE, day);
            request.setAttribute("calendar", calendar);
            request.setAttribute("calendars", calendars);
            request.getRequestDispatcher("/WEB-INF/pages/createMonth.jsp")
                    .forward(request, response);
        }

    }

    public void addCalendar(HttpServletRequest request,
                            HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("calendarName");
        String color = "#EAEAEA";
        // String color = request.getParameter("calendarName");
        if (name != null && color != null) {
            EventCalendar calendar = new EventCalendar();
            calendar.setName(name);
            calendar.setColor(color);
            canDOService.addCalendar(calendar, user.getKey());
            fillEventCalendarMenu(request, response);
        }
    }

    public void fillEventCalendarMenu(HttpServletRequest request,
                                      HttpServletResponse response) throws ServletException, IOException {
        List<EventCalendar> calendars = canDOService.getCalendars(user);
        request.setAttribute("calendars", calendars);
        request.getRequestDispatcher(
                "/WEB-INF/pages/createEventCalendarMenu.jsp").forward(request,
                response);
    }

    public void removeCalendar(HttpServletRequest request,
                               HttpServletResponse response) {
        String calendarKey = request.getParameter("calendarKey");
        if (!"".equals(calendarKey)) {
            canDOService.removeCalendarByKey(calendarKey);
        }

    }

    public void updateCalendar(HttpServletRequest request,
                               HttpServletResponse response) {
        String calendarKey = request.getParameter("calendarKey");
        String calendarName = request.getParameter("calendarName");
        if (calendarKey != null && calendarName != null) {
            EventCalendar calendar = canDOService.getCalendarByKey(calendarKey);
            calendar.setName(calendarName);
            canDOService.saveCalendar(calendar);
        }
    }

    @SuppressWarnings("deprecation")
    public void addEvent(HttpServletRequest request,
                         HttpServletResponse response) {
        String calendarKey = request.getParameter("calendarKey");
        String eventName = request.getParameter("eventName");
        String eventFinishDay = request.getParameter("eventFinishDay");
        String eventStartDay = request.getParameter("eventStartDay");
        String eventDesc = request.getParameter("eventDesc");
        int warningTimeDays = Integer.valueOf(request
                .getParameter("warningTimeDays"));
        int warningTimeHours = Integer.valueOf(request
                .getParameter("warningTimeHours"));
        int warningTimeMinutes = Integer.valueOf(request
                .getParameter("warningTimeMinutes"));
        if (calendarKey != null  && eventName != null  && eventFinishDay != null) {
            EventCalendar calendar = canDOService.getCalendarByKey(calendarKey);
            Event event = new Event();
            event.setName(eventName);
            if (eventDesc != null) {
                event.setDescription(eventDesc);
            } else {
                event.setDescription("");
            }

            Date finish;
            try {
                finish = formatter.parse(eventFinishDay);
            } catch (ParseException e) {
                return;
            }
            event.setFinish(finish);
            Date start;
            try {
                start = formatter.parse(eventStartDay);
            } catch (ParseException e) {
                start = new Date();
            }
            event.setStart(start);

            Date warningTime = new Date();
            warningTime.setTime(0);
            warningTime.setDate(warningTimeDays + 1);
            warningTime.setHours(warningTimeHours);
            warningTime.setMinutes(warningTimeMinutes);
            event.setWarningTime(warningTime);

            calendar.getEvents().add(event);
            canDOService.saveCalendar(calendar);
        }
    }

    public void updateEvent(HttpServletRequest request,
                            HttpServletResponse response) {
        String eventKey = request.getParameter("eventKey");
        String eventName = request.getParameter("eventName");
        String eventFinishDay = request.getParameter("eventFinishDay");
        String eventStartDay = request.getParameter("eventStartDay");
        String eventDesc = request.getParameter("eventDesc");
        int warningTimeDays = Integer.valueOf(request
                .getParameter("warningTimeDays"));
        int warningTimeHours = Integer.valueOf(request
                .getParameter("warningTimeHours"));
        int warningTimeMinutes = Integer.valueOf(request
                .getParameter("warningTimeMinutes"));
        if (eventKey != null  && eventName != null  && eventFinishDay != null) {
            Event event = canDOService.getEventByKey(eventKey);
            event.setName(eventName);
            if (eventDesc != null) {
                event.setDescription(eventDesc);
            } else {
                event.setDescription("");
            }

            Date finish;
            try {
                finish = formatter.parse(eventFinishDay);
            } catch (ParseException e) {
                return;
            }
            event.setFinish(finish);
            Date start;
            try {
                start = formatter.parse(eventStartDay);
            } catch (ParseException e) {
                start = new Date();
            }
            event.setStart(start);

            Date warningTime = new Date();
            warningTime.setTime(0);
            warningTime.setDate(warningTimeDays + 1);
            warningTime.setHours(warningTimeHours);
            warningTime.setMinutes(warningTimeMinutes);
            event.setWarningTime(warningTime);

            canDOService.saveEvent(event);
        }
    }

    public void removeEvent(HttpServletRequest request,
                            HttpServletResponse response) {
        String eventKey = request.getParameter("eventKey");
        if (eventKey != null) {
            EventCalendar calendar = canDOService.getCalendar(eventKey,
                    user.getKey());
            calendar.getEvents().remove(canDOService.getEventByKey(eventKey));
            canDOService.saveCalendar(calendar);
        }
    }

    private void viewEvent(HttpServletRequest request,
                           HttpServletResponse response) throws ServletException, IOException {
        String eventKey = request.getParameter("eventKey");
        if (eventKey!=null) {
            Event event = canDOService.getEventByKey(eventKey);
            EventCalendar calendar = canDOService.getCalendar(eventKey,
                    user.getKey());
            request.setAttribute("formatter", formatter);
            request.setAttribute("event", event);
            request.setAttribute("calendarName", calendar.getName());
            request.getRequestDispatcher("/WEB-INF/pages/viewEvent.jsp")
                    .forward(request, response);
        }

    }

    private void getEvent(HttpServletRequest request,
                          HttpServletResponse response) throws IOException {
        String eventKey = request.getParameter("eventKey");
        if (eventKey != null) {
            Event event = canDOService.getEventByKey(eventKey);
            EventCalendar calendar = canDOService.getCalendar(eventKey,
                    user.getKey());
            String[] data = new String[8];
            data[0] = event.getName() != null ? event.getName() : "";
            data[1] = event.getDescription() != null ? event.getDescription()
                    : "";
            data[2] = formatter.format(event.getStart());
            data[3] = formatter.format(event.getFinish());
            data[4] = calendar.getName();
            Date warningTime = event.getWarningTime();
            data[5] = String.valueOf(warningTime.getDate() - 1);
            data[6] = String.valueOf(warningTime.getHours());
            data[7] = String.valueOf(warningTime.getMinutes());

            ServletUtils.writeJson(response, data);
        }
    }

    private void viewTasks(HttpServletRequest request,
                           HttpServletResponse response) throws IOException, ServletException {
        List<TaskList> tasksLists = canDOService.getTaskLists(user, Permission.Owner);

        List<Task> tasks = new ArrayList<Task>();
        for(TaskList taskList : tasksLists){
            tasks.addAll(taskList.getTasks());
        }
        request.setAttribute("tasks", tasks);
        request.getRequestDispatcher("/WEB-INF/pages/viewTasks.jsp").forward(
                request, response);
    }

    private void addTask(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String name =(String) request.getParameter("taskName");
        if(name!=null&&!"".equals(name)){
            List<TaskList> tasksLists = canDOService.getTaskLists(user, Permission.Owner);
            if(tasksLists.isEmpty()){
                tasksLists.add(canDOService.addTaskList(new TaskList(), user.getKey()));
            }
            TaskList list = tasksLists.get(0);
            Task task = new Task();
            task.setName(name);
            task.setState(State.Undone);
            list.getTasks().add(task);
            canDOService.saveTaskList(list);
            ServletUtils.writeJson(response, "ready");
        }else{
            log.warning("not define name of task");
        }
    }
    private void removeTask(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String key =(String) request.getParameter("taskKey");
        List<TaskList> tasksLists = canDOService.getTaskLists(user, Permission.Owner);
        TaskList list = tasksLists.get(0);
        boolean find = false;
        for(Task task:list.getTasks()){
            if((KeyFactory.keyToString(task.getKey())).equals(key)){
                find = true;
                list.getTasks().remove(task);
                //canDOService.removeTaskByKey(key);
                canDOService.saveTaskList(list);

            }
        }
        if(find){
            ServletUtils.writeJson(response, "ready");
        }
        else
            log.warning("task key not found");

    }
    private void updateTask(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String key =(String) request.getParameter("taskKey");
        String name =(String) request.getParameter("taskName");
        String taskDate =(String) request.getParameter("todo_date");
        Date date = null;

        if("".equals(taskDate)){
            date = null;
        }else{
            try {
                date = formatter.parse(taskDate);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        String description =(String) request.getParameter("todo_description");
        List<TaskList> tasksLists = canDOService.getTaskLists(user, Permission.Owner);
        TaskList list = tasksLists.get(0);
        boolean find = false;
        for(int i=0;i<list.getTasks().size();i++){
            if((KeyFactory.keyToString(list.getTasks().get(i).getKey())).equals(key)){
                find = true;
                State st = list.getTasks().get(i).getState();
                if(State.Undone==st)
                    st=State.Done;
                else
                    st=State.Undone;
                list.getTasks().get(i).setName(name);
                list.getTasks().get(i).setDate(date);
                list.getTasks().get(i).setDescription(description);
                canDOService.saveTaskList(list);
            }
        }
        if(find){
            ServletUtils.writeJson(response, "ready");
        }
        else
            log.warning("task key not found");
    }
    private void changeTaskState(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String key =(String) request.getParameter("taskKey");
        List<TaskList> tasksLists = canDOService.getTaskLists(user, Permission.Owner);
        TaskList list = tasksLists.get(0);
        boolean find = false;
        for(int i=0;i<list.getTasks().size();i++){
            if((KeyFactory.keyToString(list.getTasks().get(i).getKey())).equals(key)){
                find = true;
                State st = list.getTasks().get(i).getState();
                if(State.Undone==st)
                    st=State.Done;
                else
                    st=State.Undone;
                list.getTasks().get(i).setState(st);
                canDOService.saveTaskList(list);
            }
        }
        if(find){
            ServletUtils.writeJson(response, "ready");
        }
        else
            log.warning("task key not found");
    }

}
