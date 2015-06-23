package controller;

import com.sun.javafx.fxml.builder.JavaFXImageBuilder;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Korzix on 2015-03-20.
 */
public class ImageChooser
{
    private final FileChooser fileChooser = new FileChooser();
    private final static ImageChooser INSTANCE = new ImageChooser();
   // private File current=null;
    public static ImageChooser getImageChooserInstance(){
        return INSTANCE;
    }

    private ImageChooser(){
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
    }
    public boolean openImage(ImageView img)//return true if image was opened
    {
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
     //       if(current!=null&&file.getAbsolutePath().equals(current.getAbsolutePath())){
                //??????????????Prompt- zapytanie o otowrzenie tego samego pliku...?????????????
       //         return false;
         //   }
            openFile(file, img);
            return true;
        }
        return false;
    }

     private void openFile(File file, ImageView myImage) {
         String localUrl = file.toURI().toString();
         Image image = new Image(localUrl, false);
            //current=file;
            myImage.setFitWidth(image.getWidth());
            myImage.setFitHeight(image.getHeight());
            myImage.setImage(image);
//        } catch (IOException ex) {
//            Logger.getLogger(JavaFXImageBuilder.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }


}
