package model;

import model.exceptions.IncorrectDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestReminderList {
    private ReminderList testList1;
    private ReminderList testList2;
    private ReminderList testList3;

    private ArrayList<Reminder> testArray1;
    private ArrayList<Reminder> testArray2;
    private ArrayList<Reminder> testArray3;

    private Reminder testReminder1;
    private Reminder testReminder2;
    private Reminder testReminder3;
    private Reminder testReminder4;


    @BeforeEach
    void runBefore() {
        testReminder1 = new Reminder("Return books at the library", 0, 45, "2021/05/12");
        testReminder2 = new Reminder("Watch for delivery", 17, 0, "2021/06/13");
        testReminder3 = new Reminder("Order a table", 9, 0, "2021/08/15");
        testReminder4 = new Reminder("Exercise", 23, 21, "2021/10/30");

        testArray1 = new ArrayList<>();
        testArray1.add(testReminder1);
        testArray1.add(testReminder2);

        testArray2 = new ArrayList<>();
        testArray2.add(testReminder1);
        testArray2.add(testReminder2);
        testArray2.add(testReminder3);
        testArray2.add(testReminder4);

        testArray3 = new ArrayList<>();
        testList1 = new ReminderList(testArray1);
        testList2 = new ReminderList(testArray2);
        testList3 = new ReminderList(testArray3);


    }


    @Test
    void testConstructor() {
        testList1 = new ReminderList(testArray1);
        testList2 = new ReminderList(testArray2);
        assertEquals(testArray1, testList1.getListOfReminder());
        assertEquals(testArray2, testList2.getListOfReminder());
        assertEquals(testArray3, testList3.getListOfReminder());
    }

    @Test void testSetListOfReminder(){
        testList1.setListOfReminder(testArray2);
        assertEquals(testList1.getListOfReminder(), testList2.getListOfReminder());
        assertEquals(4, testList1.getNumElementsInListOfReminder());
        assertEquals(4, testList1.getNumElementsInListOfReminder());
    }


    @Test
    void testAddRemindWithNoExceptionThrown() {

        Reminder testRemind1 = new Reminder("Watch for delivery", 17, 0, "2021/03/13");
        ArrayList<Reminder> testArr1 = new ArrayList<>();
        testArr1.add(testReminder1);
        testArr1.add(testReminder2);
        testArr1.add(testRemind1);
        try {
            testList1.addRemind("Watch for delivery", 17, 0, "2021/03/13");
            assertEquals(testArr1.get(1).getWhatToRemind(), testList1.getListOfReminder().get(1).getWhatToRemind());
            assertEquals(testArr1.get(1).getHour(), testList1.getListOfReminder().get(1).getHour());
            assertEquals(testArr1.get(1).getMinute(), testList1.getListOfReminder().get(1).getMinute());
            assertEquals(testArr1.get(1).getDate(), testList1.getListOfReminder().get(1).getDate());
        } catch (IncorrectDate d){
            fail("Shouldn't have thrown error!");
        }

    }


    @Test
    void testAddRemindWithExceptionThrown() {
        Reminder testRemind2 = new Reminder("Drink 7 cups of water", 12, 34, "2021/06/06");
        ArrayList<Reminder> testArr2 = new ArrayList<>();
        testArr2.add(testReminder1);
        testArr2.add(testReminder2);
        testArr2.add(testReminder3);
        testArr2.add(testReminder4);
        testArr2.add(testRemind2);

        try {
            testList2.addRemind("Drink 7 cups of water", 12, 34, "202/06/06");
            assertEquals(testArr2.get(4).getWhatToRemind(), testList2.getListOfReminder().get(4).getWhatToRemind());
            assertEquals(testArr2.get(4).getHour(), testList2.getListOfReminder().get(4).getHour());
            assertEquals(testArr2.get(4).getMinute(), testList2.getListOfReminder().get(4).getMinute());
            assertEquals(testArr2.get(4).getDate(), testList2.getListOfReminder().get(4).getDate());
            fail("Should've caught error");
        } catch (IncorrectDate d) {
            //pass
        }
    }


    @Test
    void testAddRemindParameterReminder() {

        Reminder testRemind1 = new Reminder("Watch for delivery", 17, 0, "2021/03/01");
        ArrayList<Reminder> testArr1 = new ArrayList<>();
        testArr1.add(testReminder1);
        testArr1.add(testReminder2);
        testArr1.add(testRemind1);
        testList1.addRemind(testRemind1);
        assertEquals(testArr1.get(1).getWhatToRemind(), testList1.getListOfReminder().get(1).getWhatToRemind());
        assertEquals(testArr1.get(1).getHour(), testList1.getListOfReminder().get(1).getHour());
        assertEquals(testArr1.get(1).getMinute(), testList1.getListOfReminder().get(1).getMinute());
        assertEquals(testArr1.get(1).getDate(), testList1.getListOfReminder().get(1).getDate());

    }

    @Test
    void testEditRemindNoExceptionThrown() {
        Reminder testRemind1 = new Reminder("Run", 12, 3, "2021/06/13");
        ArrayList<Reminder> testArr1 = new ArrayList<>();
        testArr1.add(testReminder1);
        testArr1.add(testReminder2);
        testArr1.set(1, testRemind1);
        try {
            testList1.editRemind(2, "Run", 12, 3, "2021/06/13");
            // my editRemind method has index 2 instead of 1, because I add 1 to make the list start at 1 and not 0
            assertEquals(testArr1.get(1).getWhatToRemind(), testList1.getListOfReminder().get(1).getWhatToRemind());
            assertEquals(testArr1.get(1).getHour(), testList1.getListOfReminder().get(1).getHour());
            assertEquals(testArr1.get(1).getMinute(), testList1.getListOfReminder().get(1).getMinute());
        } catch (IncorrectDate d) {
            fail("Should've not thrown exception");
        }
    }


    @Test
    void testEditRemindWithExceptionThrown() {

        Reminder testRemind1 = new Reminder("Make oatmeal", 22, 0, "2021/09/16");
        ArrayList<Reminder> testArr1 = new ArrayList<>();
        testArr1.add(testReminder1);
        testArr1.add(testReminder2);
        testArr1.set(1, testRemind1);
        try {
            testList1.editRemind(2, "Make oatmeal", 22, 0, "202/09/16");
            // my editRemind method has index 2 instead of 1, because I add 1 to make the list start at 1 and not 0
            assertEquals(testArr1.get(1).getWhatToRemind(), testList1.getListOfReminder().get(1).getWhatToRemind());
            assertEquals(testArr1.get(1).getHour(), testList1.getListOfReminder().get(1).getHour());
            assertEquals(testArr1.get(1).getMinute(), testList1.getListOfReminder().get(1).getMinute());
            fail("Should've thrown exception");
        } catch (IncorrectDate d) {
            //pass
        }
    }


    @Test
    void testDeleteRemind() {
        ArrayList<Reminder> testArr1 = new ArrayList<>();
        testArr1.add(testReminder1);
        testArr1.add(testReminder2);
        testArr1.remove(0);
        testList1.deleteRemind(1);
        // my deleteRemind method has index 1 instead of 0, because I add 1 to make the list start at 1 and not 0
        assertEquals(testArr1.get(0).getWhatToRemind(), testList1.getListOfReminder().get(0).getWhatToRemind());
        assertEquals(testArr1.get(0).getHour(), testList1.getListOfReminder().get(0).getHour());
        assertEquals(testArr1.get(0).getMinute(), testList1.getListOfReminder().get(0).getMinute());

        ArrayList<Reminder> testArr2 = new ArrayList<>();
        testArr2.add(testReminder1);
        testArr2.add(testReminder2);
        testArr2.add(testReminder3);
        testArr2.add(testReminder4);
        testArr2.remove(2);
        testList2.deleteRemind(3);
        // my deleteRemind method has index 3 instead of 2, because I add 1 to make the list start at 1 and not 0
        assertEquals(testArr2.get(2).getWhatToRemind(), testList2.getListOfReminder().get(2).getWhatToRemind());
        assertEquals(testArr2.get(2).getHour(), testList2.getListOfReminder().get(2).getHour());
        assertEquals(testArr2.get(2).getMinute(), testList2.getListOfReminder().get(2).getMinute());

        //deleteRemind needs at least one reminder inside of list
    }


    @Test
    void testToJson() {
        try {
            Reminder testReminder5 = new Reminder("Eat", 2, 45, "2021/06/12");
            Reminder testReminder6 = new Reminder("Dance", 1, 7, "2021/11/01");

            ArrayList<Reminder> testArray5 = new ArrayList<>();
            testArray5.add(testReminder5);
            testArray5.add(testReminder6);

            testReminder5.setMonth(6);
            testReminder6.setDay(1);

            ReminderList testList5 = new ReminderList(testArray5);


            testList5.toJson(); //simultaneously calls the toJson() in Reminder class as the one in ReminderList
            //calls the one in the Reminder class

            JsonWriter jsonWriter = new JsonWriter("./data/testToJSonReminderList.json");
            jsonWriter.open();
            jsonWriter.write(testList5);
            jsonWriter.close();

            JsonReader jsonReader = new JsonReader("./data/testToJSonReminderList.json");
            ReminderList rl = jsonReader.readReminder();
            assertEquals("reminder list", rl.getName());
            List<Reminder> listOfReminders = rl.getListOfReminder();

            assertEquals(2, listOfReminders.size());

            assertEquals("Eat", listOfReminders.get(0).getWhatToRemind());
            assertEquals(2, listOfReminders.get(0).getHour());
            assertEquals(45, listOfReminders.get(0).getMinute());
            assertEquals("2021/06/12", listOfReminders.get(0).getDate());

            assertEquals("Dance", listOfReminders.get(1).getWhatToRemind());
            assertEquals(1, listOfReminders.get(1).getHour());
            assertEquals(7, listOfReminders.get(1).getMinute());
            assertEquals("2021/11/01", listOfReminders.get(1).getDate());
        } catch (IOException exception) {
            fail("Whoops, didn't work");
        }

    }
}
