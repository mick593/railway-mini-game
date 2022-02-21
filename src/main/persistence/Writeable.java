package persistence;

import org.json.JSONObject;

import java.io.PrintWriter;

public interface Writeable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
