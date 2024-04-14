// Withdrawal.java
// Represents a withdrawal ATM transaction

import java.awt.AWTException;

public class Withdrawal extends Transaction
{
    // constant corresponding to menu option to cancel
    private final static int CANCELED = 12;
    private final Keypad keypad; // reference to keypad
    private final CashDispenser cashDispenser; // reference to cash dispenser
    private final InputHandler inputHandler;
    private final CardSlot cardSlot;
    private boolean isExit = false;

    // Withdrawal constructor
    public Withdrawal( int userAccountNumber, Screen atmScreen,
                       BankDatabase atmBankDatabase, Keypad atmKeypad,
                       CashDispenser atmCashDispenser, CardSlot atmCardSlot )
    {
        // initialize superclass variables
        super( userAccountNumber, atmScreen, atmBankDatabase );

        // initialize references to keypad and cash dispenser
        cardSlot = atmCardSlot;
        keypad = atmKeypad;
        cashDispenser = atmCashDispenser;
        inputHandler = keypad.getInputHandler();
    } // end Withdrawal constructor

    // perform transaction
    @Override
    public void execute()
    {
        boolean cashDispensed = false; // cash was not dispensed yet
        double availableBalance; // amount available for withdrawal

        // get references to bank database and screen
        BankDatabase bankDatabase = getBankDatabase();
        Screen screen = getScreen();

        // loop until cash is dispensed or the user cancels
        do {
            // obtain a chosen withdrawal amount from the user
            // amount to withdraw
            int amount;
            try {
                amount = displayMenuOfAmounts();
            } catch ( AWTException exception ) {
                screen.displayErrorView();
                screen.pause();
                return;
            }
            // check whether user chose a withdrawal amount or canceled
            if ( amount != CANCELED ) {
                // get available balance of account involved
                availableBalance =
                        bankDatabase.getAvailableBalance( getAccountNumber() );

                // check whether the user has enough money in the account
                if ( amount <= availableBalance ) {
                    // check whether the cash dispenser has enough money
                    if ( cashDispenser.isSufficientCashAvailable( amount ) ) {

                        screen.displayPromptConfirmationView( String.format( "Withdraw $%d", amount ) );
                        boolean isConfirm = false;
                        try {
                            while ( inputHandler.getSignal() == InputHandler.Signal.PENDING ) {
                                screen.pause( 250 );
                            } // end while
                            if ( inputHandler.getSignal() == InputHandler.Signal.PROMPT_CONFIRMATION ) {
                                isConfirm = inputHandler.getBoolBuffer();
                                inputHandler.reset();
                            } // end if
                        } // end try
                        catch ( Exception exception ) {
                            screen.displayMessage( "Canceling transaction..." );
                            screen.pause();
                            return; // return to main menu because user canceled
                        } // end catch
                        if ( isConfirm ) {
                            // update the account involved to reflect withdrawal
                            bankDatabase.debit( getAccountNumber(), amount );

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

                            cashDispenser.dispenseCash( amount ); // dispense cash
                            cashDispensed = true; // cash was dispensed

                            // instruct user to take cash
                            try {
                                screen.displayView( Screen.View.TAKE_YOUR_CASH_VIEW );
                            } catch ( AWTException exception ) {
                                screen.displayErrorView();
                                screen.pause();
                            }
                            try {
                                while ( inputHandler.getSignal() == InputHandler.Signal.PENDING ) {
                                    screen.pause( 250 );
                                } // end while
                                if ( inputHandler.getSignal() == InputHandler.Signal.TAKE_CASH ) {
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
                            done();
                        } // end if
                        else {
                            screen.displayMessage( "Canceling transaction..." );
                            screen.pause();
                            return; // return to main menu because user canceled
                        } // end else
                    } // end if
                    else // cash dispenser does not have enough cash
                    {
                        String[] messages = { "Insufficient cash available in the ATM.",
                                "Please choose a smaller amount." };
                        screen.displayMessage( messages );
                        screen.pause( 3000 );
                    }
                } // end if
                else // not enough money available in user's account
                {
                    String[] messages = { "Insufficient funds in your account.",
                            "Please choose a smaller amount." };
                    screen.displayMessage( messages );
                    screen.pause();
                } // end else

            } // end if
            else // user chose cancel menu option
            {
                screen.displayMessage( "Canceling transaction..." );
                screen.pause();
                return; // return to main menu because user canceled
            } // end else
        } while ( !cashDispensed ); // end do-while
    } // end method execute

    @Override
    public void done()
    {
        isExit = true;
    }

    @Override
    public boolean isExit()
    {
        return isExit;
    }

    // display a menu of withdrawal amounts and the option to cancel;
    // return the chosen amount or 0 if the user chooses to cancel
    private int displayMenuOfAmounts() throws AWTException
    {
        int userChoice = 0; // local variable to store return value

        Screen screen = getScreen(); // get screen reference

        // loop while no valid choice has been made
        while ( userChoice == 0 ) {
            // display the menu
            screen.displayView( Screen.View.WITHDRAWAL_MENU_VIEW );

            int input = 0;
            try {
                while ( inputHandler.getSignal() == InputHandler.Signal.PENDING ) {
                    screen.pause( 250 );
                }
                if ( inputHandler.getSignal() == InputHandler.Signal.PROMPT_WITHDRAWAL_MENU_OPTION ) {
                    input = Integer.parseInt( inputHandler.getBuffer() );
                    inputHandler.reset();
                }
            } // end try
            catch ( Exception exception ) {
                screen.displayMessage( "Invalid input." );
                screen.pause();
                return CANCELED;
            } // end catch

            // determine how to proceed based on the input value
            switch ( input ) {
                // if the user chose a withdrawal amount
                // (i.e., chose option 1-12), return option 100 or CANCEL
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                    userChoice = input * 100; // withdrawal amount = option x100
                    break;
                case 11:
                    userChoice = promptForWithdrawalAmount();
                    break;
                case CANCELED: // the user chose to cancel
                    userChoice = CANCELED; // save user's choice
                    break;
                default: // the user did not enter a value from 1-6
                    screen.displayMessage(
                            "Invalid selection. Try again." );
                    screen.pause();
            } // end switch
        } // end while

        return userChoice; // return withdrawal amount or CANCELED
    } // end method displayMenuOfAmounts

    // prompt user to enter a withdrawal amount in dollars
    private int promptForWithdrawalAmount()
    {
        Screen screen = getScreen(); // get reference to screen

        // display the prompt
        try {
            screen.displayView( Screen.View.PROMPT_WITHDRAWAL_AMOUNT_VIEW );
        } catch ( AWTException exception ) {
            screen.displayErrorView();
            screen.pause();
            return CANCELED;
        }
        int input = 0;
        try {
            while ( inputHandler.getSignal() == InputHandler.Signal.PENDING ) {
                screen.pause( 250 );
            }
            if ( inputHandler.getSignal() == InputHandler.Signal.PROMPT_WITHDRAWAL_AMOUNT ) {
                input = Integer.parseInt( inputHandler.getBuffer() );
                inputHandler.reset();
            }
        } // end try
        catch ( Exception exception ) {
            screen.displayMessage( "Invalid amount." );
            screen.pause();
            return CANCELED;
        } // end catch

        // check whether the user canceled or entered a valid amount
        if ( input != 0 && ( input % 100 == 0 ) ) {
            return input; // return dollar amount
        } else {
            screen.displayMessage( "Invalid amount. Please enter multiple of 100." );
            screen.pause();
            return CANCELED;
        }
    } // end method promptForDepositAmount
} // end class Withdrawal