package Main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class CalculateSalary {
    private static final Logger log = LoggerFactory.getLogger(CalculateSalary.class);

    @Autowired
    private IssueRepository repo;

    @Autowired
    private EmailRepository emailRepo;

    private String url = "http://localhost:8080";

    public CalculateSalary(SonarPush sonarPush, List<Issue> issues) {
        HttpConnector httpConnector = new HttpConnector("user", "c1cc6367-5c10-4d76-955e-d58678eeb1f8");

        double salary = Math.floor(getSalary(sonarPush, issues));
        // Get user id
        // roep dan aan gameinfocontroller changeInformation

        httpConnector.sendPost(url + "/gameinfo/"+sonarPush.getUser().getId()+"/money/add/"+ salary, "", true);

        httpConnector.sendPost(url + "/email/new?", "salary="+salary+"&userId=" + sonarPush.getUser().getId(), true);

        httpConnector.sendPost(url + "/sonarpush/setSalary?", "salary="+salary+"&id=" + sonarPush.getId(), true);

    }

    public double getSalary(SonarPush sonarPush, List<Issue> issues){
        Double classComplexity = sonarPush.getClassComplexity();
        Double fileComplexity = sonarPush.getFileComplexity();
        Double functionComplexity = sonarPush.getFunctionComplexity();

        Double technicalDebt = sonarPush.getTechnicalDebt();

        Double[] complexities = {classComplexity, fileComplexity, functionComplexity};

        double salaryModifiers = (calculateComplexity(complexities)
                                * calculateTechnicalDebt(technicalDebt)
                                * calculateViolation(issues)) /* * calculateCoverage() * calculateDuplication() */  ;

        log.info("salaryModifiers: " + String.valueOf(salaryModifiers));

        double salary = (10 *  productivity(1.2f)) * salaryModifiers + getBonus();

        return salary;
    }

    private float getBonus(){
        Random random = new Random();
        float bonus = 0;

        if (random.nextInt(100) < 10){
            bonus = random.nextInt(5);
            log.info("Bonus: " + String.valueOf(bonus));
        }

        return bonus;
    }

    private float productivity(float productivityNumber){
        float productivity = productivityNumber;

        return productivity;
    }
    private float calculateCoverage(){
        float coverageProcent = 1;

        return coverageProcent;
    }

    private float calculateDuplication(){
        float duplicationProcent = 1;

        return duplicationProcent;
    }

    private double calculateComplexity(Double[] complexityArray){
        double completeComplexity = 0;

        for (Double complexity : complexityArray) {
            if(complexity >= 5)
                completeComplexity += 1;
            else{
                if (complexity >= 2)
                    completeComplexity += 0.95;
                else
                    completeComplexity += 0.9;
            }
        }

        return completeComplexity;
    }

    private double calculateViolation(List<Issue> issues){
        double violationProcent = 1;
        float minorIssues = 0;
        float majorIssues = 0;
        float criticalIssues = 0;
        float blockerIssues = 0;


        for (Issue issue: issues){
            switch (issue.getSeverity()){
                case "MINOR":
                    minorIssues++;
                    break;
                case "MAJOR":
                    majorIssues++;
                    break;
                case "CRITICAL":
                    criticalIssues++;
                    break;
                case "BLOCKER":
                    blockerIssues++;
                    break;

            }
        }

        violationProcent = Math.pow(0.7f, blockerIssues) * Math.pow(0.8f, criticalIssues) * Math.pow(0.95f, majorIssues) * Math.pow(0.99f, minorIssues);

        return violationProcent;
    }

    private float calculateTechnicalDebt(Double technicalDebt){
        float debtProcent = 1;

        if (technicalDebt <= 15){
            debtProcent = 1f;
        }else if (technicalDebt <= 60){
            debtProcent = 0.9f;
        }else if (technicalDebt >= 61){
            debtProcent = 0.85f;
        }

        return debtProcent;
    }

}
