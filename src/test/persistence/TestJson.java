package persistence;

import model.Reminder;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestJson {

    protected void checkReminderList(String whatToRemind, int hour, int minute, String date, Reminder r) {
        assertEquals(whatToRemind, r.getWhatToRemind());
        assertEquals(hour, r.getHour());
        assertEquals(minute, r.getMinute());
        assertEquals(date, r.getDate());
    }
}
