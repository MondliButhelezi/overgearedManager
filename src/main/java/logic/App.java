package logic;
//
//import overgeared.Manager;

import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

//import java.util.Map;

public class App {

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }


    public static void main(String[] args) {
        port(getHerokuAssignedPort());

        Manager manager = new Manager();
//        List<String> weekdays = new ArrayList<>();
        manager.createTables();
//      manager.updateWaiterShift("tom",weekdays);
//        System.out.println(weekdays);
//        System.out.println(manager.waiterNames());
//        port(4567);
        staticFiles.location("/public");
        get("/", (req, res) -> {
            Map<String, Object> map = new HashMap<>();
            return new ModelAndView(map, "landingPage.handlebars");
        }, new HandlebarsTemplateEngine());

        get("/manager_chamber", (req, res) -> {
            Map<String, Object> map = new HashMap<>();
            map.put("weekday", manager.daysOfWeek());
            map.put("days", manager.daysOfWeek());
            map.put("waiters1",
                    manager.waitersWorkingOnTheDay(1));
            map.put("waiters2",
                    manager.waitersWorkingOnTheDay(2));
            map.put("waiters3",
                    manager.waitersWorkingOnTheDay(3));
            map.put("waiters4",
                    manager.waitersWorkingOnTheDay(4));
            map.put("waiters5",
                    manager.waitersWorkingOnTheDay(5));
            map.put("waiters6",
                    manager.waitersWorkingOnTheDay(6));
            map.put("waiters7",
                    manager.waitersWorkingOnTheDay(7));

            return new ModelAndView(map, "manager_chamber.handlebars");
        }, new HandlebarsTemplateEngine());


        get("/waiter_chamber", (req, res) -> {
            Map<String, Object> map = new HashMap<>();
            map.put("weekday", manager.daysOfWeek());
            map.put("days", manager.daysOfWeek());
            map.put("waiters1",
                    manager.waitersWorkingOnTheDay(1));
            map.put("waiters2",
                    manager.waitersWorkingOnTheDay(2));
            map.put("waiters3",
                    manager.waitersWorkingOnTheDay(3));
            map.put("waiters4",
                    manager.waitersWorkingOnTheDay(4));
            map.put("waiters5",
                    manager.waitersWorkingOnTheDay(5));
            map.put("waiters6",
                    manager.waitersWorkingOnTheDay(6));
            map.put("waiters7",
                    manager.waitersWorkingOnTheDay(7));


            return new ModelAndView(map, "waiter_chamber.handlebars");
        }, new HandlebarsTemplateEngine());


        get("/add_employees", (req, res) -> {
            Map<String, Object> map = new HashMap<>();
            map.put("weekday", manager.daysOfWeek());
            map.put("days", manager.daysOfWeek());

            return new ModelAndView(map, "update_employees.handlebars");
        }, new HandlebarsTemplateEngine());
        post("/add_employees", (req, res) -> {
            Map<String, Object> map = new HashMap<>();
            String waiterNameInsert = req.queryParams("waiterName");
            manager.addWaiter(waiterNameInsert);
            map.put("weekday", manager.daysOfWeek());
            map.put("days", manager.daysOfWeek());

            map.put("waiters1",
                    manager.waitersWorkingOnTheDay(1));
            map.put("waiters2",
                    manager.waitersWorkingOnTheDay(2));
            map.put("waiters3",
                    manager.waitersWorkingOnTheDay(3));
            map.put("waiters4",
                    manager.waitersWorkingOnTheDay(4));
            map.put("waiters5",
                    manager.waitersWorkingOnTheDay(5));
            map.put("waiters6",
                    manager.waitersWorkingOnTheDay(6));
            map.put("waiters7",
                    manager.waitersWorkingOnTheDay(7));

            return new ModelAndView(map, "update_employees.handlebars");

        }, new HandlebarsTemplateEngine());
        post("/clear_employees", (req, res) -> {
            Map<String, Object> map = new HashMap<>();
            String waiterNameInsert = req.queryParams("waiterName");

            manager.clearWaiterShifts();

            map.put("weekday", manager.daysOfWeek());
            map.put("days", manager.daysOfWeek());
            return new ModelAndView(map, "update_employees.handlebars");

        }, new HandlebarsTemplateEngine());
        get("/clear_employees", (req, res) -> {
            Map<String, Object> map = new HashMap<>();
            String waiterNameInsert = req.queryParams("waiterName");

            map.put("weekday", manager.daysOfWeek());
            map.put("days", manager.daysOfWeek());
            return new ModelAndView(map, "update_employees.handlebars");

        }, new HandlebarsTemplateEngine());


        get("/update_employees", (req, res) -> {
            Map<String, Object> map = new HashMap<>();
            map.put("weekday", manager.daysOfWeek());
            map.put("days", manager.daysOfWeek());
            map.put("waiters1",
                    manager.waitersWorkingOnTheDay(1));
            map.put("waiters2",
                    manager.waitersWorkingOnTheDay(2));
            map.put("waiters3",
                    manager.waitersWorkingOnTheDay(3));
            map.put("waiters4",
                    manager.waitersWorkingOnTheDay(4));
            map.put("waiters5",
                    manager.waitersWorkingOnTheDay(5));
            map.put("waiters6",
                    manager.waitersWorkingOnTheDay(6));
            map.put("waiters7",
                    manager.waitersWorkingOnTheDay(7));

            return new ModelAndView(map, "update_employees.handlebars");
        }, new HandlebarsTemplateEngine());

        post("/update_employees", (req, res) -> {
            Map<String, Object> map = new HashMap<>();
            map.put("weekday", manager.daysOfWeek());
            String waiterName = req.queryParams("employeeName");
            List<String> daysSelected = Arrays.asList(req.queryParamsValues("chkDays"));
            map.put("weekday", manager.daysOfWeek());
            map.put("days", manager.daysOfWeek());
            manager.updateWaiterShift(waiterName, daysSelected);
            map.put("waiters1",
                    manager.waitersWorkingOnTheDay(1));
            map.put("waiters2",
                    manager.waitersWorkingOnTheDay(2));
            map.put("waiters3",
                    manager.waitersWorkingOnTheDay(3));
            map.put("waiters4",
                    manager.waitersWorkingOnTheDay(4));
            map.put("waiters5",
                    manager.waitersWorkingOnTheDay(5));
            map.put("waiters6",
                    manager.waitersWorkingOnTheDay(6));
            map.put("waiters7",
                    manager.waitersWorkingOnTheDay(7));

            return new ModelAndView(map, "update_employees.handlebars");
        }, new HandlebarsTemplateEngine());
        get("/rosters", (req, res) -> {
            Map<String, Object> map = new HashMap<>();
            map.put("weekday", manager.daysOfWeek());
            map.put("days", manager.daysOfWeek());
            map.put("waiters1",
                    manager.waitersWorkingOnTheDay(1));
            map.put("waiters2",
                    manager.waitersWorkingOnTheDay(2));
            map.put("waiters3",
                    manager.waitersWorkingOnTheDay(3));
            map.put("waiters4",
                    manager.waitersWorkingOnTheDay(4));
            map.put("waiters5",
                    manager.waitersWorkingOnTheDay(5));
            map.put("waiters6",
                    manager.waitersWorkingOnTheDay(6));
            map.put("waiters7",
                    manager.waitersWorkingOnTheDay(7));

            return new ModelAndView(map, "rosters.handlebars");
        }, new HandlebarsTemplateEngine());

        post("/waiterAbsence", (req, res) -> {
            Map<String, Object> map = new HashMap<>();
            Waiter waiter=new Waiter();
            String nameOfWaiter = req.queryParams("waiterName");
            String dayOff = req.queryParams("dayOff");
            String dayToRepay = req.queryParams("dayReplaced");
            waiter.requestAbsentDay(nameOfWaiter, dayOff, dayToRepay);
            res.redirect("/waiter_chamber");
            return new ModelAndView(map, "waiterAbsence.handlebars");

        }, new HandlebarsTemplateEngine());

        get("/waiterAbsence", (req, res) -> {
            Map<String, Object> map = new HashMap<>();

            return new ModelAndView(map, "waiterAbsence.handlebars");

        }, new HandlebarsTemplateEngine());


//
//
        get("/absence_report", (req, res) -> {
            Map<String, Object> map = new HashMap<>();
            map.put("name", manager.waitersRequestedOffDays());
            map.put("dayAbsent", manager.daysOff());
            map.put("dayToReplace", manager.daysToBeReplaced());
            return new ModelAndView(map, "absence_report.handlebars");

        }, new HandlebarsTemplateEngine());

    }

}
