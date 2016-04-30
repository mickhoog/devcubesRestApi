package Main;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class SonarPersister {
	
	private final String issuesUurl = "http://145.24.222.130:9000/api/issues/search?";
	private final String createIssueUrl = "http://localhost:8080/issue/create";
	private final String urlIssue = "http://localhost:8080/issue/";
	private JsonReader jsonReader = new JsonReader();
	private HttpConnector httpConnector = new HttpConnector();
	
	public SonarPersister()
	{}
	
	//saves issues to database
	public void saveIssues() {
		try {
			JSONArray issues = jsonReader.readJsonComponent(issuesUurl, "issues");
			System.out.println("total: " + issues.size());
			for(int i =0; i < issues.size(); i++) {
				JSONObject jo = (JSONObject) issues.get(i);
				String key = jo.get("key").toString();
				if(jsonReader.readJson(urlIssue + key).size() == 0) {
					StringBuilder sb = new StringBuilder();
					String debt = "";			
					if (jo.keySet().contains("debt"))
						debt = jo.get("debt").toString();
						
					sb.append("id=" + key);
					sb.append("&severity=" + jo.get("severity").toString());
					sb.append("&component="+ jo.get("component").toString());
					sb.append("&message="+ jo.get("message").toString());
					sb.append("&debt="+ debt);
					sb.append("&date="+ jo.get("updateDate").toString());
					sb.append("&useremail="+ jo.get("author").toString());
					sb.append("&project="+ jo.get("project").toString());				
					httpConnector.sendPost(createIssueUrl, sb.toString());
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}		
}
