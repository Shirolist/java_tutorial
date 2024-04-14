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

public class MenuView extends JPanel implements IView, ActionListener
{
    private final InputHandler inputHandler;
    private final JPanel content;
    private final JLabel[] labels;
    private final JTextField optionField;
    private final String[] messages = {
            "Main menu:", "1 - View my balance",
            "2 - Withdraw cash", "3 - Transfer funds",
            "4 - Exit", "Enter a choice:" };
    private String input;

    public MenuView( InputHandler inputHandler )
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
            inputHandler.setSignal( InputHandler.Signal.PROMPT_MENU_OPTION );
        }
    }
}
