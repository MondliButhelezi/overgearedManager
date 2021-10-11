package logic;

public class Waiter extends logic.Manager {
    public void requestAbsentDay(String dayAbsent, String dayReplaced, String Name) {
        handle.execute("insert into absentRequests(name,day_absent,day_replaced,)VALUES(?,?,?)");

    }
}
