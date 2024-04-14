// SavingAccount.java
// Represents a saving bank account, subclass of Account

public class SavingAccount extends Account
{
    static private double interestRate = 0.001; // interest rate 0.1% per annum

    // SavingAccount constructor
    public SavingAccount( int theAccountNumber, String thePIN, double theAvailableBalance, double theTotalBalance )
    {
        super( theAccountNumber, thePIN, theAvailableBalance, theTotalBalance );
    } // end SavingAccount constructor

    // return interest rate
    public static double getInterestRate()
    {
        return interestRate;
    } // end method getInterestRate

    // set interest rate
    public static void setInterestRate( double newInterestRate )
    {
        interestRate = newInterestRate;
    } // end method setInterestRate
}
