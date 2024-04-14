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
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;

public class PromptPINView extends JPanel implements IView, ActionListener
{
    private final InputHandler inputHandler;
    private final JPanel content;
    private final JLabel pinLabel;
    private final JPasswordField pinField;
    private String input;

    public PromptPINView( InputHandler inputHandler )
    {
        this.inputHandler = inputHandler;
        setLayout( new BorderLayout() );
        setOpaque( false );
        setPreferredSize( new Dimension( 800, 600 ) );

        GridBagConstraints constraints = new GridBagConstraints();

        content = new JPanel( new GridBagLayout() );
        content.setOpaque( false );

        pinLabel = new JLabel( "Enter your PIN:", SwingConstants.CENTER );
        Font font = new Font( "Serial", Font.BOLD, 40 );
        pinLabel.setFont( font );
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridx = 0;
        constraints.gridy = 0;
        content.add( pinLabel, constraints );

        pinField = new FancyPasswordField( "", 4 );
        pinField.setFocusable( true );
//        pinField.setEchoChar('*');
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridx = 0;
        constraints.gridy = 1;
        this.addComponentListener( focusListener );
        pinField.addActionListener( this );
        content.add( pinField, constraints );

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
        if ( actionEvent.getSource() == pinField ) {
            input = actionEvent.getActionCommand();
            inputHandler.setBuffer( input );
            inputHandler.setSignal( InputHandler.Signal.PROMPT_PIN );
        }
    }
}
