import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class PromptConfirmationView extends JPanel implements IView, ActionListener
{
    private final InputHandler inputHandler;
    private final JPanel content;
    private final JLabel confirmLabel;
    private final JButton confirmButton;
    private final JButton cancelButton;
    private final JLabel[] operationLabel;
    private Boolean confirmationInput;

    public PromptConfirmationView( String operationMessage, InputHandler inputHandler )
    {
        this.inputHandler = inputHandler;
        setLayout( new BorderLayout() );
        setOpaque( false );
        setPreferredSize( new Dimension( 800, 600 ) );


        Font font = new Font( "Serial", Font.BOLD, 30 );
        Font buttonFont = new Font( "Serial", Font.PLAIN, 40 );
        GridBagConstraints constraints = new GridBagConstraints();

        content = new JPanel( new GridBagLayout() );
        content.setOpaque( false );

        confirmLabel = new JLabel( "Do you confirm:", SwingConstants.CENTER );
        confirmLabel.setFont( font );
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridx = 0;
        constraints.gridy = 0;
        content.add( confirmLabel, constraints );

        operationLabel = new JLabel[1];
        operationLabel[0] = new JLabel( operationMessage, SwingConstants.CENTER );
        operationLabel[0].setFont( font );
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridx = 0;
        constraints.gridy = 1;
        content.add( operationLabel[0], constraints );

        confirmButton = new JButton( "Confirm" );
        confirmButton.setPreferredSize( new Dimension( 200, 50 ) );
        confirmButton.setFont( buttonFont );
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridx = 0;
        constraints.gridy = 2;
        confirmButton.addActionListener( this );
        content.add( confirmButton, constraints );

        cancelButton = new JButton( "Cancel" );
        cancelButton.setPreferredSize( new Dimension( 200, 50 ) );
        cancelButton.setFont( buttonFont );
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridx = 1;
        constraints.gridy = 2;
        cancelButton.addActionListener( this );
        content.add( cancelButton, constraints );

        add( content, BorderLayout.CENTER );
    }

    public PromptConfirmationView( String[] operationMessage, InputHandler inputHandler )
    {
        this.inputHandler = inputHandler;
        setLayout( new BorderLayout() );
        setOpaque( false );
        setPreferredSize( new Dimension( 800, 600 ) );


        Font font = new Font( "Serial", Font.BOLD, 30 );
        Font buttonFont = new Font( "Serial", Font.PLAIN, 40 );
        GridBagConstraints constraints = new GridBagConstraints();

        content = new JPanel( new GridBagLayout() );
        content.setOpaque( false );

        confirmLabel = new JLabel( "Do you confirm:", SwingConstants.CENTER );
        confirmLabel.setFont( font );
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridx = 0;
        constraints.gridy = 0;
        content.add( confirmLabel, constraints );

        operationLabel = new JLabel[operationMessage.length];

        int i = 0;
        for ( String message : operationMessage ) {
            operationLabel[i] = new JLabel( message, SwingConstants.LEFT );
            operationLabel[i].setFont( font );
            constraints.weightx = 1;
            constraints.weighty = 1;
            constraints.gridx = 0;
            constraints.gridy = i + 1;
            content.add( operationLabel[i], constraints );
            ++i;
        }

        confirmButton = new JButton( "Confirm" );
        confirmButton.setPreferredSize( new Dimension( 200, 50 ) );
        confirmButton.setFont( buttonFont );
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridx = 0;
        constraints.gridy = i + 2;
        confirmButton.addActionListener( this );
        content.add( confirmButton, constraints );

        cancelButton = new JButton( "Cancel" );
        cancelButton.setPreferredSize( new Dimension( 200, 50 ) );
        cancelButton.setFont( buttonFont );
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridx = 1;
        constraints.gridy = i + 2;
        cancelButton.addActionListener( this );
        content.add( cancelButton, constraints );

        add( content, BorderLayout.CENTER );
    }

    @Override
    public JComponent getView()
    {
        return this;
    }

    @Override
    public void actionPerformed( ActionEvent actionEvent )
    {
        if ( actionEvent.getSource() == confirmButton ) {
            confirmationInput = true;
            inputHandler.setBoolBuffer( confirmationInput );
            inputHandler.setSignal( InputHandler.Signal.PROMPT_CONFIRMATION );
        } else if ( actionEvent.getSource() == cancelButton ) {
            confirmationInput = false;
            inputHandler.setBoolBuffer( confirmationInput );
            inputHandler.setSignal( InputHandler.Signal.PROMPT_CONFIRMATION );
        }
    }
}
