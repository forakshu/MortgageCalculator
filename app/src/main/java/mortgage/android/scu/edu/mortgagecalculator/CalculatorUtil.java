package mortgage.android.scu.edu.mortgagecalculator;

//This class contains method to calculate the monthly payment
public class CalculatorUtil {

    //Called when interest is anything except 0.0%
    public static double calculateMortgageWithInterest(double principalAmount, double monInt, double numOfMon, double tax) {

        double loanAmount;

        loanAmount = (principalAmount * ((monInt) / (1 - (1 / (Math.pow(1 + monInt, numOfMon)))))) + tax;

        return loanAmount;

    }

    //Called when interest is 0.0%
    public static double calculateMortgageWithoutInterest(double principalAmount, double numOfMon, double tax) {

        double loanAmount;

        loanAmount = (principalAmount / numOfMon) + tax;

        return loanAmount;

    }
}
