package com.dinus;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Node;

public class KrsController implements Initializable {
    ObservableList<Krs> listKrs;
    boolean flagEdit;

    @FXML
    private Button btnAdd;

    @FXML
    private TextField tfCari;
   
    @FXML
    private Button btnCancel;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnPilihMatkul;

    @FXML
    private Button btnPilihMhs;

    @FXML
    private TableColumn<Krs, String> kodeMk;

    @FXML
    private TableColumn<Krs, String> namaMk;

    @FXML
    private TableColumn<Krs, String> kelas;

    @FXML
    private TableColumn<Krs, String> nim;

    @FXML
    private TableColumn<Krs, String> namaMhs;

    @FXML
    private TableColumn<Krs, String> status;

    @FXML
    private TableView<Krs> tbKrs;

    @FXML
    private TextField tfKodeMk;

    @FXML
    private TextField tfNamaMk;

    @FXML
    private TextField tfKelas;

    @FXML
    private TextField tfNim;

    @FXML
    private TextField tfNamaMhs;

    @FXML
    private ComboBox<String> cbStatus;
    
    @FXML
    void pilihMatkul(ActionEvent event) {
        Stage stage = new Stage();
        Parent root;       
        try {
            root = FXMLLoader.load(MatkulSearchController.class.getResource("fmatkulSearch.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Daftar Matakuliah");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node)event.getSource()).getScene().getWindow());
            stage.showAndWait();         
            Matakuliah m;
            m = (Matakuliah) stage.getUserData();            
            tfKodeMk.setText(m.getKodeMk());
            tfNamaMk.setText(m.getNamaMk());
        } catch (IOException ex) {
            Logger.getLogger(KrsController.class.getName()).log(Level.SEVERE, null, ex);
        }         
    }

    @FXML
    void pilihMhs(ActionEvent event) {
        Stage stage = new Stage();
        Parent root;       
        try {
            root = FXMLLoader.load(MhsSearchController.class.getResource("fmhsSearch.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Daftar Mahasiswa");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node)event.getSource()).getScene().getWindow());
            stage.showAndWait();         
            Mhs m;
            m = (Mhs) stage.getUserData();            
            tfNim.setText(m.getNim());
            tfNamaMhs.setText(m.getNama());
        } catch (IOException ex) {
            Logger.getLogger(KrsController.class.getName()).log(Level.SEVERE, null, ex);
        }         
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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "hapus data ?", ButtonType.YES, ButtonType.CANCEL);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            int idx = tbKrs.getSelectionModel().getSelectedIndex();            
            String vKodeMk = tbKrs.getItems().get(idx).getKodeMk();
            String vKelas = tbKrs.getItems().get(idx).getKelas();
            String vNim = tbKrs.getItems().get(idx).getNim();
            AksesDB.delDataKrs(vKodeMk, vKelas, vNim);
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
        int idx = tbKrs.getSelectionModel().getSelectedIndex();
        tfKodeMk.setText(tbKrs.getItems().get(idx).getKodeMk());
        tfNamaMk.setText(tbKrs.getItems().get(idx).getNamaMk());
        tfKelas.setText(tbKrs.getItems().get(idx).getKelas());
        tfNim.setText(tbKrs.getItems().get(idx).getNim());
        tfNamaMhs.setText(tbKrs.getItems().get(idx).getNamaMhs());
        cbStatus.setValue(tbKrs.getItems().get(idx).getStatus());
        tfKodeMk.requestFocus();
    }

    @FXML
    void update(ActionEvent event) {
        String vKodeMk, vKelas, vNim, vStatus;
        String vKodeMkLama, vKelasLama, vNimLama;
        
        vKodeMk = tfKodeMk.getText();
        vKelas = tfKelas.getText();
        vNim = tfNim.getText();
        vStatus = cbStatus.getValue();

        if (flagEdit == false) {
            AksesDB.addDataKrs(vKodeMk, vKelas, vNim, vStatus);	
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Insert Data Sukses..");
            alert.show();			
        } else {
            int idx = tbKrs.getSelectionModel().getSelectedIndex();
            vKodeMkLama = tbKrs.getItems().get(idx).getKodeMk();
            vKelasLama = tbKrs.getItems().get(idx).getKelas();
            vNimLama = tbKrs.getItems().get(idx).getNim();
            AksesDB.updateDataKrs(vKodeMk, vKelas, vNim, vStatus, vKodeMkLama, vKelasLama, vNimLama);
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
        btnDelete.setDisable(nonAktif);
        btnUpdate.setDisable(!nonAktif);
        btnCancel.setDisable(!nonAktif);
    }     
    
    public void teksAktif(boolean aktif) {
        tfKodeMk.setEditable(aktif);
        tfNamaMk.setEditable(aktif);
        tfKelas.setEditable(aktif);
        tfNim.setEditable(aktif);
        tfNamaMhs.setEditable(aktif);
        cbStatus.setDisable(!aktif);
    }
    
    public void clearTeks() {
        tfKodeMk.setText("");
        tfNamaMk.setText("");
        tfKelas.setText("");
        tfNim.setText("");
        tfNamaMhs.setText("");
        cbStatus.setValue("baru");
    }   
    
    void initTabel() {
        kodeMk.setCellValueFactory(new PropertyValueFactory<Krs, String>("kodeMk"));
        namaMk.setCellValueFactory(new PropertyValueFactory<Krs, String>("namaMk"));
        kelas.setCellValueFactory(new PropertyValueFactory<Krs, String>("kelas"));
        nim.setCellValueFactory(new PropertyValueFactory<Krs, String>("nim"));
        namaMhs.setCellValueFactory(new PropertyValueFactory<Krs, String>("namaMhs"));
        status.setCellValueFactory(new PropertyValueFactory<Krs, String>("status"));
    } 
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listKrs = FXCollections.observableArrayList();        
        initTabel();
        loadData();
        setFilter();
        buttonAktif(false);
        teksAktif(false);
        flagEdit = false;
        
        // Initialize ComboBox
        cbStatus.getItems().addAll("baru", "ulang");
        cbStatus.setValue("baru");
    }      
    
    void loadData() {
        listKrs = AksesDB.getDataKrs();
        tbKrs.setItems(listKrs);
    }      
    
    void setFilter() {
        FilteredList<Krs> filterData = new FilteredList<>(listKrs, b -> true);
        tfCari.textProperty().addListener((observable, oldValue, newValue) -> {
            filterData.setPredicate(Krs -> {
                if (newValue.isEmpty() || newValue == null) {
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();
                if (Krs.getNamaMk().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (Krs.getKodeMk().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (Krs.getNamaMhs().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (Krs.getNim().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else
                    return false;
            });           
        });
        SortedList<Krs> sortedData = new SortedList<>(filterData);
        sortedData.comparatorProperty().bind(tbKrs.comparatorProperty());
        tbKrs.setItems(sortedData);
    }      
}