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

public class PromptTransferAmountView extends JPanel implements IView, ActionListener
{
    private final InputHandler inputHandler;
    private final JPanel content;
    private final JLabel transferAmountLabel;
    private final JTextField transferAmountField;
    private String input;

    public PromptTransferAmountView( InputHandler inputHandler )
    {
        this.inputHandler = inputHandler;
        setLayout( new BorderLayout() );
        setOpaque( false );
        setPreferredSize( new Dimension( 800, 600 ) );

        GridBagConstraints constraints = new GridBagConstraints();

        content = new JPanel( new GridBagLayout() );
        content.setOpaque( false );

        transferAmountLabel = new JLabel( "Input the transfer amount:", SwingConstants.CENTER );
        Font font = new Font( "Serial", Font.BOLD, 40 );
        transferAmountLabel.setFont( font );
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridx = 0;
        constraints.gridy = 0;
        content.add( transferAmountLabel, constraints );

        transferAmountField = new FancyTextField( "", 15 );
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridx = 0;
        constraints.gridy = 1;
        this.addComponentListener( focusListener );
        transferAmountField.addActionListener( this );
        content.add( transferAmountField, constraints );

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
        if ( actionEvent.getSource() == transferAmountField ) {
            input = actionEvent.getActionCommand();
            inputHandler.setBuffer( input );
            inputHandler.setSignal( InputHandler.Signal.PROMPT_TRANSFER_AMOUNT );
        }
    }
}