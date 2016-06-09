package Main;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;

public class JsonReader {

	public JsonReader() 
	{}
	
	public JSONArray readJsonComponent(String url, String component) throws ParseException, IOException {
		JSONParser parser = new JSONParser();
		InputStream is = new URL(url).openStream();
		InputStreamReader isr = new InputStreamReader(is, Charset.defaultCharset());		
		Object obj = parser.parse(isr);		
		JSONObject jsonObject = (JSONObject) obj;
		JSONArray components = new JSONArray();
		if(jsonObject.keySet().contains(component))
			components = (JSONArray) jsonObject.get(component);

		return components;
	}
		
	public JSONObject readJson(String url) throws IOException, ParseException {
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = new JSONObject();
		InputStream is = new URL(url).openStream();
		InputStreamReader isr = new InputStreamReader(is, Charset.defaultCharset());			
		Object obj = parser.parse(isr);		
		jsonObject = (JSONObject) obj;	
		return jsonObject;
	}
	
	public JSONArray readJsonArray(String url) throws ParseException, IOException {
		JSONParser parser = new JSONParser();
		InputStream is = new URL(url).openStream();
		InputStreamReader isr = new InputStreamReader(is, Charset.defaultCharset());		
		Object obj = parser.parse(isr);		
		JSONArray jArray = (JSONArray) obj;
		return jArray;
	}
}
