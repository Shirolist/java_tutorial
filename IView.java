import java.awt.Component;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JComponent;

public interface IView
{
    ComponentListener focusListener = new ComponentAdapter()
    {
        @Override
        public void componentShown( ComponentEvent e )
        {
            Component source = ( Component ) e.getSource();
            source.requestFocusInWindow();
        }
    };

    JComponent getView();
}