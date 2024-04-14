// BalanceInquiry.java
// Represents a balance inquiry ATM transaction

public class BalanceInquiry extends Transaction
{
    // BalanceInquiry constructor
    public BalanceInquiry( int userAccountNumber, Screen atmScreen,
                           BankDatabase atmBankDatabase )
    {
        super( userAccountNumber, atmScreen, atmBankDatabase );
    } // end BalanceInquiry constructor

    // performs the transaction
    public void execute()
    {
        // get references to bank database and screen
        BankDatabase bankDatabase = getBankDatabase();
        Screen screen = getScreen();

        // get the available balance for the account involved
        double availableBalance =
                bankDatabase.getAvailableBalance( getAccountNumber() );

        // get the total balance for the account involved
        double totalBalance =
                bankDatabase.getTotalBalance( getAccountNumber() );

        // display the balance information on the screen
        String[] messages = { "Balance Information:",
                " - Available balance: " + screen.dollarAmountToString( availableBalance ),
                " - Total balance:        " + screen.dollarAmountToString( totalBalance ) };

        screen.displayMessage( messages );
        screen.pause( 3000 );
    } // end method execute

    @Override
    public void done()
    {
    }

    @Override
    public boolean isExit()
    {
        return false;
    }
} // end class BalanceInquiry