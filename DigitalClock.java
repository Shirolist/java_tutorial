import java.awt.Font;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DigitalClock extends JPanel
{
    private final String time;
    private final Timer timer;
    private final Calendar calendar;
    private final Font font;
    private final JLabel timeLabel;

    public DigitalClock()
    {
        timer = new Timer();
        Date date = new Date();
        DateFormat timeFormat = new SimpleDateFormat( "HH:mm:ss" );
        time = timeFormat.format( date );
        timeLabel = new JLabel( time );
        font = new Font( "SansSerif", Font.PLAIN, 20 );
        timeLabel.setFont( font );
        add( timeLabel );

        calendar = Calendar.getInstance();
        timer.scheduleAtFixedRate( new ClockUpdateTask(), 0, 1000 );
        this.setOpaque( false );
    }

    public void updateClock()
    {
        Date date = new Date();
        DateFormat timeFormat = new SimpleDateFormat( "HH:mm:ss" );
        String time = timeFormat.format( date );
        timeLabel.setText( time );
    }

    class ClockUpdateTask extends TimerTask
    {
        @Override
        public void run()
        {
            updateClock();
        }
    }
}