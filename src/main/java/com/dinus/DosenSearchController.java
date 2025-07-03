package com.dinus;

import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.fxml.Initializable;
import java.net.URL;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class DosenSearchController implements Initializable {
    ObservableList<Dosen> listDosen;
    Stage stage;

    @FXML
    private Label lblErr;
    
    @FXML
    private Button btnPilih;
    
    @FXML
    private TextField tfCariNama;

    @FXML
    private TableColumn<Dosen, String> kodeDsn;

    @FXML
    private TableColumn<Dosen, String> namaDsn;

    @FXML
    private TableView<Dosen> tvDosen;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listDosen = FXCollections.observableArrayList();        
        initTabel();
        loadData();
        setFilter();
    } 

    void initTabel() {
        kodeDsn.setCellValueFactory(new PropertyValueFactory<Dosen, String>("kodeDsn"));
        namaDsn.setCellValueFactory(new PropertyValueFactory<Dosen, String>("namaDsn"));
    }

    void loadData() {
        listDosen = AksesDB.getDataDosen();
        tvDosen.setItems(listDosen);
    }
    
    void setFilter() {
        FilteredList<Dosen> filterData = new FilteredList<>(listDosen, b -> true);
        tfCariNama.textProperty().addListener((observable, oldValue, newValue) -> {
            filterData.setPredicate(Dosen -> {
                if (newValue.isEmpty() || newValue == null) {
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();
                if (Dosen.getNamaDsn().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (Dosen.getKodeDsn().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else
                    return false;
            });           
        });
        SortedList<Dosen> sortedData = new SortedList<>(filterData);
        sortedData.comparatorProperty().bind(tvDosen.comparatorProperty());
        tvDosen.setItems(sortedData);
    }     
    
    @FXML
    void pilih(ActionEvent event) {
        stage = (Stage) btnPilih.getScene().getWindow();
        Dosen d = tvDosen.getSelectionModel().getSelectedItem();
        stage.setUserData(d);
        stage.close();        
    }    
}