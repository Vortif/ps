package controller;

import javafx.scene.image.ImageView;

/**
 * Created by Korzix on 2015-04-10.
 */
public class RotateHelper {
    private final static RotateHelper INSTANCE = new RotateHelper();

    private RotateHelper(){}
    public static RotateHelper getRotateHelperInstance(){ return INSTANCE;}
    private ImageView imageView;

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public void rotate(String angleTaker){
        double angle =0;

        try{
            angle+=Double.parseDouble(angleTaker);

        }catch(Exception e){
            e.printStackTrace();
        }

        imageView.setRotate(angle);
    }

}
