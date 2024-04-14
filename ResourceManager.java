import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ResourceManager
{
    public final String BACKGROUND_IMAGE_PATH = "image/background.png";
    public final String BACKGROUND_IMAGE_URL;

    public ResourceManager()
    {
        BACKGROUND_IMAGE_URL = getClass().getResource( BACKGROUND_IMAGE_PATH ).getPath();
    }

    public Image readImage( String url ) throws IOException
    {
        return ImageIO.read( new File( url ) );
    }

//    public String getDATABASE_PATH()
//    {
//        return DATABASE_PATH;
//    }
}
