// ATM.java
// Represents an automated teller machine

import java.awt.AWTException;

public class ATM
{
    // constants corresponding to main menu options
    private static final int BALANCE_INQUIRY = 1;
    private static final int WITHDRAWAL = 2;
    private static final int TRANSFER = 3;
    private static final int EXIT = 4;
    private final Screen screen; // ATM's screen
    private final Keypad keypad; // ATM's keypad
    private final InputHandler inputHandler;
    private final CashDispenser cashDispenser; // ATM's cash dispenser
    private final BankDatabase bankDatabase; // account information database
    private boolean userAuthenticated; // whether user is authenticated
    private int currentAccountNumber; // current user's account number
    private CardSlot cardSlot;

    // no-argument ATM constructor initializes instance variables
    public ATM()
    {
        userAuthenticated = false; // user is not authenticated to start
        currentAccountNumber = 0; // no current account number to start
        keypad = new Keypad(); // create keypad
        inputHandler = keypad.getInputHandler();
        screen = new Screen( inputHandler ); // create screen
        cashDispenser = new CashDispenser(); // create cash dispenser
        bankDatabase = new BankDatabase(); // create acct info database
        cardSlot = new CardSlot();
    } // end no-argument ATM constructor

    // start ATM
    public void run()
    {
        // welcome and authenticate user; perform transactions
        while ( true ) {
            // loop while user is not yet authenticated
            while ( !userAuthenticated ) {
                try {
                    screen.displayView( Screen.View.WELCOME_VIEW );
                    try {
                        while ( inputHandler.getSignal() == InputHandler.Signal.PENDING ) {
                            screen.pause( 250 );
                        } // end while
                        if ( inputHandler.getSignal() == InputHandler.Signal.INSERT_CARD ) {
                            cardSlot.setHasCard( inputHandler.getBoolBuffer() );
                            inputHandler.reset();
                        } // end if
                    } // end try
                    catch ( Exception exception ) {
                        screen.displayMessage( "Exiting system..." );
                        screen.pause();
                        return; // return to main menu because user canceled
                    } // end catch
                } catch ( AWTException exception ) {
                    screen.displayErrorView();
                }
                authenticateUser(); // authenticate user
            } // end while

            if ( cardSlot.hasCard() && cardSlot.checkCardValidity() ) {
                performTransactions(); // user is now authenticated
                userAuthenticated = false; // reset before next ATM session
                currentAccountNumber = 0; // reset before next ATM session
                screen.displayMessage( "Thank you! Goodbye!" );
                screen.pause();
            }
            else
            {
                String[] messages = { "Cannot recognize inserted card.", "Please contact our customer service!" };
                screen.displayMessage( messages );
                screen.pause( 4000  );
            }
        } // end while
    } // end method run

    // attempts to authenticate user against database
    private void authenticateUser()
    {
        int accountNumber = 0;
        try {
            screen.displayView( Screen.View.PROMPT_ACCOUNT_NUMBER_VIEW );
        } catch ( AWTException exception ) {
            screen.displayErrorView();
        }
        try {
            while ( inputHandler.getSignal() == InputHandler.Signal.PENDING ) {
                screen.pause( 250 );
            }
            if ( inputHandler.getSignal() == InputHandler.Signal.PROMPT_ACCOUNT_NUMBER ) {
                accountNumber = Integer.parseInt( inputHandler.getBuffer() );
                inputHandler.reset();
            } else {
                screen.displayView( Screen.View.ERROR_VIEW );
                screen.pause();
                return;
            }
        } catch ( Exception exception ) {
            screen.displayMessage( "Invalid input." );
            screen.pause();
        }

        String pin = null;
        try {
            screen.displayView( Screen.View.PROMPT_PIN_VIEW );
        } catch ( AWTException exception ) {
            screen.displayErrorView();
        }
        try {
            while ( inputHandler.getSignal() == InputHandler.Signal.PENDING ) {
                screen.pause( 250 );
            }
            if ( inputHandler.getSignal() == InputHandler.Signal.PROMPT_PIN ) {
                pin = inputHandler.getBuffer();
                inputHandler.reset();
            } else {
                screen.displayView( Screen.View.ERROR_VIEW );
                screen.pause();
                return;
            }
        } catch ( Exception exception ) {
            screen.displayMessage( "Invalid input." );
            screen.pause();
        } // end catch

        // set userAuthenticated to boolean value returned by database
        userAuthenticated =
                bankDatabase.authenticateUser( accountNumber, pin );

        // check whether authentication succeeded
        if ( userAuthenticated ) {
            currentAccountNumber = accountNumber; // save user's account #
        } // end if
        else {
            screen.displayMessage(
                    "Invalid account number or PIN. Please try again." );
            screen.pause();
        }
    } // end method authenticateUser

    // display the main menu and perform transactions
    private void performTransactions()
    {
        // local variable to store transaction currently being processed
        Transaction currentTransaction;

        boolean userExited = false; // user has not chosen to exit

        // loop while user has not chosen option to exit system
        while ( !userExited ) {
            // show main menu and get user selection
            int mainMenuSelection = displayMainMenu();

            // decide how to proceed based on user's menu selection
            switch ( mainMenuSelection ) {
                // user chose to perform one of three transaction types
                case BALANCE_INQUIRY:
                case WITHDRAWAL:
                case TRANSFER:
                    // initialize as new object of chosen type
                    currentTransaction =
                            createTransaction( mainMenuSelection );
                    currentTransaction.execute(); // execute transaction
                    userExited = currentTransaction.isExit();
                    break;
                case EXIT: // user chose to terminate session
                    try {
                        screen.displayView( Screen.View.TAKE_YOUR_CARD_VIEW );
                    } catch ( AWTException exception ) {
                        screen.displayErrorView();
                        screen.pause();
                    }
                    try {
                        while ( inputHandler.getSignal() == InputHandler.Signal.PENDING ) {
                            screen.pause( 250 );
                        } // end while
                        if ( inputHandler.getSignal() == InputHandler.Signal.TAKE_CARD ) {
                            cardSlot.setHasCard( !inputHandler.getBoolBuffer() );
                            inputHandler.reset();
                        } // end if
                    } // end try
                    catch ( Exception exception ) {
                        screen.displayErrorView();
                        screen.pause();
                        String[] messages = { "Please contact customer service", "if you encounter any issues." };
                        screen.displayMessage( messages );
                        screen.pause();
                        return;
                    } // end catch
                    screen.displayMessage( "Exiting the system..." );
                    screen.pause();
                    userExited = true; // this ATM session should end
                    break;
                default: // user did not enter an integer from 1-4
                    screen.displayMessage(
                            "You did not enter a valid selection. Try again." );
                    screen.pause();
                    break;
            } // end switch
        } // end while
    } // end method performTransactions

    // display the main menu and return an input selection
    private int displayMainMenu()
    {
        try {
            screen.displayView( Screen.View.MENU_VIEW );
        } catch ( AWTException exception ) {
            screen.displayErrorView();
        }
        int input = 0;
        try {
            while ( inputHandler.getSignal() == InputHandler.Signal.PENDING ) {
                screen.pause( 250 );
            }
            if ( inputHandler.getSignal() == InputHandler.Signal.PROMPT_MENU_OPTION ) {
                input = Integer.parseInt( inputHandler.getBuffer() );
            } else {
                screen.displayView( Screen.View.ERROR_VIEW );
            }
        } catch ( Exception e ) {
            screen.displayMessage( "Invalid input" );
            screen.pause();
        }

        return input; // return user's selection
    } // end method displayMainMenu

    // return object of specified Transaction subclass
    private Transaction createTransaction( int type )
    {
        Transaction temp = null; // temporary Transaction variable

        // determine which type of Transaction to create
        switch ( type ) {
            case BALANCE_INQUIRY: // create new BalanceInquiry transaction
                temp = new BalanceInquiry(
                        currentAccountNumber, screen, bankDatabase );
                break;
            case WITHDRAWAL: // create new Withdrawal transaction
                temp = new Withdrawal( currentAccountNumber, screen,
                        bankDatabase, keypad, cashDispenser, cardSlot );
                break;
            case TRANSFER: // create new Transfer transaction
                temp = new Transfer( currentAccountNumber, screen,
                        bankDatabase, keypad );
                break;
        } // end switch

        return temp; // return the newly created object
    } // end method createTransaction
} // end class ATM