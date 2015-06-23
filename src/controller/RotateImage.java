package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;


/**
 * Created by Maciej Chrzan on 2015-04-02.
 */
public class RotateImage {

    private RotateHelper rotateHelper;
    @FXML
    private TextField angleTaker;

    public void initialize(){
        rotateHelper = RotateHelper.getRotateHelperInstance();
    }

    public void rotateImg(ActionEvent actionEvent){
        rotateHelper.rotate(angleTaker.getText());
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }

}
