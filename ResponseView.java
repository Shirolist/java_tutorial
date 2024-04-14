import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ResponseView extends JPanel implements IView
{
    public ResponseView( String message )
    {
        setLayout( new BorderLayout() );
        setOpaque( false );

        Font font = new Font( "Serial", Font.BOLD, 40 );
        JLabel welcomeLabel = new JLabel( message, SwingConstants.CENTER );
        welcomeLabel.setFont( font );
        add( welcomeLabel );
        setPreferredSize( new Dimension( 800, 600 ) );
    }

    public ResponseView( String[] messages )
    {
        setLayout( new BorderLayout() );
        setOpaque( false );
        setPreferredSize( new Dimension( 800, 600 ) );

        JPanel content = new JPanel( new GridBagLayout() );
        content.setOpaque( false );
        JLabel[] labels = new JLabel[messages.length];
        GridBagConstraints constraints = new GridBagConstraints();
        Font font = new Font( "Serial", Font.BOLD, 40 );

        for ( int index = 0; index < messages.length; ++index ) {
            labels[index] = new JLabel( messages[index] );
            labels[index].setFont( font );
            constraints.gridx = 0;
            constraints.gridy = index;
            content.add( labels[index], constraints );
        }
        add( content, BorderLayout.CENTER );
    }

    @Override
    public JComponent getView()
    {
        return this;
    }
}