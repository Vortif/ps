package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import model.Caretaker;
import model.ImageModel;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    private ImageSaver imageSaver;
    private final KeyCombination CTRL_S = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);
    private final KeyCombination CTRL_O = new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN);
    private final KeyCombination CTRL_Z = new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN);
    private ImageModel imageModel;
    private Caretaker caretaker;

    @FXML
    private ImageView imageView;
    @FXML
    private Slider sliderBrightness, sliderSaturation, sliderContrast, sliderHue;
    @FXML
    private MenuItem saveImgId, undoButton;
    @FXML
    private Button defContrastButton, defSatButton, defBrightButton, defHueButton, rotateButton, resizeButton, blurButton, gaussButton, sharpenButton, edgesButton, embossingButton, laplaceButton, cutButton;
    @FXML
    private TextField leftCut, rightCut, upCut, downCut;
    private List<Slider> sliderList;
    private int currentImage =0;

    private void addSlidersToList(){
        sliderList= new ArrayList<>();
        sliderList.add(sliderBrightness);
        sliderList.add(sliderSaturation);
        sliderList.add(sliderContrast);
        sliderList.add(sliderHue);
    }
    @FXML
    public void openImgSearching(ActionEvent actionEvent)    {
        if(imageModel.openImage()){
            setSlidersDefaultValues();
            enableSliders();
            enableButtons();
            currentImage =0;
            addImageMemento();
        }
    }


    private void setSlidersDefaultValues(){
        for(Slider slider : sliderList)
            slider.setValue(0.0);
    }

    private void enableSliders(){
        for(Slider slider : sliderList)
            slider.setDisable(false);
    }


    public void zoomImage(ScrollEvent scrollEvent){
        imageModel.zoomImage(scrollEvent);
    }

    public void initialize()    {
        imageSaver = ImageSaver.getImageSaverInstance();
        imageModel = new ImageModel(imageView);
        caretaker = new Caretaker();
        addSlidersToList();
        setBindsBidirectional();
        setSliderListeners();
        setSlidersOnChangeListeners();
    }

    private void setBindsBidirectional(){
        imageModel.setBindsBidirectional(sliderBrightness, sliderSaturation, sliderContrast, sliderHue);
    }

    private void setSliderListeners(){
        imageModel.setSliderListeners(sliderBrightness, sliderSaturation, sliderContrast, sliderHue);
    }

    private void setSlidersOnChangeListeners(){
        sliderBrightness.setOnMousePressed((ob) -> {
            addImageMemento();
        });
        sliderSaturation.setOnMousePressed((ob) -> {
            addImageMemento();
        });
        sliderContrast.setOnMousePressed((ob) -> {
            addImageMemento();
        });
        sliderHue.setOnMousePressed((ob) -> {
            addImageMemento();
        });
    }

    public void onDefBrightness(ActionEvent actionEvent) {
        addImageMemento();
        imageModel.setDefaultBrightness();
    }

    public void onDefSaturation(ActionEvent actionEvent) {
        addImageMemento();
        imageModel.setDefaultSaturation();
    }

    public void onDefContrast(ActionEvent actionEvent) {
        addImageMemento();
        imageModel.setDefaultContrast();
    }

    public void onDefHue(ActionEvent actionEvent) {
        addImageMemento();
        imageModel.setDefaultHue();
    }

    public void saveImgAction(ActionEvent actionEvent) {
        imageSaver.saveImage(imageView);
    }

    public void cutImage(ActionEvent actionEvent){
        addImageMemento();
        imageModel.cutField(leftCut.getText(), upCut.getText(), rightCut.getText(), downCut.getText());
        upCut.setText("0");
        leftCut.setText("0");
        downCut.setText("0");
        rightCut.setText("0");
    }

    public void onCtrlShortcutReleased(KeyEvent keyEvent) {
        if(CTRL_O.match(keyEvent))
            openImgSearching(new ActionEvent(null, null));
        else if(imageView.getImage()!=null) {
            if(CTRL_Z.match(keyEvent))
                undoAction(new ActionEvent(null, null));
            else if (CTRL_S.match(keyEvent))
                imageSaver.saveImage(imageView);
        }
    }
    public void rotate(ActionEvent actionEvent){
        addImageMemento();
        imageModel.rotate();

    }

    public void resize(ActionEvent actionEvent){
        addImageMemento();
        imageModel.resize();
    }
    public void blur(ActionEvent actionEvent){
        addImageMemento();
        imageModel.blur();
    }
    public void gauss(ActionEvent actionEvent){
        addImageMemento();
        imageModel.gauss();
    }
    public void sharpen(ActionEvent actionEvent){
        addImageMemento();
        imageModel.sharpen();
    }
    public void edges(ActionEvent actionEvent){
        addImageMemento();
        imageModel.edges();
    }
    public void embossing(ActionEvent actionEvent){
        addImageMemento();
        imageModel.embossing();
    }
    public void laplace(ActionEvent actionEvent){
        addImageMemento();
        imageModel.laplace();
    }

    private void enableButtons(){
        rotateButton.setDisable(false);
        resizeButton.setDisable(false);
        defBrightButton.setDisable(false);
        defContrastButton.setDisable(false);
        defHueButton.setDisable(false);
        defSatButton.setDisable(false);
        rotateButton.setDisable(false);
        resizeButton.setDisable(false);
        blurButton.setDisable(false);
        gaussButton.setDisable(false);
        sharpenButton.setDisable(false);
        edgesButton.setDisable(false);
        embossingButton.setDisable(false);
        laplaceButton.setDisable(false);
        cutButton.setDisable(false);
        saveImgId.setDisable(false);
        leftCut.setDisable(false);
        upCut.setDisable(false);
        downCut.setDisable(false);
        rightCut.setDisable(false);
    }

    public void undoAction(ActionEvent actionEvent) {
        if(currentImage >= 1){
            currentImage--;
            ImageView iv = imageModel.restoreFromMemento(caretaker.getMementoImage());

            imageModel.setImageView(iv);
            setSliderListeners();
            setBindsBidirectional();
        }
        else{
            undoButton.setDisable(true);
        }
    }

    private void addImageMemento(){
        caretaker.addMemento(imageModel.storeInMemento());
        undoButton.setDisable(false);
        currentImage++;
    }
}
