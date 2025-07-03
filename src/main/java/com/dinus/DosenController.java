package com.dinus;

import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Label;
import javafx.fxml.Initializable;
import java.net.URL;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class DosenController implements Initializable {
    ObservableList<Dosen> listDosen;
    boolean flagEdit;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnDel;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnUpdate;

    @FXML
    private Label lblErr;

    @FXML
    private TextField tfKodeDsn;

    @FXML
    private TextField tfNamaDsn;

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
        buttonAktif(false);
        teksAktif(false);
        flagEdit = false;
    } 

    @FXML
    void add(ActionEvent event) {
        flagEdit = false;
        teksAktif(true);
        buttonAktif(true);
        tfKodeDsn.requestFocus();
    }

    @FXML
    void cancel(ActionEvent event) {
        teksAktif(false);
        clearTeks();
        buttonAktif(false);  
    }

    @FXML
    void delete(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Hapus data dosen?", ButtonType.YES, ButtonType.CANCEL);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            int idx = tvDosen.getSelectionModel().getSelectedIndex();
            String kodeDsn = tvDosen.getItems().get(idx).getKodeDsn();
            AksesDB.delDataDosen(kodeDsn);

            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setContentText("Delete Data Sukses..");
            alert2.show();
            loadData();                       
        }
    }

    @FXML
    void edit(ActionEvent event) {        
        buttonAktif(true);
        teksAktif(true);
        flagEdit = true;			
        int idx = tvDosen.getSelectionModel().getSelectedIndex();
        tfKodeDsn.setText(tvDosen.getItems().get(idx).getKodeDsn());
        tfNamaDsn.setText(tvDosen.getItems().get(idx).getNamaDsn());
        tfKodeDsn.requestFocus();
    }

    @FXML
    void update(ActionEvent event) {
        String kodeDsn, namaDsn, kodeDsnLama;
        kodeDsn = tfKodeDsn.getText();
        namaDsn = tfNamaDsn.getText();	
        
        if (flagEdit == false) {
            AksesDB.addDataDosen(kodeDsn, namaDsn);	
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Insert Data Sukses..");
            alert.show();			
        } else {
            int idx = tvDosen.getSelectionModel().getSelectedIndex();
            kodeDsnLama = tvDosen.getItems().get(idx).getKodeDsn();
            AksesDB.updateDataDosen(kodeDsn, namaDsn, kodeDsnLama);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Update Data Berhasil");
            alert.show();
        }
        loadData();
        flagEdit = false;
        teksAktif(false);
        clearTeks();
        buttonAktif(false);        
    }

    public void buttonAktif(boolean nonAktif) {
        btnAdd.setDisable(nonAktif);
        btnEdit.setDisable(nonAktif);
        btnDel.setDisable(nonAktif);
        btnUpdate.setDisable(!nonAktif);
        btnCancel.setDisable(!nonAktif);
    }

    public void teksAktif(boolean aktif) {
        tfKodeDsn.setEditable(aktif);
        tfNamaDsn.setEditable(aktif);
    }

    public void clearTeks() {
        tfKodeDsn.setText("");
        tfNamaDsn.setText("");
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
}