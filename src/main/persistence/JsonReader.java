package persistence;

import model.Reminder;
import model.ReminderList;
import org.json.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;



// Represents a reader that reads list of reminders from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it
    // throws IOException if an error occurs reading data from file
    public ReminderList readReminder() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseReminderList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses reminder list from JSON object and returns it
    private ReminderList parseReminderList(JSONObject jsonObject) {
        ReminderList rl = new ReminderList(new ArrayList<>());

        for (int i = 0; i < jsonObject.length(); i++) {
            JSONArray jsonReminders = jsonObject.getJSONArray("reminder list");
            for (Object json : jsonReminders) {
                JSONObject nextReminder = (JSONObject) json;

                String whatToRemind = nextReminder.getString("reminder");
                int hour = nextReminder.getInt("hour");
                int minute = nextReminder.getInt("minute");
                String date = nextReminder.getString("date");

                Reminder r = new Reminder(whatToRemind, hour, minute, date);
                rl.addRemind(r);

            }
        }
        return rl;

    }



}
