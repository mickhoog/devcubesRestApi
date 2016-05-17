package Main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by Harmen on 12-5-2016.
 */
public class CalculateSalary {
    private static final Logger log = LoggerFactory.getLogger(CalculateSalary.class);

    public CalculateSalary(SonarPush sonarPush) {
        float s = getSalary(sonarPush);
        log.info(String.valueOf(s));

        log.info("getTechnicalDebt: " + String.valueOf(sonarPush.getTechnicalDebt()));
        log.info("getIssues: " + String.valueOf(sonarPush.getIssues()));
        log.info("getClassComplexity: " + String.valueOf(sonarPush.getClassComplexity()));
        log.info(("getFileComplexity: " + String.valueOf(sonarPush.getFileComplexity())));
        log.info("getFunctionComplexity: " + String.valueOf(sonarPush.getFunctionComplexity()));
        log.info("getOverallComplexity: " + String.valueOf(sonarPush.getOverallComplexity()));
        // Get user id
        // roep dan aan gameinfocontroller changeInformation

        //http://localhost:8080/updatesonardata
    }
    // Ophalen van Code Coverage, Duplications, Code Complexity, Issue, Technical Debt -> BV: [[99%], [4,6,8], [1=3,2=4,4=15,6=3], [2,4,2,55], [2d5h]]

    public float getSalary(SonarPush sonarPush){
        float salary = (2000 * productivity(1.0f)) *
                (calculateComplexity(sonarPush.getOverallComplexity(), sonarPush.getClassComplexity(), sonarPush.getFileComplexity(), sonarPush.getFunctionComplexity())
                        * /*calculateCoverage() * calculateDuplication()
                        * * */  calculateTechnicalDebt(sonarPush.getTechnicalDebt())
                        * calculateViolation(sonarPush.getIssues()))
                + getBonus(500);
        return salary;
    }

    private float getBonus(int randomBonus){
        float bonus = randomBonus;

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

    private float calculateComplexity(Double overallComplexity, Double classComplexity, Double fileComplexity, Double functionComplexity){
        float complexityProcent = 1;

        return complexityProcent;
    }

    private float calculateViolation(List<Issue> issues){
        float violationProcent = 1;

        return violationProcent;
    }

    private float calculateTechnicalDebt(Double technicalDebt){
        float debtProcent = 1;

        return debtProcent;
    }

}
