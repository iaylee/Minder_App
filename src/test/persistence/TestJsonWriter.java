package persistence;

import model.exceptions.IncorrectDate;
import model.Reminder;
import model.ReminderList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class TestJsonWriter extends TestJson{

    @Test
    void testNoSuchFileWriter(){
        try {
            ReminderList rl = new ReminderList(new ArrayList<Reminder>());
            JsonWriter jsonWriter = new JsonWriter("./data/my\0illegal:fileName.json");
            jsonWriter.open();
            fail("IOException expected");
        } catch (IOException exception){

        }
    }

    @Test
    void testEmptyReminderListWriter(){
        try {
            ReminderList rl = new ReminderList(new ArrayList<Reminder>());
            JsonWriter jsonWriter = new JsonWriter("./data/testEmptyReminderListWriter.json");
            jsonWriter.open();
            jsonWriter.write(rl);
            jsonWriter.close();

            JsonReader jsonReader = new JsonReader("./data/testEmptyReminderListWriter.json");
            rl = jsonReader.readReminder();
            assertEquals("reminder list", rl.getName());
            assertEquals(0, rl.getNumElementsInListOfReminder());
        } catch (IOException exception) {
            fail("Shouldn't fail");
        }
    }

    @Test
    void testGeneralWorkroomWriterWithYearError() {
        try {
            ReminderList rl = new ReminderList(new ArrayList<Reminder>());
            rl.addRemind("Sing", 4, 55, "2021/04/03");
            rl.addRemind("Sleep", 23, 59, "201/12/05");
            JsonWriter jsonWriter = new JsonWriter("./data/testGeneralReminderListWriter.json");
            jsonWriter.open();
            jsonWriter.write(rl);
            jsonWriter.close();

            JsonReader jsonReader = new JsonReader("./data/testGeneralReminderListWriter.json");
            rl = jsonReader.readReminder();
            assertEquals("reminder list", rl.getName());
            List<Reminder> listOfReminders = rl.getListOfReminder();
            assertEquals(2, listOfReminders.size());
            checkReminderList("Sing", 4, 55, "2021/04/03", listOfReminders.get(0));
            checkReminderList("Sleep", 23, 59, "2021/12/05", listOfReminders.get(1));
            fail("Should have thrown incorrect date error");
        } catch (IncorrectDate d) {
            //pass
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testGeneralWorkroomWriterWithMonthError() {
        try {
            ReminderList rl = new ReminderList(new ArrayList<Reminder>());
            rl.addRemind("Sing", 4, 55, "2021/5/03");
            rl.addRemind("Sleep", 23, 59, "2021/1/3");
            JsonWriter jsonWriter = new JsonWriter("./data/testGeneralReminderListWriter.json");
            jsonWriter.open();
            jsonWriter.write(rl);
            jsonWriter.close();

            JsonReader jsonReader = new JsonReader("./data/testGeneralReminderListWriter.json");
            rl = jsonReader.readReminder();
            assertEquals("reminder list", rl.getName());
            List<Reminder> listOfReminders = rl.getListOfReminder();
            assertEquals(2, listOfReminders.size());
            checkReminderList("Sing", 4, 55, "2021/05/03", listOfReminders.get(0));
            checkReminderList("Sleep", 23, 59, "2021/01/03", listOfReminders.get(1));
            fail("Should have thrown incorrect date error");
        } catch (IncorrectDate d) {
            //pass
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testGeneralWorkroomWriterWithDayError() {
        try {
            ReminderList rl = new ReminderList(new ArrayList<Reminder>());
            rl.addRemind("Sing", 4, 55, "2021/0/03");
            rl.addRemind("Sleep", 23, 59, "2021/12/");
            JsonWriter jsonWriter = new JsonWriter("./data/testGeneralReminderListWriter.json");
            jsonWriter.open();
            jsonWriter.write(rl);
            jsonWriter.close();

            JsonReader jsonReader = new JsonReader("./data/testGeneralReminderListWriter.json");
            rl = jsonReader.readReminder();
            assertEquals("reminder list", rl.getName());
            List<Reminder> listOfReminders = rl.getListOfReminder();
            assertEquals(2, listOfReminders.size());
            checkReminderList("Sing", 4, 55, "2021/05/03", listOfReminders.get(0));
            checkReminderList("Sleep", 23, 59, "2021/12/05", listOfReminders.get(1));
            fail("Should have thrown incorrect date error");
        } catch (IncorrectDate d) {
            //pass
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testGeneralWorkroomWriterWithDateNoError() {
        try {
            ReminderList rl = new ReminderList(new ArrayList<Reminder>());
            rl.addRemind("Sing", 4, 55, "2021/04/05");
            rl.addRemind("Sleep", 23, 59, "2021/09/06");
            JsonWriter jsonWriter = new JsonWriter("./data/testGeneralReminderListWriter2.json");
            jsonWriter.open();
            jsonWriter.write(rl);
            jsonWriter.close();

            JsonReader jsonReader = new JsonReader("./data/testGeneralReminderListWriter2.json");
            rl = jsonReader.readReminder();
            assertEquals("reminder list", rl.getName());
            List<Reminder> listOfReminders = rl.getListOfReminder();
            assertEquals(2, listOfReminders.size());
            checkReminderList("Sing", 4, 55, "2021/04/05", listOfReminders.get(0));
            checkReminderList("Sleep", 23, 59, "2021/09/06", listOfReminders.get(1));

        } catch (IncorrectDate d) {
            fail("Incorrect date error should have not been thrown");
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}
