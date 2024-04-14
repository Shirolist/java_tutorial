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

public class TakeYourCardView extends JPanel implements IView
{
    private boolean isTaken;
    private InputHandler inputHandler;

    public TakeYourCardView( InputHandler inputHandler )
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

        JLabel instructionLabel = new JLabel( "Take your card.", SwingConstants.CENTER );
        instructionLabel.setFont( font );
        constraints.gridx = 0;
        constraints.gridy = 0;
        content.add( instructionLabel, constraints );

        JLabel tipLabel = new JLabel( "Press enter to simulate take card action.", SwingConstants.CENTER );
        tipLabel.setFont( instructionFont );
        constraints.gridx = 0;
        constraints.gridy = 1;
        content.add( tipLabel, constraints );

        this.addComponentListener( focusListener );
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
                isTaken = true;
                inputHandler.setBoolBuffer( isTaken );
                inputHandler.setSignal( InputHandler.Signal.TAKE_CARD );
            }
        }

        @Override
        public void keyReleased( KeyEvent keyEvent )
        {
        }
    }
}
