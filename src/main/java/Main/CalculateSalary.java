package Main;

import Controllers.GameInfoController;

import java.util.Random;

/**
 * Created by Harmen on 12-5-2016.
 */
public class CalculateSalary{

    public void CalculateSalary(Object sonarPush){
        float salary = (getBasicSalary(2) * productivity()) * (calculateComplexity() * /*calculateCoverage() **/ calculateDuplication() * calculateTechnicalDebt() * calculateViolation()) + getBonus();

        GameInfoController gameInfoController = new GameInfoController();
        /*gameInfoController.changeInformation(id, money, add, salary);*/
        //return salary;
    }

    // Gebaseerd op je bv pc-level
    private float getBasicSalary(int level){
        float basicSalary = level * 1000;

        return basicSalary;
    }

    // Random bonus
    private float getBonus(){
        Random random = new Random();
        return random.nextInt(500);
    }

    // Productiviteit modifier
    private float productivity(){
        float productivity = 0;

        return productivity;
    }

    /*
    private float calculateCoverage(){
        float coverageProcent = 0;

        return coverageProcent;
    }
*/
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
