package Main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Random;

public class CalculateSalary {
    private static final Logger log = LoggerFactory.getLogger(CalculateSalary.class);

    @Autowired
    private IssueRepository repo;

    @Autowired
    private EmailRepository emailRepo;

    public CalculateSalary(SonarPush sonarPush, List<Issue> issues) {
        HttpConnector httpConnector = new HttpConnector("user", "c1cc6367-5c10-4d76-955e-d58678eeb1f8");

        log.info(String.valueOf(issues));

        double salary = Math.floor(getSalary(sonarPush, issues));
        log.info("Salary: " + String.valueOf(salary));
        // Get user id
        // roep dan aan gameinfocontroller changeInformation

        //http://localhost:8080/updatesonardata?project=my:DevCube&useremail=sammeyer1994@hotmail.com

        httpConnector.sendPost("http://localhost:8080/gameinfo/"+sonarPush.getUser().getId()+"/money/add/"+ salary, "", true);

        httpConnector.sendPost("http://localhost:8080/email/new?", "salary="+salary+"&userId=" + sonarPush.getUser().getId(), true);

        httpConnector.sendPost("http://localhost:8080/sonarpush/setSalary?", "salary="+salary+"&id=" + sonarPush.getId(), true);

    }

    public double getSalary(SonarPush sonarPush, List<Issue> issues){
        Double classComplexity = sonarPush.getClassComplexity();
        Double fileComplexity = sonarPush.getFileComplexity();
        Double functionComplexity = sonarPush.getFunctionComplexity();

        Double technicalDebt = sonarPush.getTechnicalDebt();

        double salaryModifiers = (calculateComplexity(classComplexity, fileComplexity, functionComplexity)
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

    private float calculateComplexity(Double classComplexity, Double fileComplexity, Double functionComplexity){
        float complexityProcent = 1;
        float functionComplexityProcent = 1;
        float classComplexityProcent = 1;
        float fileComplexityProcent = 1;

        // TODO Duplicate code weghalen

        if (functionComplexity <= 2){
            functionComplexityProcent = 1;
        }else if(functionComplexity <= 4){
            functionComplexityProcent = 0.95f;
        }else if (fileComplexity >= 5){
            functionComplexityProcent = 0.9f;
        }

        if (classComplexity <= 2){
            classComplexityProcent = 1;
        }else if(classComplexity <= 4){
            classComplexityProcent = 0.95f;
        }else if (classComplexity >= 5){
            classComplexityProcent = 0.9f;
        }

        if (fileComplexity <= 2){
            fileComplexityProcent = 1;
        }else if(fileComplexity <= 4){
            fileComplexityProcent = 0.95f;
        }else if (fileComplexity >= 5){
            fileComplexityProcent = 0.9f;
        }

        complexityProcent = functionComplexityProcent * classComplexityProcent * fileComplexityProcent;

        return complexityProcent;
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
