// Screen.java
// Represents the screen of the ATM

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Screen
{
    private final JFrame frame;
    private final LayeredContainer layeredContainer;
    private final InputHandler inputHandler;
    private final CardLayout cardLayout;
    private final MainPanel mainPanel;
    private final ResourceManager resourceManager;

    Screen( InputHandler inputHandler )
    {
        this.inputHandler = inputHandler;
        frame = new JFrame( "ATM" );
        cardLayout = new CardLayout();
        mainPanel = new MainPanel( cardLayout );
        resourceManager = new ResourceManager();
        layeredContainer = new LayeredContainer( mainPanel, resourceManager );


        EventQueue.invokeLater( () -> {
            try {
                UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
            } catch ( ClassNotFoundException | InstantiationException |
                    IllegalAccessException | UnsupportedLookAndFeelException ex ) {
                ex.printStackTrace();
            }

            frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
            frame.setLayout( new BorderLayout() );
            frame.setContentPane( layeredContainer );
            frame.setSize( 800, 600 );
            frame.setResizable( false );
            frame.setUndecorated( true );
            frame.setLocationRelativeTo( null );
            frame.setVisible( true );
        } );
    }

    public JFrame getFrame()
    {
        return frame;
    }

    // displays a message
    public void displayMessage( String message )
    {
        ResponseView responseView = new ResponseView( message );
        mainPanel.add( responseView.getView(), View.RESPONSE_VIEW.getViewString() );
        cardLayout.show( mainPanel, View.RESPONSE_VIEW.getViewString() );
    } // end method displayMessage

    public void displayMessage( String[] messages )
    {
        ResponseView responseView = new ResponseView( messages );
        mainPanel.add( responseView.getView(), View.RESPONSE_VIEW.getViewString() );
        cardLayout.show( mainPanel, View.RESPONSE_VIEW.getViewString() );
    } // end method displayMessage

    public String dollarAmountToString( double amount )
    {
        return String.format( "$%,.2f", amount );
    }

    public void displayView( View type ) throws AWTException
    {
        IView view;
        String viewString;
        inputHandler.reset();
        switch ( type ) {
            case WELCOME_VIEW:
                view = new WelcomeView( inputHandler );
                viewString = View.WELCOME_VIEW.getViewString();
                break;
            case PROMPT_ACCOUNT_NUMBER_VIEW:
                view = new PromptAccountNumberView( inputHandler );
                viewString = View.PROMPT_ACCOUNT_NUMBER_VIEW.getViewString();
                break;
            case PROMPT_PIN_VIEW:
                view = new PromptPINView( inputHandler );
                viewString = View.PROMPT_PIN_VIEW.getViewString();
                break;
            case MENU_VIEW:
                view = new MenuView( inputHandler );
                viewString = View.MENU_VIEW.getViewString();
                break;
            case WITHDRAWAL_MENU_VIEW:
                view = new WithdrawalMenuView( inputHandler );
                viewString = View.WITHDRAWAL_MENU_VIEW.getViewString();
                break;
            case PROMPT_WITHDRAWAL_AMOUNT_VIEW:
                view = new PromptWithdrawalAmountView( inputHandler );
                viewString = View.PROMPT_WITHDRAWAL_AMOUNT_VIEW.getViewString();
                break;
            case PROMPT_RECIPIENT_ACCOUNT_NUMBER_VIEW:
                view = new PromptRecipientAccountNumberView( inputHandler );
                viewString = View.PROMPT_RECIPIENT_ACCOUNT_NUMBER_VIEW.getViewString();
                break;
            case PROMPT_TRANSFER_AMOUNT_VIEW:
                view = new PromptTransferAmountView( inputHandler );
                viewString = View.PROMPT_TRANSFER_AMOUNT_VIEW.getViewString();
                break;
            case TAKE_YOUR_CARD_VIEW:
                view = new TakeYourCardView( inputHandler );
                viewString = View.TAKE_YOUR_CARD_VIEW.getViewString();
                break;
            case TAKE_YOUR_CASH_VIEW:
                view = new TakeYourCashView( inputHandler );
                viewString = View.TAKE_YOUR_CASH_VIEW.getViewString();
                break;
            default:
                view = new ResponseView( "ERROR" );
                viewString = View.ERROR_VIEW.getViewString();
        }
        mainPanel.add( view.getView(), viewString );
        cardLayout.show( mainPanel, viewString );

        Robot robot = new Robot();
        pause( 100 );
        robot.keyPress( KeyEvent.VK_TAB );
    }

    public void displayPromptConfirmationView( String operationMessage )
    {
        PromptConfirmationView view = new PromptConfirmationView( operationMessage, inputHandler );
        String viewString = View.PROMPT_CONFORMATION_VIEW.getViewString();

        mainPanel.add( view.getView(), viewString );
        cardLayout.show( mainPanel, viewString );
    }

    public void displayPromptConfirmationView( String[] operationMessage )
    {
        PromptConfirmationView view = new PromptConfirmationView( operationMessage, inputHandler );
        String viewString = View.PROMPT_CONFORMATION_VIEW.getViewString();

        mainPanel.add( view.getView(), viewString );
        cardLayout.show( mainPanel, viewString );
    }

    public void displayErrorView()
    {
        ResponseView view = new ResponseView( "ERROR" );
        String viewString = View.ERROR_VIEW.getViewString();

        mainPanel.add( view.getView(), viewString );
        cardLayout.show( mainPanel, viewString );
    }

    public void pause()
    {
        try {
            Thread.sleep( 1500 );
        } catch ( InterruptedException exception ) {
            Thread.currentThread().interrupt();
        }
    }

    public void pause( int duration )
    {
        try {
            Thread.sleep( duration );
        } catch ( InterruptedException exception ) {
            Thread.currentThread().interrupt();
        }
    }

    public enum View
    {
        WELCOME_VIEW( "View.welcome" ),
        PROMPT_ACCOUNT_NUMBER_VIEW( "View.promptAccountNumber" ),
        PROMPT_PIN_VIEW( "View.promptPIN" ),
        MENU_VIEW( "View.menu" ),
        WITHDRAWAL_MENU_VIEW( "View.withdrawalMenu" ),
        PROMPT_WITHDRAWAL_AMOUNT_VIEW( "View.promptWithdrawalAmount" ),
        PROMPT_RECIPIENT_ACCOUNT_NUMBER_VIEW( "View.promptRecipientAccountNumber" ),
        PROMPT_TRANSFER_AMOUNT_VIEW( "View.promptTransferAmount" ),
        PROMPT_CONFORMATION_VIEW( "View.promptConfirmation" ),
        RESPONSE_VIEW( "View.response" ),
        TAKE_YOUR_CARD_VIEW( "View.takeYourCard "),
        TAKE_YOUR_CASH_VIEW( "View.takeYourCash "),
        ERROR_VIEW( "View.error" );

        private final String viewString;

        View( String viewString )
        {
            this.viewString = viewString;
        }

        public String getViewString()
        {
            return viewString;
        }

        @Override
        public String toString()
        {
            return viewString;
        }
    }
} // end class Screen