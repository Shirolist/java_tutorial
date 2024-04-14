// BankDatabase.java
// Represents the bank account information database 

public class BankDatabase
{
    private final Account[] accounts; // array of Accounts

    // no-argument BankDatabase constructor initializes accounts
    public BankDatabase()
    {
        accounts = new Account[4]; // just 3 accounts for testing
        accounts[0] = new SavingAccount( 1, "0000", 10000.0, 12000.0 );
        accounts[1] = new SavingAccount( 2, "0246", 20000.0, 22000.0 );
        accounts[2] = new ChequeAccount( 3, "0369", 15000.0, 17000.0 );
        accounts[3] = new ChequeAccount( 4, "4812", 51000.0, 60000.0 );
    } // end no-argument BankDatabase constructor

    // retrieve Account object containing specified account number
    private Account getAccount( int accountNumber )
    {   
        // loop through accounts searching for matching account number
        for ( Account currentAccount : accounts ) {
            // return current account if match found
            if ( currentAccount.getAccountNumber() == accountNumber )
                return currentAccount;
        } // end for

        return null; // if no matching account was found, return null
    } // end method getAccount

    // check if account exists with an account number
    public boolean isValidAccountNumber( int accountNumber )
    {
        for ( Account currentAccount : accounts ) {
            // return current account if match found
            if ( currentAccount.getAccountNumber() == accountNumber )
                return true;
        } // end for

        return false; // if no matching account was found, return false
    }

    // determine whether user-specified account number and PIN match
    // those of an account in the database
    public boolean authenticateUser( int userAccountNumber, String userPIN )
    {
        // attempt to retrieve the account with the account number
        Account userAccount = getAccount( userAccountNumber );

        // if account exists, return result of Account method validatePIN
        if ( userAccount != null )
            return userAccount.validatePIN( userPIN );
        else
            return false; // account number not found, so return false
    } // end method authenticateUser

    // return available balance of Account with specified account number
    public double getAvailableBalance( int userAccountNumber )
    {
        return getAccount( userAccountNumber ).getAvailableBalance();
    } // end method getAvailableBalance

    // return total balance of Account with specified account number
    public double getTotalBalance( int userAccountNumber )
    {
        return getAccount( userAccountNumber ).getTotalBalance();
    } // end method getTotalBalance

    // credit an amount to Account with specified account number
    public void credit( int userAccountNumber, double amount )
    {
        getAccount( userAccountNumber ).credit( amount );
    } // end method credit

    // debit an amount from of Account with specified account number
    public void debit( int userAccountNumber, double amount )
    {
        getAccount( userAccountNumber ).debit( amount );
    } // end method debit
} // end class BankDatabase