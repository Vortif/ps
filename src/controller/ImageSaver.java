package controller;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Msciej Chrzan on 2015-03-31.
 */
public class ImageSaver {
    private final FileChooser fileChooser = new FileChooser();
    private final static ImageSaver INSTANCE = new ImageSaver();
    public static ImageSaver getImageSaverInstance(){
        return INSTANCE;
    }

    private ImageSaver(){
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
    }
    public void saveImage(ImageView img)
    {
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            saveFile(file, img);
        }
    }

    private void saveFile(File file, ImageView myImage) {
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(myImage.snapshot(null, null), null), "png", file);

        } catch (IOException ex) {
            Logger.getLogger(ImageSaver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
