package persistence;

import model.Reminder;
import model.ReminderList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;

public class TestJsonReader extends TestJson{

    @Test
    void testFileDoesNotExistReader(){
        JsonReader jsonReader = new JsonReader("./data/doesNotExist.json");
        try{
            ReminderList rl = jsonReader.readReminder();
            fail("IOException expected");
        } catch (IOException exception){
            // pass
        }
    }

    @Test
    void testEmptyReminderListReader(){
        JsonReader jsonReader = new JsonReader("./data/testReaderEmptyReminderList.json");
        try {
            ReminderList rl = jsonReader.readReminder();
            assertEquals(0, rl.getNumElementsInListOfReminder());
            assertEquals("reminder list", rl.getName());
        } catch (IOException exception) {
            fail("Nothing in file");

        }
    }

    @Test
    void testGeneralReminderListReader(){
        JsonReader jsonReader = new JsonReader("./data/testReaderGeneralReminderList.json");
        try {
            ReminderList rl = jsonReader.readReminder();
            assertEquals("reminder list", rl.getName());
            List<Reminder> listOfReminder = rl.getListOfReminder();
            assertEquals(2, listOfReminder.size());
            checkReminderList("Do dishes", 12, 34, "2021/06/05", listOfReminder.get(0));
            checkReminderList("Clean room", 6, 2,"2021/05/03", listOfReminder.get(1));
        } catch (IOException exception){
            fail("Failed to read from file");
        }
    }

}
