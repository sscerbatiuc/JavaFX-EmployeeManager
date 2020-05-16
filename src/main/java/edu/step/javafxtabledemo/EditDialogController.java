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
public class EditDialogController implements Initializable {

    @FXML
    private TextField tfId;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfAge;

    private Integer editedIndex;
    private ObservableList<EmployeeModel> appMainObservableList;
    
    public EmployeeModel result;
    
    @FXML
    void btnEditPersonClicked(ActionEvent event) {
        int editedId = Integer.valueOf(tfId.getText().trim());
        String editedName = tfName.getText().trim();
        int editedAge = Integer.valueOf(tfAge.getText().trim());
        this.result = new EmployeeModel(editedId, editedName, editedAge);
        closeStage(event);
    }

    public void setModel(EmployeeModel model) {
        this.tfId.setText(String.valueOf(model.getId()));
        this.tfName.setText(model.getName());
        this.tfAge.setText(String.valueOf(model.getAge()));
    }

    private void closeStage(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO
    }

}
