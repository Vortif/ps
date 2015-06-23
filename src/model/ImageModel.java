package model;

import controller.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ScrollEvent;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Korzix on 2015-04-10.
 */
public class ImageModel {

    private ColorAdjust colorAdjust;
    private ImageView imageView;
    boolean imageChange = false;
    private RotateHelper rotateHelper;
    private ResizeHelper resizeHelper;

    public ImageModel(ImageView imageView){
        this.imageView = imageView;
        colorAdjust = new ColorAdjust();
        imageView.setEffect(colorAdjust);
        rotateHelper = RotateHelper.getRotateHelperInstance();
        resizeHelper = ResizeHelper.getResizeHelperInstance();

    }

    public void setImageView(ImageView iv){
        this.imageView.setFitHeight(iv.getFitHeight());
        this.imageView.setFitWidth(iv.getFitWidth());
        this.imageView.setRotate(iv.getRotate());
        this.imageView.setImage(iv.getImage());
        colorAdjust = (ColorAdjust)iv.getEffect();
        this.imageView.setEffect(colorAdjust);

    }
    public void setBindsBidirectional(Slider sliderBrightness, Slider sliderSaturation, Slider sliderContrast, Slider sliderHue){
        sliderBrightness.valueProperty().bindBidirectional(colorAdjust.brightnessProperty());
        sliderSaturation.valueProperty().bindBidirectional(colorAdjust.saturationProperty());
        sliderContrast.valueProperty().bindBidirectional(colorAdjust.contrastProperty());
        sliderHue.valueProperty().bindBidirectional(colorAdjust.hueProperty());
    }

    public void setSliderListeners(Slider sliderBrightness, Slider sliderSaturation, Slider sliderContrast, Slider sliderHue){
        sliderBrightness.valueProperty()
                .addListener((ov) ->
                {
                    imageView.setEffect(colorAdjust);
                });

        sliderSaturation.valueProperty()
                .addListener((ov) ->
                {
                    imageView.setEffect(colorAdjust);
                });
        sliderContrast.valueProperty()
                .addListener((ob) ->
                {
                    imageView.setEffect(colorAdjust);
                });
        sliderHue.valueProperty()
                .addListener((ob) ->
                {
                    imageView.setEffect(colorAdjust);
                });
    }
    public void setDefaultBrightness() {
        colorAdjust.brightnessProperty().setValue(0);
        imageView.setEffect(colorAdjust);
    }

    public void setDefaultSaturation() {
        colorAdjust.saturationProperty().setValue(0);
        imageView.setEffect(colorAdjust);
    }

    public void setDefaultContrast() {
        colorAdjust.contrastProperty().setValue(0);
        imageView.setEffect(colorAdjust);
    }

    public void setDefaultHue() {
        colorAdjust.hueProperty().setValue(0);
        imageView.setEffect(colorAdjust);
    }

    public boolean openImage(){
        ImageChooser imageChooser = ImageChooser.getImageChooserInstance();
        imageChange=imageChooser.openImage(imageView);
        if(imageView!=null&&imageChange) {
            imageChange=false;
            imageView.setRotate(0);
            return true;
        }
        imageChange=false;
        return false;
    }

    public void zoomImage(ScrollEvent scrollEvent) {
        scrollEvent.getScreenX();
        if(scrollEvent.getDeltaY() > 0)
        {
            imageView.setFitHeight(imageView.getFitHeight() * 1.1);
            imageView.setFitWidth(imageView.getFitWidth() * 1.1);
        }
        else if (scrollEvent.getDeltaY()<0) {
            imageView.setFitHeight(imageView.getFitHeight() / 1.1);
            imageView.setFitWidth(imageView.getFitWidth() / 1.1);
        }
    }

    public MementoImage storeInMemento(){
        return new MementoImage(imageView, colorAdjust);
    }

    public ImageView restoreFromMemento(MementoImage mementoImage){
        ImageView iv = mementoImage.getSavedImageView();
        return iv;
    }

    public void rotate(){
        rotateHelper.setImageView(imageView);
        try {
            Parent root = (new FXMLLoader(getClass().getResource("/fxml/AngleTaker.fxml"))).load();
            Scene scene = new Scene(root);
            Stage stage= new Stage();
            stage.setScene(scene);
            stage.setTitle("Enter rotation angle");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void resize() {
        resizeHelper.setImageView(imageView);
        try{
            Parent root = (new FXMLLoader(getClass().getResource("/fxml/Resize.fxml"))).load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Resize");
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void blur() {
        Filters.setImageView(imageView);
        Filters.averagingFilter();
    }

    public void gauss() {
        Filters.setImageView(imageView);
        Filters.gaussFilter();
    }

    public void sharpen() {
        Filters.setImageView(imageView);
        Filters.sharpen();
    }

    public void edges() {
        Filters.setImageView(imageView);
        Filters.showEdges();
    }

    public void embossing() {
        Filters.setImageView(imageView);
        Filters.embossing();
    }

    public void laplace() {
        Filters.setImageView(imageView);
        Filters.laplace();
    }

    public void cutField(String leftCut, String upCut, String rightCut, String downCut){
        int x = 0, y = 0;
        int width = (int)imageView.getImage().getWidth();
        int height = (int)imageView.getImage().getHeight();

        if(leftCut.matches("[1-9][0-9]*")){
            x = (int)imageView.getX()+Integer.parseInt(leftCut);
        }

        if(upCut.matches("[1-9][0-9]*")){
            y =(int)imageView.getY() + Integer.parseInt(upCut);
        }

        if(rightCut.matches("[1-9][0-9]*")) {
            width -= Integer.parseInt(rightCut);
        }

        if(downCut.matches("[1-9][0-9]*")) {
            height -= Integer.parseInt(downCut);
        }

        PixelReader pixelReader = imageView.getImage().getPixelReader();
        WritableImage img = new WritableImage(pixelReader, x, y, width-x, height-y);
        imageView.setImage(img);
        imageView.setFitHeight(height-y);
        imageView.setFitWidth(width-x);
    }
}
