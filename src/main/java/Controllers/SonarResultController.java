package Controllers;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Main.Issue;
import Main.Project;
import Main.SonarPersister;
import Main.User;

@RestController
public class SonarResultController {
    @Autowired
    Main.ProjectRepository prijRepo;

    @Autowired
    Main.UserRepository userRepo;
    
    @Autowired
    Main.SonarResultRepository sonarRepo;
    
	
	@RequestMapping("/updatesonardata")
	public void UpdateSonar() {
    	SonarPersister sp = new SonarPersister();
    	sp.saveIssues();
	}
	
	@RequestMapping("/issue/create")
	public Issue createIssue(@RequestParam("id") String id,@RequestParam("severity") String severity,
							 @RequestParam("component") String component,@RequestParam("message") String message, 
							 @RequestParam("debt") String debt, @RequestParam("date") String date, 
							 @RequestParam("useremail") String email, @RequestParam("project") String project) {
		User user = userRepo.findByEmail(email);
		Project prij = prijRepo.findByName(project);	
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		format.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date date1 = new Date(new java.util.Date().getTime());
		try {
			date1 =  new Date(format.parse(date.substring(0, 9)).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Issue issue = new Issue(id, severity, component, message, debt, date1, prij, user);
		sonarRepo.save(issue);		
		return issue;
	}
	
	@RequestMapping("/issue/{id}")
	public Issue getIssue(@PathVariable("id") String id) {
		Issue issue = (Issue) sonarRepo.findIssueById(id);
		return issue;
	}
	
}
