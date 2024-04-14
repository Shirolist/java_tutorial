import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class WithdrawalMenuView extends JPanel implements IView, ActionListener
{
    private final InputHandler inputHandler;
    private final JPanel content;
    private final JLabel[] labels;
    private final JTextField optionField;
    private final String[] messages = {
            "Withdrawal Menu:",
            "1 - $100   6 - $600",
            "2 - $200   7 - $700",
            "3 - $300   8 - $800",
            "4 - $400   9 - $900",
            "5 - $500 10 - $1000",
            "11 - Input withdrawal amount",
            "12 - Cancel transaction",
            "Choose a withdrawal amount:" };
    private String input;

    public WithdrawalMenuView( InputHandler inputHandler )
    {
        this.inputHandler = inputHandler;

        setLayout( new BorderLayout() );
        setOpaque( false );

        content = new JPanel( new GridBagLayout() );
        content.setOpaque( false );
        labels = new JLabel[messages.length];
        GridBagConstraints constraints = new GridBagConstraints();
        Font font = new Font( "Serial", Font.BOLD, 40 );

        for ( int index = 0; index < messages.length; ++index ) {
            labels[index] = new JLabel( messages[index] );
            labels[index].setFont( font );
            constraints.weightx = 1;
            constraints.weighty = 1;
            constraints.gridx = 0;
            constraints.gridy = index;
            if ( index == messages.length - 1 ) {
                constraints.gridwidth = 1;
            } else {
                constraints.gridwidth = 2;
            }
            content.add( labels[index], constraints );
        }

        optionField = new FancyTextField( "", 2 );
        optionField.setFocusable( true );
        constraints.gridx = 1;
        constraints.gridy = messages.length - 1;
        this.addComponentListener( focusListener );
        optionField.addActionListener( this );
        content.add( optionField, constraints );

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
        if ( actionEvent.getSource() == optionField ) {
            input = actionEvent.getActionCommand();
            inputHandler.setBuffer( input );
            inputHandler.setSignal( InputHandler.Signal.PROMPT_WITHDRAWAL_MENU_OPTION );
        }
    }
}
