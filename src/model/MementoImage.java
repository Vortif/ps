package model;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by Korzix on 2015-04-10.
 */
public class MementoImage {


    private ColorAdjust colorAdjust;
    private ImageView imageView;

    public MementoImage(ImageView iv, ColorAdjust colAdjust) {
        imageView = new ImageView();
        imageView.setImage(iv.getImage());
        colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(colAdjust.getBrightness());
        colorAdjust.setSaturation(colAdjust.getSaturation());
        colorAdjust.setContrast(colAdjust.getContrast());
        colorAdjust.setHue(colAdjust.getHue());
        imageView.setEffect(colorAdjust);
        imageView.setRotate(iv.getRotate());

    }

    public ImageView getSavedImageView(){ return imageView;}




}
