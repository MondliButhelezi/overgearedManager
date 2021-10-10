package logic;

public class Waiter extends Manager {
    public void requestAbsentDay(String dayAbsent, String dayReplaced, String name) {
        handle.execute("insert into absentRequests(name,day_absent,day_replaced,)VALUES(?,?,?)",name,dayAbsent,dayReplaced);

    }
}
