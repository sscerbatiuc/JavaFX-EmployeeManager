package edu.step.javafxtabledemo;

import edu.step.javafxtabledemo.model.EmployeeModel;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainSceneController implements Initializable {

    @FXML
    private TableView<EmployeeModel> tvData;
    @FXML
    private TableColumn colId;
    @FXML
    private TableColumn colName;
    @FXML
    private TableColumn colAge;

    private final ObservableList<EmployeeModel> tvObservableList = FXCollections.observableArrayList();

    @FXML
    void onOpenAddDialog(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/AddDialog.fxml"));
        Parent parent = fxmlLoader.load();
        AddDialogController dialogController = (AddDialogController) fxmlLoader.getController();
        dialogController.setAppMainObservableList(tvObservableList);

        Scene scene = new Scene(parent, 300, 200);
        Stage stage = new Stage();
        stage.setTitle("Add employee");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    void onOpenEditDialog(ActionEvent event) throws IOException {
        int selectedRow = this.tvData.getSelectionModel().getSelectedIndex();
        if (selectedRow != -1) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/EditDialog.fxml"));
            Parent parent = fxmlLoader.load();
            EditDialogController dialogController = (EditDialogController) fxmlLoader.getController();
            dialogController.setObservableList(tvObservableList, selectedRow);

            Scene scene = new Scene(parent, 300, 200);
            Stage stage = new Stage();
            stage.setTitle("Edit employee");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
        }
    }

    @FXML
    void onDeleteEmployee() {
        int selectedRow = this.tvData.getSelectionModel().getSelectedIndex();
        if (selectedRow != -1) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Please confirm");
            alert.setHeaderText("Are you sure you want to delete this user?");
            alert.setContentText("This action cannot be undone");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                this.tvObservableList.remove(selectedRow);
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        tvData.setItems(tvObservableList);
        tvData.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        tvObservableList.addListener((ListChangeListener) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                   // DAO.addEmployee()
                    System.out.println(tvObservableList.get(change.getFrom()));
                } else if (change.wasUpdated()){
                    // DAO.updateEmployee();
                }
            }
        });
    }
}
