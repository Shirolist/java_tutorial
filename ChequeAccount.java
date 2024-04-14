// ChequeAccount.java
// Represents a cheque bank account, subclass of Account

public class ChequeAccount extends Account
{
    static private double limitPerCheque = 10000;

    // ChequeAccount constructor
    public ChequeAccount( int theAccountNumber, String thePIN, double theAvailableBalance, double theTotalBalance )
    {
        super( theAccountNumber, thePIN, theAvailableBalance, theTotalBalance );
    } // end ChequeAccount constructor

    // return limit per cheque
    public static double getLimitPerCheque()
    {
        return limitPerCheque;
    } // end method getLimitPerCheque

    // set limit per cheque
    public static void setLimitPerCheque( double newLimitPerCheque )
    {
        limitPerCheque = newLimitPerCheque;
    } // end method setLimitPerCheque
}
