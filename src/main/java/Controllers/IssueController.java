package Controllers;

import Main.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class IssueController {

    private static final Logger log = LoggerFactory.getLogger(IssueController.class);

    @Autowired
    Main.ProjectRepository prijRepo;

    @Autowired
    Main.UserRepository userRepo;
    
    @Autowired
    Main.IssueRepository issueRepo;

    @Autowired
    Main.SonarPushRepository sonarRepo;

	private JsonReader jsonReader = new JsonReader();
    
	@RequestMapping("/issue")
	public List<Issue> issues() {
		return issueRepo.findAll();
	}



	//updates sonar data in database
	@RequestMapping("/updatesonardata")
	public String updateSonar(@RequestParam("project") String projectName,
							  @RequestParam("useremail") String userEmail) {
        Project project = prijRepo.findByName(projectName);
        User user = userRepo.findByEmail(userEmail);
        SonarPush sonarPush = new SonarPush();
        sonarPush.setUser(user);
        sonarPush.setProject(project);

        java.util.Date newDate = new Date();
        Timestamp param = new Timestamp(newDate.getTime());
        log.info(String.valueOf(param));

        sonarPush.setDate(param);

        String sonarPushName = project.getSonarName();
        String issuesUrl = "http://145.24.222.130:9000/api/issues/search?statuses=OPEN&projects="+sonarPushName;
        String complexityTechDebtUrl = "http://145.24.222.130:9000/api/resources/index?resource="+sonarPushName+"&metrics=complexity,class_complexity,file_complexity,function_complexity,sqale_index,sqale_debt_ratio&format=json";

        getComplexityAndTechnicalDebt(complexityTechDebtUrl, sonarPush);
        sonarRepo.save(sonarPush);
        List<Issue> issueList = saveIssues(issuesUrl, user, sonarPush);

        // Call script van harmen voor cijfer.
        // De logic hier achter wordt: haal de laatste sonar push van dit project op, van dat project bijv: getComplexity, en als die lager is dan dat deze push is, heb je het verbeterd! good job :)
        new CalculateSalary(sonarPush, issueList);
        return "Done";
	}	
	
    public List<Issue> saveIssues(String url, User user, SonarPush sonarPush){
        List<Issue> issueList = new ArrayList<Issue>();
        try {
            JSONObject jobject = jsonReader.readJson(url);
            String paramPageSize = "pageSize=" + jobject.get("total").toString();
            JSONArray issues = jsonReader.readJsonComponent(url + "&" + paramPageSize, "issues");
            for (int i=0; i < issues.size(); i++) {
                JSONObject issue = (JSONObject) issues.get(i);

                if(issue.get("author").toString().equals(user.getEmail())){
                    String debt = "";
                    if (issue.keySet().contains("debt"))
                        debt = issue.get("debt").toString();

                    Issue newIssue = new Issue(issue.get("severity").toString(), issue.get("component").toString(), issue.get("message").toString(), debt, sonarPush);
                    issueList.add(newIssue);
                    issueRepo.save(newIssue);
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return issueList;
    }

    public void getComplexityAndTechnicalDebt(String url, SonarPush sonarPush){
        try {           
            JSONArray array = jsonReader.readJsonArray(url);            
            for(int n = 0; n < array.size(); n++){
                JSONObject object = (JSONObject) array.get(n);
                JSONArray ar = (JSONArray) object.get("msr");
                for(int i = 0; i < ar.size(); i++){;
                    JSONObject obj = (JSONObject) ar.get(i);
                    switch (obj.get("key").toString()){
                        case "complexity":
                            sonarPush.setOverallComplexity((Double) obj.get("val"));
                            break;
                        case "file_complexity":
                            sonarPush.setFileComplexity((Double) obj.get("val"));
                            break;
                        case "class_complexity":
                            sonarPush.setClassComplexity((Double) obj.get("val"));
                            break;
                        case "function_complexity":
                            sonarPush.setFunctionComplexity((Double) obj.get("val"));
                            break;
                        case "sqale_index":
                            sonarPush.setTechnicalDebt((Double) obj.get("val"));
                            break;
                    }
                }
            }            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
