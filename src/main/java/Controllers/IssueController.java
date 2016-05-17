package Controllers;

import Main.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
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
	public String updateSonar() {
        SonarPush sonarPush = new SonarPush();
        sonarPush.setUser(userRepo.getOne(1));

        //1 is devcubes 2 is gamification
        sonarPush.setProject(prijRepo.getOne(1));

        java.util.Date newDate = new Date();
        Timestamp param = new Timestamp(newDate.getTime());
        log.info(String.valueOf(param));

        sonarPush.setDate(param);

        String sonarPushName = sonarPush.getProject().getSonarName();
        String issuesUrl = "http://145.24.222.130:9000/api/issues/search?statuses=OPEN&projectKeys="+sonarPushName+"";
        String complexityTechDebtUrl = "http://145.24.222.130:9000/api/resources/index?resource="+sonarPushName+"&metrics=complexity,class_complexity,file_complexity,function_complexity,sqale_index,sqale_debt_ratio&format=json";

        getComplexityAndTechnicalDebt(complexityTechDebtUrl, sonarPush);
        //sonarRepo.save(sonarPush);
        saveIssues(issuesUrl, userRepo.getOne(1), sonarPush);

        // Call script van harmen voor cijfer.
        // De logic hier achter wordt: haal de laatste sonar push van dit project op, van dat project bijv: getComplexity, en als die lager is dan dat deze push is, heb je het verbeterd! good job :)
        new CalculateSalary(sonarPush);
        return "Done";
	}

    public void saveIssues(String url, User user, SonarPush sonarPush){
        try {
            JSONObject jobject = jsonReader.readJson(url);
            //String paramPageSize = "pageSize=" + jobject.get("total").toString();
            JSONArray issues = jsonReader.readJsonComponent(url + "&" + "3", "issues");

            for (int i=0; i < issues.size(); i++) {
                JSONObject issue = (JSONObject) issues.get(i);

                if(issue.get("author").toString().equals(user.getEmail())){
                    String debt = "";
                    if (issue.keySet().contains("debt"))
                        debt = issue.get("debt").toString();

                    // if issue.key(flow) == bestaand

                    Issue newIssue = new Issue(issue.get("severity").toString(), issue.get("component").toString(), issue.get("message").toString(), debt, sonarPush);
                    //issueRepo.save(newIssue);
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void getComplexityAndTechnicalDebt(String url, SonarPush sonarPush){
        JSONParser parser = new JSONParser();
        try {
            URL oracle = new URL(url); // URL to Parse
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));

            JSONArray array = (JSONArray) parser.parse(in.readLine());

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
            in.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
