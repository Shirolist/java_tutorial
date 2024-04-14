import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

public class LayeredContainer extends JLayeredPane
{
    private final DigitalClock clock;
    private final MainPanel mainPanel;
    private final ResourceManager resourceManager;
    private final JLabel background;

    public LayeredContainer( MainPanel mainPanel, ResourceManager resourceManager )
    {
        this.mainPanel = mainPanel;
        this.resourceManager = resourceManager;
        clock = new DigitalClock();
        Image backgroundImage;
        try {
            backgroundImage = resourceManager.readImage( resourceManager.BACKGROUND_IMAGE_URL );
        } catch ( IOException exception ) {
            backgroundImage = null;
        }
        assert backgroundImage != null;
        Image temp = backgroundImage.getScaledInstance( 800, 600, Image.SCALE_SMOOTH );
        ImageIcon imageIcon = new ImageIcon( temp );
        background = new JLabel( imageIcon );
        background.setLayout( null );
        background.setBounds( 0, 0, 800, 600 );
        setLayout( new BorderLayout() );
        setPreferredSize( new Dimension( 800, 600 ) );
        setOpaque( true );
        clock.setBounds( 685, 5, 100, 100 );
        mainPanel.setOpaque( false );

        setLayer( background, 0 );
        setLayer( mainPanel, 1 );
        setLayer( clock, 2 );

        add( background );
        add( clock );
        add( mainPanel );
    }
}
