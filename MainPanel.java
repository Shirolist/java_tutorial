import java.awt.CardLayout;
import javax.swing.JPanel;

public class MainPanel extends JPanel
{
    private final CardLayout cardLayout;

    public MainPanel( CardLayout cardLayout )
    {
        this.cardLayout = cardLayout;
        setLayout( cardLayout );
    }
}