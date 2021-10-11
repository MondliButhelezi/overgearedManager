package logic;//import jdk.internal.org.objectweb.asm.Handle;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Manager {

    String dbDiskURL = "jdbc:h2:file:./overgeared.db";
    Jdbi jdbi = Jdbi.create(dbDiskURL, "sa", "");
    Handle handle = jdbi.open();

    public List waiterNames() {
        List<String> names = handle.createQuery("select name from waiters")
                .mapTo(String.class)
                .list();

        return names;
    }

    public void sendReport(String nameOfWaiter, String dayOff, String dayReplaced) {
        handle.execute("insert into absentRequests(name,day_absent,day_replaced)VALUES(?,?,?)", nameOfWaiter, dayOff, dayReplaced);
    }

    public void createTables() {
//        handle.execute("drop table if exists waiters");
        handle.execute("drop table if exists weekdays");
//        handle.execute("drop table if exists shifts");
        handle.execute("drop table if exists absentRequests");

        handle.execute("create table if not exists absentRequests(id integer identity,name varchar(50),day_absent varchar(50),day_replaced varchar(50))");
        handle.execute("create table if not exists waiters(id integer identity,name varchar(50))");
        handle.execute("create table if not exists weekdays(id integer identity , name varchar(50))");
        handle.execute("create table if not exists shifts ( id integer identity, waiternameid int not null,weekdayid int not null, FOREIGN key (waiternameid) REFERENCES waiters(id),FOREIGN key (weekdayid) REFERENCES weekdays(id))    ");

        handle.execute("insert into weekdays (name)VALUES('Monday')");
        handle.execute("insert into weekdays (name)VALUES('Tuesday')");
        handle.execute("insert into weekdays (name)VALUES('Wednesday')");
        handle.execute("insert into weekdays (name)VALUES('Thursday')");
        handle.execute("insert into weekdays (name)VALUES('Friday')");
        handle.execute("insert into weekdays (name)VALUES('Saturday')");
        handle.execute("insert into weekdays (name)VALUES('Sunday')");
    }

    public List<String> waitersRequestedOffDays() {
        List<String> namesOfWaiters = handle.select("select name from absentRequests").mapTo(String.class).list();

        return namesOfWaiters;
    }

    public List<String> daysOff() {
        List<String> daysOff = handle.select("select day_absent from absentRequests").mapTo(String.class).list();

        return daysOff;
    }

    public void removeAbsenteeList() {
        handle.execute("drop table absentRequests");
        handle.execute("create table if not exists absentRequests(id integer identity,name varchar(50),day_absent varchar(50),day_replaced varchar(50))");

    }

    public List<String> daysToBeReplaced() {
        List<String> dayToBeReplaced = handle.select("select day_replaced from absentRequests").mapTo(String.class).list();
        return dayToBeReplaced;
    }

    public void addWaiter(String name) {
        int checkIfUserExist = handle.select("select count(*) from waiters where name = ?", name.toLowerCase())
                .mapTo(int.class)
                .findOnly();

        if (checkIfUserExist < 1) {
            handle.execute("insert into waiters(name) VALUES (?)", name.toLowerCase());
        } else if (checkIfUserExist > 0) {
            ifUserExists();
        }
    }

    public String ifUserExists() {
        return "User already exists";
    }

    public void clearWaiterShifts() {
        handle.execute("drop table shifts");
        handle.execute("drop table waiters");
        handle.execute("create table if not exists waiters(id integer identity,name varchar(50))");

        handle.execute("create table if not exists shifts ( id integer identity, waiternameid int not null,weekdayid int not null, FOREIGN key (waiternameid) REFERENCES waiters(id),FOREIGN key (weekdayid) REFERENCES weekdays(id))    ");

    }

    public List<String> waitersWorkingOnTheDay(int dayID) {
        List<String> waitersDaysWorkingOnADay = handle.select("SELECT  waiters.name AS name\n" +
                "    FROM waiters\n" +
                "    LEFT JOIN shifts\n" +
                "    ON waiters.id=shifts.waiternameid\n" +
                "    LEFT JOIN weekdays\n" +
                "    ON weekdays.id=shifts.weekdayid\n" +
                "    where weekdays.id=?", dayID).mapTo(String.class).list();
        return waitersDaysWorkingOnADay;
        ///gives the users working on a day

    }

    public List<Integer> countWaiters() {
        List<Integer> countPerDay = new ArrayList<>();
        for (int i = 1; i < 8; i++) {
//            System.out.println("bread");

            List<Integer> waitersPerDay = handle.select("select id from weekdays where id=?", i).mapTo(int.class).list();
            countPerDay.add(waitersWorkingOnTheDay(i).size());
//            System.out.println(waitersWorkingOnTheDay(i).size());//<<---- this gives the amount of users on a given day
        }

        return countPerDay;
    }

    public List<String> daysOfWeek() {
        List<String> daysOfTheWeek = handle.createQuery("select name from weekdays").mapTo(String.class).list();
//        System.out.println(daysOfTheWeek);
        return daysOfTheWeek;
    }

    public void updateWaiterShift(String waiterName, List<String> weekday) {
//        addWaiter(waiterName);
        for (String day : weekday) {
            int dayID = handle.select("select id from weekdays where name=?", day).
                    mapTo(int.class).findOnly();
            int waiterID = handle.select("select id from waiters where name =?", waiterName).mapTo(int.class).findOnly();
//            System.out.println(dayID);
//            System.out.println(waiterID);
            handle.execute("insert into shifts (waiternameid,weekdayid)VALUES(?,?)", waiterID, dayID);
        }
//        List <Integer> waitersid=handle.select("select waiternameid from shifts").mapTo(int.class).list();
//
//        List <Integer> shiftsdayid=handle.select("select weekdayid from shifts").mapTo(int.class).list();
//        System.out.println(waitersid);
//        System.out.println(shiftsdayid);
    }
//        else if (userExists == 1) {
//            int id = handle.execute("select id from waiters where name =?", waiterName);
//            handle.execute("UPDATE shifts\n" +
//                    "SET weekdayid = ?, ...\n" +
//                    "WHERE waiternameid=?", id, id);
//
//        }

//set foreign key weekday id to equal the id of the waiter entered where the waiter's id was first found
//    }

}
