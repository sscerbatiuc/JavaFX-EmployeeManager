package edu.step.javafxtabledemo;

import edu.step.javafxtabledemo.model.EmployeeModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author sscerbatiuc
 */
public class AddDialogController implements Initializable {

    @FXML
    private TextField tfId;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfAge;
    
    private ObservableList<EmployeeModel> appMainObservableList;

    @FXML
    void btnAddPersonClicked(ActionEvent event) {
        int id = Integer.valueOf(tfId.getText().trim());
        String name = tfName.getText().trim();
        int iAge = Integer.valueOf(tfAge.getText().trim());
        
        EmployeeModel data = new EmployeeModel(id, name, iAge);
        appMainObservableList.add(data);
        
        closeStage(event);
    }

    public void setAppMainObservableList(ObservableList<EmployeeModel> tvObservableList) {
        this.appMainObservableList = tvObservableList;
        
    }

    private void closeStage(ActionEvent event) {
        Node  source = (Node)  event.getSource(); 
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }  

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO
    }
    
}
