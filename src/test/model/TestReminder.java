package model;


import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestReminder {
    private Reminder testReminder1;
    private Reminder testReminder2;
    private Reminder testReminder3;
    private Reminder testReminder4;


    @BeforeEach
    void runBefore() {
        testReminder1 = new Reminder("Tutoring", 5, 30, "2021/09/08");
        testReminder2 = new Reminder("CPSC 210 class", 11, 0, "2021/10/13");
        testReminder3 = new Reminder("Eat out with Jane", 3, 0, "2022/04/02");
        testReminder4 = new Reminder("Dentist", 14, 50, "2021/07/12");

    }

    @Test
    void testConstructor() {
        assertEquals("Tutoring", testReminder1.getWhatToRemind());
        assertEquals("5:30", (testReminder1.getHour() + ":" + testReminder1.getMinute()));
        assertEquals("2021/09/08", testReminder1.getDate());
        assertEquals(2021, testReminder1.getYear());
        assertEquals(9, testReminder1.getMonth());
        assertEquals(8, testReminder1.getDay());

        assertEquals("CPSC 210 class", testReminder2.getWhatToRemind());
        assertEquals(11, testReminder2.getHour());
        assertEquals(0, testReminder2.getMinute());
        assertEquals("2021/10/13", testReminder2.getDate());
        assertEquals(2021, testReminder2.getYear());
        assertEquals(10, testReminder2.getMonth());
        assertEquals(13, testReminder2.getDay());

        assertEquals("Eat out with Jane", testReminder3.getWhatToRemind());
        assertEquals(3, testReminder3.getHour());
        assertEquals(0, testReminder3.getMinute());
        assertEquals("2022/04/02", testReminder3.getDate());
        assertEquals(2022, testReminder3.getYear());
        assertEquals(4, testReminder3.getMonth());
        assertEquals(2, testReminder3.getDay());

        assertEquals("Dentist", testReminder4.getWhatToRemind());
        assertEquals("14:50", (testReminder4.getHour() + ":" + testReminder4.getMinute()));
        assertEquals("2021/07/12", testReminder4.getDate());
        assertEquals(2021, testReminder4.getYear());
        assertEquals(7, testReminder4.getMonth());
        assertEquals(12, testReminder4.getDay());

    }

    @Test
    void testSetWhatToRemind() {
        testReminder1.setWhatToRemind("Acupuncture");
        assertEquals("Acupuncture", testReminder1.getWhatToRemind());
        testReminder2.setWhatToRemind("Piano class");
        assertEquals("Piano class", testReminder2.getWhatToRemind());
        testReminder3.setWhatToRemind("Go swimming");
        assertEquals("Go swimming", testReminder3.getWhatToRemind());
        testReminder4.setWhatToRemind("Yoga");
        assertEquals("Yoga", testReminder4.getWhatToRemind());
    }

    @Test
    void testSetMonthAndDay(){
        assertEquals(9, testReminder1.getMonth());
        testReminder1.setMonth(10);
        assertEquals(10, testReminder1.getMonth());

        assertEquals(13, testReminder2.getDay());
        testReminder2.setDay(24);
        assertEquals(24, testReminder2.getDay());
    }

    @Test
    void testSetReminder() {
        testReminder1.setReminder("Turn on lights", 3, 21, "2021/08/04");
        assertEquals("Turn on lights", testReminder1.getWhatToRemind());
        assertEquals("3:21", (testReminder1.getHour() + ":" + testReminder1.getMinute()));
        assertEquals("2021/08/04", testReminder1.getDate());

        testReminder2.setReminder("Brush teeth", 21,00, "2021/05/11");
        assertEquals("Brush teeth", testReminder2.getWhatToRemind());
        assertEquals(21, testReminder2.getHour());
        assertEquals(0, testReminder2.getMinute());
        assertEquals("2021/05/11", testReminder2.getDate());

        testReminder3.setReminder("Make pasta", 6, 0, "2021/07/25");
        assertEquals("Make pasta", testReminder3.getWhatToRemind());
        assertEquals(6, testReminder3.getHour());
        assertEquals(0, testReminder3.getMinute());
        assertEquals("2021/07/25", testReminder3.getDate());

        testReminder4.setReminder("Fill up car gas", 15, 42, "2021/12/22");
        assertEquals("Fill up car gas", testReminder4.getWhatToRemind());
        assertEquals("15:42", (testReminder4.getHour() + ":" + testReminder4.getMinute()));
        assertEquals("2021/12/22", testReminder4.getDate());

    }

    @Test
    void testViewReminder() {
        assertEquals("Date: 2021/9/8   Time: 5h 30m    Reminder: Tutoring", testReminder1.viewReminder());
        assertEquals("Date: 2021/10/13   Time: 11h 0m    Reminder: CPSC 210 class", testReminder2.viewReminder());
        assertEquals("Date: 2022/4/2   Time: 3h 0m    Reminder: Eat out with Jane", testReminder3.viewReminder());
        assertEquals("Date: 2021/7/12   Time: 14h 50m    Reminder: Dentist", testReminder4.viewReminder());
    }



}