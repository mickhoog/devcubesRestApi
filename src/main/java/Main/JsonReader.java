package Main;

import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public interface JsonReader {

	JSONArray readJsonComponent(String source, String component) throws ParseException, IOException ;
	JSONObject readJson(String source) throws IOException, ParseException;
	JSONArray readJsonArray(String source) throws ParseException, IOException;
}
