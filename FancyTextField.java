    import java.awt.Color;
    import java.awt.Font;
    import java.awt.Graphics;
    import javax.swing.JTextField;

    public class FancyTextField extends JTextField
    {

        public FancyTextField( String text, int columns )
        {
            super( text, columns );
            setOpaque( false );
            Font font = new Font( "Serial", Font.PLAIN, 20 );
            setFont( font );
            setHorizontalAlignment( JTextField.CENTER );
        }

        @Override
        protected void paintComponent( Graphics g )
        {
            g.setColor( getBackground() );
            g.fillRoundRect( 0, 0, getWidth() - 1, getHeight() - 1, 20, 20 );
            super.paintComponent( g );
        }

        @Override
        protected void paintBorder( Graphics g )
        {
            g.setColor( Color.WHITE );
            g.drawRoundRect( 0, 0, getWidth() - 1, getHeight() - 1, 20, 20 );
        }
    }
