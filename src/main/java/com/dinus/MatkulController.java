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

public class MatkulController implements Initializable {
    ObservableList<Matakuliah> listMatakuliah;
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
    private TextField tfKodeMk;

    @FXML
    private TextField tfNamaMk;

    @FXML
    private TextField tfSks;

    @FXML
    private TextField tfCariNama;

    @FXML
    private TableColumn<Matakuliah, String> kodeMk;

    @FXML
    private TableColumn<Matakuliah, String> namaMk;

    @FXML
    private TableColumn<Matakuliah, Integer> sks;

    @FXML
    private TableView<Matakuliah> tvMatakuliah;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listMatakuliah = FXCollections.observableArrayList();        
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
        tfKodeMk.requestFocus();
    }

    @FXML
    void cancel(ActionEvent event) {
        teksAktif(false);
        clearTeks();
        buttonAktif(false);  
    }

    @FXML
    void delete(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Hapus data matakuliah?", ButtonType.YES, ButtonType.CANCEL);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            int idx = tvMatakuliah.getSelectionModel().getSelectedIndex();
            String kodeMk = tvMatakuliah.getItems().get(idx).getKodeMk();
            AksesDB.delDataMatakuliah(kodeMk);

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
        int idx = tvMatakuliah.getSelectionModel().getSelectedIndex();
        tfKodeMk.setText(tvMatakuliah.getItems().get(idx).getKodeMk());
        tfNamaMk.setText(tvMatakuliah.getItems().get(idx).getNamaMk());
        tfSks.setText(String.valueOf(tvMatakuliah.getItems().get(idx).getSks()));        
        tfKodeMk.requestFocus();
    }

    @FXML
    void update(ActionEvent event) {
        String kodeMk, namaMk, kodeMkLama;
        int sks;
        kodeMk = tfKodeMk.getText();
        namaMk = tfNamaMk.getText();	
        
        try {
            sks = Integer.parseInt(tfSks.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("SKS harus berupa angka!");
            alert.show();
            return;
        }
        
        if (flagEdit == false) {
            AksesDB.addDataMatakuliah(kodeMk, namaMk, sks);	
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Insert Data Sukses..");
            alert.show();			
        } else {
            int idx = tvMatakuliah.getSelectionModel().getSelectedIndex();
            kodeMkLama = tvMatakuliah.getItems().get(idx).getKodeMk();
            AksesDB.updateDataMatakuliah(kodeMk, namaMk, sks, kodeMkLama);
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
        tfKodeMk.setEditable(aktif);
        tfNamaMk.setEditable(aktif);
        tfSks.setEditable(aktif);
    }

    public void clearTeks() {
        tfKodeMk.setText("");
        tfNamaMk.setText("");
        tfSks.setText("");
    }

    void initTabel() {
        kodeMk.setCellValueFactory(new PropertyValueFactory<Matakuliah, String>("kodeMk"));
        namaMk.setCellValueFactory(new PropertyValueFactory<Matakuliah, String>("namaMk"));
        sks.setCellValueFactory(new PropertyValueFactory<Matakuliah, Integer>("sks"));
    }

    void loadData() {
        listMatakuliah = AksesDB.getDataMatakuliah();
        tvMatakuliah.setItems(listMatakuliah);
    }

    void setFilter() {
        FilteredList<Matakuliah> filterData = new FilteredList<>(listMatakuliah, b -> true);
        tfCariNama.textProperty().addListener((observable, oldValue, newValue) -> {
            filterData.setPredicate(Matakuliah -> {
                if (newValue.isEmpty() || newValue == null) {
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();
                if (Matakuliah.getNamaMk().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (Matakuliah.getKodeMk().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else
                    return false;
            });           
        });
        SortedList<Matakuliah> sortedData = new SortedList<>(filterData);
        sortedData.comparatorProperty().bind(tvMatakuliah.comparatorProperty());
        tvMatakuliah.setItems(sortedData);
    }
}