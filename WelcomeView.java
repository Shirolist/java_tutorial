import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class WelcomeView extends JPanel implements IView
{
    private boolean isInserted;
    private final InputHandler inputHandler;

    public WelcomeView( InputHandler inputHandler )
    {
        this.inputHandler = inputHandler;
        BorderLayout borderLayout = new BorderLayout();
        setLayout( borderLayout );
        setOpaque( false );
        setPreferredSize( new Dimension( 800, 600 ) );

        JPanel content = new JPanel( new GridBagLayout() );
        content.setOpaque( false );
        content.setFocusable( true );
        content.requestFocusInWindow();
        GridBagConstraints constraints = new GridBagConstraints();
        Font font = new Font( "Serial", Font.BOLD, 40 );
        Font instructionFont = new Font( "Serial", Font.PLAIN, 25 );

        JLabel welcomeLabel = new JLabel( "Welcome!", SwingConstants.CENTER );
        welcomeLabel.setFont( font );
        constraints.gridx = 0;
        constraints.gridy = 0;
        content.add( welcomeLabel, constraints );

        JLabel instructionLabel = new JLabel( "Please insert your card.", SwingConstants.CENTER  );
        instructionLabel.setFont( instructionFont );
        constraints.gridx = 0;
        constraints.gridy = 1;
        this.addComponentListener( focusListener );
        content.add( instructionLabel, constraints );

        JLabel tipLabel = new JLabel( "Press enter to simulate insert card action.", SwingConstants.CENTER );
        tipLabel.setFont( instructionFont );
        constraints.gridx = 0;
        constraints.gridy = 2;
        content.add( tipLabel, constraints );

        content.addKeyListener( new ResponseListener() );

        add( content, BorderLayout.CENTER );
    }

    @Override
    public JComponent getView()
    {
        return this;
    }

    class ResponseListener implements KeyListener
    {

        @Override
        public void keyTyped( KeyEvent keyEvent )
        {
        }

        @Override
        public void keyPressed( KeyEvent keyEvent )
        {
            if ( keyEvent.getKeyCode() == KeyEvent.VK_ENTER ){
                isInserted = true;
                inputHandler.setBoolBuffer( isInserted );
                inputHandler.setSignal( InputHandler.Signal.INSERT_CARD );
            }
        }

        @Override
        public void keyReleased( KeyEvent keyEvent )
        {
        }
    }
}
