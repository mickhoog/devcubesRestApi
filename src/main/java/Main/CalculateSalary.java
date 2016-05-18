package Main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Random;

/**
 * Created by Harmen on 12-5-2016.
 */
public class CalculateSalary {
    private static final Logger log = LoggerFactory.getLogger(CalculateSalary.class);

    @Autowired
    private IssueRepository repo;

    public CalculateSalary(SonarPush sonarPush, List<Issue> issues) {

        log.info(String.valueOf(issues));

        //float s = getSalary(sonarPush);
        //log.info("Salary: " + String.valueOf(s));
        // Get user id
        // roep dan aan gameinfocontroller changeInformation

        //http://localhost:8080/updatesonardata

    }

    public float getSalary(SonarPush sonarPush){
        Double classComplexity = sonarPush.getClassComplexity();
        log.info("classComplexity: " + String.valueOf(classComplexity));
        Double fileComplexity = sonarPush.getFileComplexity();
        log.info("fileComplexity: " + String.valueOf(fileComplexity));
        Double functionComplexity = sonarPush.getFunctionComplexity();
        log.info("functionComplexity: " + String.valueOf(functionComplexity));

        Double technicalDebt = sonarPush.getTechnicalDebt();
        log.info("technicalDebt: " + String.valueOf(technicalDebt));
        List<Issue> issues = sonarPush.getIssues();

        float salaryModifiers = (calculateComplexity(classComplexity, fileComplexity, functionComplexity)
                * calculateTechnicalDebt(technicalDebt)
                * calculateViolation(issues)) /* * calculateCoverage() * calculateDuplication() */  ;

        log.info("salaryModifiers: " + String.valueOf(salaryModifiers));

        float salary = (2000 * productivity(1.2f)) * salaryModifiers + getBonus();

        return salary;
    }

    private float getBonus(){
        Random random = new Random();
        float bonus = 0;

        if (random.nextBoolean()){
            bonus = random.nextInt(250);
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

        log.info("complexityProcent: " + String.valueOf(complexityProcent));

        return complexityProcent;
    }

    private float calculateViolation(List<Issue> issues){
        float violationProcent = 1;

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
