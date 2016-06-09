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

import org.json.simple.parser.ParseException;

import java.io.IOException;

@RestController
public class IssueController {

    private static final Logger log = LoggerFactory.getLogger(IssueController.class);
    private final String apiUrl = "http://145.24.222.130:9000/api/";
    
    @Autowired
    Main.ProjectRepository prijRepo;

    @Autowired
    Main.UserRepository userRepo;
    
    @Autowired
    Main.IssueRepository issueRepo;

    @Autowired
    Main.SonarPushRepository sonarRepo;

    @Autowired
    Main.EmailRepository emailRepo;

	private WebJsonReader jsonReader = new WebJsonReader();
    
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
        SonarPush sonarPush = getNewPush(user, project);
        
        String sonarProjectName = project.getSonarName();
        String complexityTechDebtUrl = apiUrl + "resources/index?resource="+sonarProjectName+"&metrics=complexity,class_complexity,file_complexity,function_complexity,sqale_index,sqale_debt_ratio&format=json";
        String issuesUrl = apiUrl + "issues/search?statuses=OPEN&projects="+sonarProjectName;
        
        updatePushProperties(complexityTechDebtUrl, sonarPush);
        sonarRepo.save(sonarPush);
        List<Issue> issueList = saveIssues(issuesUrl, sonarPush);
        log.info(issueList.size() + " issues found");
        new CalculateSalary(sonarPush, issueList);
        return "Done";
	}

    /**
     * Creates a new sonarpush, sets user, project and date
     * @param user
     * @param project
     * @return newly created sonarpush
     */
	private SonarPush getNewPush(User user, Project project) {
        SonarPush sonarPush = SonarPush.Create();
        sonarPush.setUser(user);
        sonarPush.setProject(project);
        java.util.Date newDate = new Date();
        Timestamp param = new Timestamp(newDate.getTime());
        sonarPush.setDate(param);	
        return sonarPush;
	}

    /**
     * Goes to given url, gets the issues from that page and adds them to the sonarpush
     * @param url
     * @param sonarPush
     * @return sonarpush added with all the issues
     */
    public List<Issue> saveIssues(String url, SonarPush sonarPush){
        List<Issue> issueList = new ArrayList<Issue>();
        try {
            JSONObject jobject = jsonReader.readJson(url);
            String paramPageSize = "pageSize=" + jobject.get("total").toString();
            JSONArray issues = jsonReader.readJsonComponent(url + "&" + paramPageSize, "issues");
            for (int i=0; i < issues.size(); i++) {
                JSONObject issue = (JSONObject) issues.get(i);
                if(issue.get("author").toString().equals(sonarPush.getUser().getEmail())){
                    String debt = "";
                    if (issue.keySet().contains("debt"))
                        debt = issue.get("debt").toString();

                    Issue newIssue = new Issue(issue.get("severity").toString(), 
                    						   issue.get("component").toString(), 
                    						   issue.get("message").toString(), 
                    						   debt, sonarPush);
                    issueList.add(newIssue);
                    issueRepo.save(newIssue);
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return issueList;
    }

    public void updatePushProperties(String url, SonarPush sonarPush){
        try {           
            JSONArray array = jsonReader.readJsonArray(url);            
            for(int i = 0; i < array.size(); i++){
                JSONObject object = (JSONObject) array.get(i);
                JSONArray ar = (JSONArray) object.get("msr");
                for(Object msr : ar){
                    JSONObject obj = (JSONObject) msr;
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
        } catch(ParseException e) {
            e.printStackTrace();
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}
