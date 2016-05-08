package Controllers;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Main.Issue;
import Main.JsonReader;
import Main.Project;
import Main.User;

@RestController
public class IssueController {
	
    @Autowired
    Main.ProjectRepository prijRepo;

    @Autowired
    Main.UserRepository userRepo;
    
    @Autowired
    Main.IssueRepository issueRepo;
    
    private final String issuesUurl = "http://145.24.222.130:9000/api/issues/search?";    
	private JsonReader jsonReader = new JsonReader();
    
	@RequestMapping("/issue")
	public List<Issue> issues() {
		return issueRepo.findAll();
	}
	
	@RequestMapping("issue/project/{id}")	
	public List<Issue> issuesByproject(@PathVariable("id") int id) {
		return issueRepo.findByProject_id(id);
	}
	
	@RequestMapping("issue/user/{id}")
	public List<Issue> issuesByUser(@PathVariable("id") int id) {
		return issueRepo.findByUser_id(id);
	}
	
	//updates sonar data in database
	@RequestMapping("/updatesonardata")
	public void updateSonar() {
		saveIssues();
	}
	
	//saves issues to database
	public void saveIssues() {
		try {
			JSONObject jobject = jsonReader.readJson(issuesUurl);
			String paramPageSize = "pageSize=" + jobject.get("total").toString();
			JSONArray issues = jsonReader.readJsonComponent(issuesUurl+paramPageSize, "issues");
			for(int i =0; i < issues.size(); i++) {
				JSONObject jo = (JSONObject) issues.get(i);
				String key = jo.get("key").toString();
				if(issueRepo.findById(key) == null) {
					String project = jo.get("project").toString();
					if(prijRepo.findByName(project) == null) {
						continue;
					}
					String debt = "";			
					if (jo.keySet().contains("debt"))
						debt = jo.get("debt").toString();	
					User user = userRepo.findByEmail(jo.get("author").toString());
					Project prij = prijRepo.findByName(project);
					Issue issue = new Issue(key, jo.get("severity").toString(), jo.get("component").toString(),
							jo.get("message").toString(), debt, parseDate(jo.get("updateDate").toString()), prij, user);
					issueRepo.save(issue);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}		
	
	//parse string date to sql date 
	private java.sql.Date parseDate(String date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = new Date(new java.util.Date().getTime());
		try {
			date1 =  new Date(format.parse(date.substring(0, 9)).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return date1;
	}
	
	//creates issue
	@RequestMapping("/issue/create")
	public Issue createIssue(@RequestParam("id") String id,@RequestParam("severity") String severity,
							 @RequestParam("component") String component,@RequestParam("message") String message, 
							 @RequestParam("debt") String debt, @RequestParam("date") String date, 
							 @RequestParam("useremail") String email, @RequestParam("project") String project) {
		User user = userRepo.findByEmail(email);
		Project prij = prijRepo.findByName(project);	
		Date date1 = parseDate(date);
		Issue issue = new Issue(id, severity, component, message, debt, date1, prij, user);
		issueRepo.save(issue);		
		return issue;
	}
	
	//returns an issue given the id
	@RequestMapping("/issue/{id}")
	public Issue getIssue(@PathVariable("id") String id) {
		Issue issue = (Issue) issueRepo.findById(id);
		return issue;
	}
	
}
