package edu.step.javafxtabledemo;

import edu.step.javafxtabledemo.model.EmployeeModel;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainSceneController implements Initializable {

    @FXML
    private TableView<EmployeeModel> table;
    @FXML
    private TableColumn<EmployeeModel, Integer> colId;
    @FXML
    private TableColumn<EmployeeModel, String> colName;
    @FXML
    private TableColumn<EmployeeModel, Integer> colAge;

    /**
     * Configure the observable list to listen for the modifications inside the
     * EmployeeModel objects.
     */
    private final ObservableList<EmployeeModel> obsList = FXCollections.observableArrayList(
            (EmployeeModel model) -> new Observable[]{model.idProperty(), model.nameProperty(), model.ageProperty()}
    );

    @FXML
    void onOpenAddDialog(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/AddDialog.fxml"));
        Parent parent = fxmlLoader.load();
        AddDialogController dialogController = (AddDialogController) fxmlLoader.getController();
        dialogController.setAppMainObservableList(obsList);

        Scene scene = new Scene(parent, 300, 200);
        Stage stage = new Stage();
        stage.setTitle("Add employee");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    void onOpenEditDialog(ActionEvent event) throws IOException {
        int selectedRow = this.table.getSelectionModel().getSelectedIndex();
        if (selectedRow != -1) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/EditDialog.fxml"));
            Parent parent = fxmlLoader.load();
            EditDialogController dialogController = fxmlLoader.getController();
            dialogController.setModel(obsList.get(selectedRow));

            Stage stage = new Stage();
            stage.setTitle("Edit employee");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(parent, 300, 200));
            stage.showAndWait();
            obsList.get(selectedRow).setId(dialogController.result.getId());
            obsList.get(selectedRow).setAge(dialogController.result.getAge());
            obsList.get(selectedRow).setName(dialogController.result.getName());
        }
    }

    @FXML
    void onDeleteEmployee() {
        int selectedRow = this.table.getSelectionModel().getSelectedIndex();
        if (selectedRow != -1) {
            Alert alert = new Alert(AlertType.CONFIRMATION, "This action cannot be undone");
            alert.setTitle("Please confirm");
            alert.setHeaderText("Are you sure you want to delete this user?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                this.obsList.remove(selectedRow);
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        
        table.setItems(obsList);
        table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        obsList.addListener(new ListChangeListener<EmployeeModel>() {
            @Override
            public void onChanged(Change<? extends EmployeeModel> change) {
                while (change.next()) {
                    if (change.wasPermutated()) {
                        for (int i = change.getFrom(); i < change.getTo(); ++i) {
                            System.out.println("Permuted: " + i + " " + obsList.get(i));
                        }
                    } else if (change.wasUpdated()) {
                        for (int i = change.getFrom(); i < change.getTo(); ++i) {
                            System.out.println("Updated: " + i + " " + obsList.get(i));
                        }
                    } else {
                        change.getRemoved().forEach((removedItem) -> {
                            System.out.println("Removed: " + removedItem);
                        });
                        change.getAddedSubList().forEach((addedItem) -> {
                            System.out.println("Added: " + addedItem);
                        });
                    }
                }
            }
        });
    }
}
