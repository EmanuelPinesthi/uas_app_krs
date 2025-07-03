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

public class MhsSearchController implements Initializable {
    ObservableList<Mhs> listMhs;
    Stage stage;

    @FXML
    private Label lblErr;
    
    @FXML
    private Button btnPilih;
    
    @FXML
    private TextField tfCariNama;

    @FXML
    private TableColumn<Mhs, String> nim;

    @FXML
    private TableColumn<Mhs, String> nama;

    @FXML
    private TableColumn<Mhs, String> alamat;

    @FXML
    private TableView<Mhs> tvMhs;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listMhs = FXCollections.observableArrayList();        
        initTabel();
        loadData();
        setFilter();
    } 

    void initTabel() {
        nim.setCellValueFactory(new PropertyValueFactory<Mhs, String>("nim"));
        nama.setCellValueFactory(new PropertyValueFactory<Mhs, String>("nama"));
        alamat.setCellValueFactory(new PropertyValueFactory<Mhs, String>("alamat"));
    }

    void loadData() {
        listMhs = AksesDB.getDataMhs();
        tvMhs.setItems(listMhs);
    }
    
    void setFilter() {
        FilteredList<Mhs> filterData = new FilteredList<>(listMhs, b -> true);
        tfCariNama.textProperty().addListener((observable, oldValue, newValue) -> {
            filterData.setPredicate(Mhs -> {
                if (newValue.isEmpty() || newValue == null) {
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();
                if (Mhs.getNama().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (Mhs.getNim().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else
                    return false;
            });           
        });
        SortedList<Mhs> sortedData = new SortedList<>(filterData);
        sortedData.comparatorProperty().bind(tvMhs.comparatorProperty());
        tvMhs.setItems(sortedData);
    }     
    
    @FXML
    void pilih(ActionEvent event) {
        stage = (Stage) btnPilih.getScene().getWindow();
        Mhs m = tvMhs.getSelectionModel().getSelectedItem();
        stage.setUserData(m);
        stage.close();        
    }    
}