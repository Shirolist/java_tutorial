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

public class PromptWithdrawalAmountView extends JPanel implements IView, ActionListener
{
    private final InputHandler inputHandler;
    private final JPanel content;
    private final JLabel withdrawalAmountLabel;
    private final JTextField withdrawalAmountField;
    private String input;

    public PromptWithdrawalAmountView( InputHandler inputHandler )
    {
        this.inputHandler = inputHandler;
        setLayout( new BorderLayout() );
        setOpaque( false );
        setPreferredSize( new Dimension( 800, 600 ) );

        GridBagConstraints constraints = new GridBagConstraints();

        content = new JPanel( new GridBagLayout() );
        content.setOpaque( false );

        withdrawalAmountLabel = new JLabel( "Please enter a withdrawal amount (or 0 to cancel):", SwingConstants.CENTER );
        Font font = new Font( "Serial", Font.BOLD, 30 );
        withdrawalAmountLabel.setFont( font );
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridx = 0;
        constraints.gridy = 0;
        content.add( withdrawalAmountLabel, constraints );

        withdrawalAmountField = new FancyTextField( "", 15 );
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridx = 0;
        constraints.gridy = 1;
        this.addComponentListener( focusListener );
        withdrawalAmountField.addActionListener( this );
        content.add( withdrawalAmountField, constraints );

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
        if ( actionEvent.getSource() == withdrawalAmountField ) {
            input = actionEvent.getActionCommand();
            inputHandler.setBuffer( input );
            inputHandler.setSignal( InputHandler.Signal.PROMPT_WITHDRAWAL_AMOUNT );
        }
    }
}