// Transfer.java
// Represents a transfer ATM transaction

import java.awt.AWTException;

public class Transfer extends Transaction
{
    // maximum transfer amount (1 million) per transaction via atm
    private static final double MAX_TRANSFER_AMOUNT = 1_000_000;
    private final Keypad keypad; // atm's Keypad
    private final InputHandler inputHandler;
    private final boolean isExit = false;

    // Transfer constructor
    Transfer( int userAccountNumber, Screen atmScreen, BankDatabase atmBankDatabase, Keypad keypad )
    {
        super( userAccountNumber, atmScreen, atmBankDatabase );
        this.keypad = keypad;
        this.inputHandler = keypad.getInputHandler();
    } // end Transfer constructor

    // perform transfer transaction
    @Override
    public void execute()
    {
        BankDatabase bankDatabase = getBankDatabase();
        Screen screen = getScreen();

        try {
            screen.displayView( Screen.View.PROMPT_RECIPIENT_ACCOUNT_NUMBER_VIEW );
        } catch ( AWTException exception ) {
            screen.displayErrorView();
            screen.pause();
            screen.displayMessage( "Canceling transaction..." );
            screen.pause();
            return;
        }
        int recipientAccountNumber;
        try {
            while ( inputHandler.getSignal() == InputHandler.Signal.PENDING ) {
                screen.pause( 250 );
            }
            if ( inputHandler.getSignal() == InputHandler.Signal.PROMPT_TRANSFER_RECIPIENTS_ACCOUNT_NUMBER ) {
                recipientAccountNumber = Integer.parseInt( inputHandler.getBuffer() );
                inputHandler.reset();
            } else {
                screen.displayView( Screen.View.ERROR_VIEW );
                screen.pause();
                screen.displayMessage( "Canceling transaction..." );
                screen.pause();
                return;
            }
        } // end try
        catch ( Exception e ) {
            screen.displayMessage( "Invalid account number." );
            screen.pause();
            screen.displayMessage( "Canceling transaction..." );
            screen.pause();
            return;
        } // end catch

        // user cannot transfer to himself
        if ( recipientAccountNumber == getAccountNumber() ) {
            screen.displayMessage( "Cannot transfer to yourself." );
            screen.pause();
            screen.displayMessage( "Canceling transaction..." );
            screen.pause();
            return;
        }

        double availableBalance = bankDatabase.getAvailableBalance( getAccountNumber() );

        // check if account exists
        if ( bankDatabase.isValidAccountNumber( recipientAccountNumber ) ) {
            try {
                screen.displayView( Screen.View.PROMPT_TRANSFER_AMOUNT_VIEW );
            } catch ( AWTException exception ) {
                screen.displayErrorView();
                screen.pause();
                screen.displayMessage( "Canceling transaction..." );
                screen.pause();
                return;
            }
            double transferAmount;
            try {
                while ( inputHandler.getSignal() == InputHandler.Signal.PENDING ) {
                    screen.pause( 250 );
                }
                if ( inputHandler.getSignal() == InputHandler.Signal.PROMPT_TRANSFER_AMOUNT ) {
                    transferAmount = Double.parseDouble( inputHandler.getBuffer() );
                    inputHandler.reset();
                } else {
                    screen.displayView( Screen.View.ERROR_VIEW );
                    screen.pause();
                    screen.displayMessage( "Canceling transaction..." );
                    screen.pause();
                    return;
                }
            } // end try
            catch ( Exception e ) {
                screen.displayMessage( "Invalid amount." );
                screen.pause();
                screen.displayMessage( "Canceling transaction..." );
                screen.pause();
                return;
            } // end catch

            // correct transfer amount to 2 decimal places
            transferAmount = ( double ) Math.round( transferAmount * 100 ) / 100;

            String[] operationMessages = { String.format( "Transfer $%.2f to",
                    transferAmount ), String.format( "Recipient with account number #%d", recipientAccountNumber ) };
            screen.displayPromptConfirmationView( operationMessages );
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

            // check whether the user account can transfer selected amount
            if ( transferAmount <= availableBalance && transferAmount <= MAX_TRANSFER_AMOUNT && isConfirm ) {
                bankDatabase.debit( getAccountNumber(), transferAmount );
                bankDatabase.credit( recipientAccountNumber, transferAmount );

                // update available balance and get total balance post-transfer
                availableBalance = bankDatabase.getAvailableBalance( getAccountNumber() );
                double totalBalance = bankDatabase.getTotalBalance( getAccountNumber() );

                // display the balance information on the screen
                String[] messages = { "Balance Information:",
                        " - Available balance: " + screen.dollarAmountToString( availableBalance ),
                        " - Total balance:        " + screen.dollarAmountToString( totalBalance ) };

                screen.displayMessage( messages );
                screen.pause( 3000 );
            } // end if
            else // not enough money available in user's account
            {
                String[] messages;
                if ( transferAmount > availableBalance ) {
                    messages = new String[] { "Insufficient funds in your account.",
                            "Please choose a smaller amount." };
                } // end if
                else {
                    messages = new String[] { "Maximum transfer amount exceeded.",
                            "Please choose a smaller amount smaller than one million" };
                } // end else
                screen.displayMessage( messages );
                screen.pause();
            } // end else
        } // end if
        else {
            screen.displayMessage( "Invalid account number." );
            screen.pause();
        } // end else
    } // end method execute

    @Override
    public void done()
    {

    }

    @Override
    public boolean isExit()
    {
        return isExit;
    }
}