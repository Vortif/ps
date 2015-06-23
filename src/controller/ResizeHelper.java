package controller;

import javafx.scene.image.ImageView;

/**
 * Created by Korzix on 2015-04-10.
 */
public class ResizeHelper {
    private final static ResizeHelper INSTANCE = new ResizeHelper();

    private ResizeHelper(){}
    public static ResizeHelper getResizeHelperInstance(){ return INSTANCE;}
    private ImageView imageView;

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public void resizeImg(String height, String width){
        try{
            imageView.setFitHeight(Double.parseDouble(height));
            imageView.setFitWidth(Double.parseDouble(width));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
