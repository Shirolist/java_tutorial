import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class PromptAccountNumberView extends JPanel implements IView, ActionListener
{
    private final InputHandler inputHandler;
    private final JPanel content;
    private final JLabel accountNumberLabel;
    private final JTextField accountNumberField;
    private String input;

    public PromptAccountNumberView( InputHandler inputHandler )
    {
        this.inputHandler = inputHandler;
        setLayout( new BorderLayout() );
        setOpaque( false );
        setPreferredSize( new Dimension( 800, 600 ) );

        GridBagConstraints constraints = new GridBagConstraints();

        content = new JPanel( new GridBagLayout() );
        content.setOpaque( false );

        accountNumberLabel = new JLabel( "Please enter your account number:", SwingConstants.CENTER );
        Font font = new Font( "Serial", Font.BOLD, 40 );
        accountNumberLabel.setFont( font );
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridx = 0;
        constraints.gridy = 0;
        content.add( accountNumberLabel, constraints );

        accountNumberField = new FancyTextField( "", 15 );
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridx = 0;
        constraints.gridy = 1;
        this.addComponentListener( focusListener );
        accountNumberField.addActionListener( this );
        content.add( accountNumberField, constraints );

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
        if ( actionEvent.getSource() == accountNumberField ) {
            input = actionEvent.getActionCommand();
            inputHandler.setBuffer( input );
            inputHandler.setSignal( InputHandler.Signal.PROMPT_ACCOUNT_NUMBER );
        }
    }
}