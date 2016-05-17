package Main;

/**
 * Created by Harmen on 12-5-2016.
 */
public class CalculateSalary {
    public CalculateSalary(SonarPush sonarPush) {

        //Get user id
        // roep dan aan gameinfocontroller changeInformation


    }
    // Ophalen van Code Coverage, Duplications, Code Complexity, Issue, Technical Debt -> BV: [[99%], [4,6,8], [1=3,2=4,4=15,6=3], [2,4,2,55], [2d5h]]

    public float CalculateSalary(SonarPush sonarPush){
        float salary = (2000 * productivity()) * (calculateComplexity() * calculateCoverage() * calculateDuplication() * calculateTechnicalDebt() * calculateViolation()) + getBonus();

        return salary;
    }

    private float getBonus(){
        float bonus = 0;

        return bonus;
    }

    private float productivity(){
        float productivity = 0;

        return productivity;
    }

    private float calculateCoverage(){
        float coverageProcent = 0;

        return coverageProcent;
    }

    private float calculateDuplication(){
        float duplicationProcent = 0;

        return duplicationProcent;
    }

    private float calculateComplexity(){
        float complexityProcent = 0;

        return complexityProcent;
    }

    private float calculateViolation(){
        float violationProcent = 0;

        return violationProcent;
    }

    private float calculateTechnicalDebt(){
        float debtProcent = 0;

        return debtProcent;
    }

}
