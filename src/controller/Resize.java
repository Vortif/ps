package controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;

/**
 * Created by Maciej Chrzan on 2015-04-03.
 */
public class Resize {
    @FXML
    private TextField height;
    @FXML
    private TextField width;
    private ResizeHelper resizeHelper;

    public void initialize(){
        resizeHelper = ResizeHelper.getResizeHelperInstance();
    }

    public void resize(ActionEvent actionEvent){
        resizeHelper.resizeImg(height.getText(), width.getText());
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }

}
