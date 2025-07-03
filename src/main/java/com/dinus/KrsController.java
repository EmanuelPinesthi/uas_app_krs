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
import javafx.scene.Node;
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

public class KrsController implements Initializable {
    ObservableList<Krs> listKrs;
    boolean flagEdit;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnPilihJadwal;

    @FXML
    private Button btnPilihMhs;

    @FXML
    private Button btnUpdate;

    @FXML
    private ComboBox<String> cbStatus;

    @FXML
    private TableColumn<Krs, String> kodeJadwal;

    @FXML
    private TableColumn<Krs, String> kodeMk;

    @FXML
    private TableColumn<Krs, String> namaMhs;

    @FXML
    private TableColumn<Krs, String> namaMk;

    @FXML
    private TableColumn<Krs, String> nim;

    @FXML
    private TableColumn<Krs, String> status;

    @FXML
    private TableView<Krs> tbKrs;

    @FXML
    private TextField tfCari;

    @FXML
    private TextField tfKodeJadwal;

    @FXML
    private TextField tfNim;

    @FXML
    private TextField tfNmMatkul;

    @FXML
    private TextField tfNmMhs;

    // Variables untuk menyimpan data yang dipilih
    private String selectedKodeMk;
    private String selectedKelas;
    private String originalKodeMk;
    private String originalKelas;
    private String originalNim;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listKrs = FXCollections.observableArrayList();
        initTabel();
        initComboBox();
        loadData();
        setFilter();
        buttonAktif(false);
        teksAktif(false);
        flagEdit = false;
        
        // Set initial state
        clearTeks();
        
        // Add selection listener to table
        tbKrs.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null && !flagEdit) {
                // Enable edit and delete buttons when an item is selected
                btnEdit.setDisable(false);
                btnDelete.setDisable(false);
            }
        });
    }

    private void initComboBox() {
        cbStatus.getItems().addAll("baru", "ulang");
        cbStatus.setValue("baru");
    }

    @FXML
    void pilihJadwal(ActionEvent event) {
        Stage stage = new Stage();
        Parent root;
        try {
            root = FXMLLoader.load(JadwalSearchKrsController.class.getResource("fJadwalSearchKrs.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Daftar Jadwal");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) event.getSource()).getScene().getWindow());
            stage.showAndWait();
            JadwalSearch j;
            j = (JadwalSearch) stage.getUserData();
            if (j != null) {
                tfKodeJadwal.setText(j.getKodeJadwal());
                tfNmMatkul.setText(j.getNamaMk());
                selectedKodeMk = j.getKodeMk();
                selectedKelas = j.getKelas();
            }
        } catch (IOException ex) {
            Logger.getLogger(KrsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void pilihMhs(ActionEvent event) {
        Stage stage = new Stage();
        Parent root;
        try {
            root = FXMLLoader.load(MhsSearchKrsController.class.getResource("fMhsSearchKrs.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Daftar Mahasiswa");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) event.getSource()).getScene().getWindow());
            stage.showAndWait();
            MhsSearch m;
            m = (MhsSearch) stage.getUserData();
            if (m != null) {
                tfNim.setText(m.getNim());
                tfNmMhs.setText(m.getNama());
            }
        } catch (IOException ex) {
            Logger.getLogger(KrsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void add(ActionEvent event) {
        flagEdit = false;
        teksAktif(true);
        buttonAktif(true);
        clearTeks();
        tfKodeJadwal.requestFocus();
    }

    @FXML
    void cancel(ActionEvent event) {
        teksAktif(false);
        clearTeks();
        buttonAktif(false);
    }

    @FXML
    void delete(ActionEvent event) {
        int selectedIndex = tbKrs.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Silakan pilih data yang akan dihapus!");
            alert.show();
            return;
        }
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Hapus data KRS?", ButtonType.YES, ButtonType.CANCEL);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            Krs selectedKrs = tbKrs.getItems().get(selectedIndex);
            AksesDB.delDataKrs(selectedKrs.getKodeMk(), selectedKrs.getKelas(), selectedKrs.getNim());
            
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setContentText("Delete Data Sukses..");
            alert2.show();
            loadData();
        }
    }

    @FXML
    void edit(ActionEvent event) {
        int selectedIndex = tbKrs.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Silakan pilih data yang akan diedit!");
            alert.show();
            return;
        }
        
        buttonAktif(true);
        teksAktif(true);
        flagEdit = true;
        
        Krs selectedKrs = tbKrs.getItems().get(selectedIndex);
        
        // Set form fields
        tfKodeJadwal.setText(selectedKrs.getKodeJadwal());
        tfNmMatkul.setText(selectedKrs.getNamaMk());
        tfNim.setText(selectedKrs.getNim());
        tfNmMhs.setText(selectedKrs.getNamaMhs());
        cbStatus.setValue(selectedKrs.getStatus());
        
        // Set selected values for new data
        selectedKodeMk = selectedKrs.getKodeMk();
        selectedKelas = selectedKrs.getKelas();
        
        // Store original values for update
        originalKodeMk = selectedKrs.getKodeMk();
        originalKelas = selectedKrs.getKelas();
        originalNim = selectedKrs.getNim();
        
        tfKodeJadwal.requestFocus();
    }

    @FXML
    void update(ActionEvent event) {
        // Validasi input
        if (selectedKodeMk == null || selectedKelas == null || tfNim.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Silakan lengkapi semua data!");
            alert.show();
            return;
        }
        
        String nim = tfNim.getText().trim();
        String status = cbStatus.getValue();

        if (flagEdit == false) {
            // Add new data
            try {
                AksesDB.addDataKrs(selectedKodeMk, selectedKelas, nim, status);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Insert Data Sukses..");
                alert.show();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Error: " + e.getMessage());
                alert.show();
                return;
            }
        } else {
            // Update existing data
            try {
                AksesDB.updateDataKrs(selectedKodeMk, selectedKelas, nim, status, 
                                    originalKodeMk, originalKelas, originalNim);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Update Data Berhasil");
                alert.show();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Error: " + e.getMessage());
                alert.show();
                return;
            }
        }
        
        loadData();
        flagEdit = false;
        teksAktif(false);
        clearTeks();
        buttonAktif(false);
    }

    public void buttonAktif(boolean nonAktif) {
        btnAdd.setDisable(nonAktif);
        btnEdit.setDisable(true); // Will be enabled when selecting from table
        btnDelete.setDisable(true); // Will be enabled when selecting from table
        btnUpdate.setDisable(!nonAktif);
        btnCancel.setDisable(!nonAktif);
    }

    public void teksAktif(boolean aktif) {
        // Jadwal fields - hanya bisa diedit melalui dialog pencarian
        tfKodeJadwal.setEditable(false);
        tfNmMatkul.setEditable(false);
        btnPilihJadwal.setDisable(!aktif);
        
        // Mahasiswa fields - hanya bisa diedit melalui dialog pencarian  
        tfNim.setEditable(false);
        tfNmMhs.setEditable(false);
        btnPilihMhs.setDisable(!aktif);
        
        // Status - bisa diedit langsung
        cbStatus.setDisable(!aktif);
    }

    public void clearTeks() {
        tfKodeJadwal.setText("");
        tfNmMatkul.setText("");
        tfNim.setText("");
        tfNmMhs.setText("");
        cbStatus.setValue("baru");
        selectedKodeMk = null;
        selectedKelas = null;
        originalKodeMk = null;
        originalKelas = null;
        originalNim = null;
    }

    void initTabel() {
        kodeJadwal.setCellValueFactory(new PropertyValueFactory<Krs, String>("kodeJadwal"));
        kodeMk.setCellValueFactory(new PropertyValueFactory<Krs, String>("kodeMk"));
        namaMk.setCellValueFactory(new PropertyValueFactory<Krs, String>("namaMk"));
        nim.setCellValueFactory(new PropertyValueFactory<Krs, String>("nim"));
        namaMhs.setCellValueFactory(new PropertyValueFactory<Krs, String>("namaMhs"));
        status.setCellValueFactory(new PropertyValueFactory<Krs, String>("status"));
    }

    void loadData() {
        listKrs = AksesDB.getDataKrs();
        if (listKrs != null) {
            tbKrs.setItems(listKrs);
            setFilter(); // Refresh the filter
        }
    }

    void setFilter() {
        FilteredList<Krs> filterData = new FilteredList<>(listKrs, b -> true);
        tfCari.textProperty().addListener((observable, oldValue, newValue) -> {
            filterData.setPredicate(krs -> {
                if (newValue.isEmpty() || newValue == null) {
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();
                if (krs.getNamaMk().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (krs.getKodeMk().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (krs.getNamaMhs().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (krs.getNim().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else
                    return false;
            });
        });
        SortedList<Krs> sortedData = new SortedList<>(filterData);
        sortedData.comparatorProperty().bind(tbKrs.comparatorProperty());
        tbKrs.setItems(sortedData);
    }

    // Helper methods untuk mengekstrak kode_mk dan kelas dari kode jadwal
    private String getKodeMkFromKodeJadwal(String kodeJadwal) {
        if (kodeJadwal != null && kodeJadwal.contains("-")) {
            return kodeJadwal.split("-")[0];
        }
        return "";
    }

    private String getKelasFromKodeJadwal(String kodeJadwal) {
        if (kodeJadwal != null && kodeJadwal.contains("-")) {
            return kodeJadwal.split("-")[1];
        }
        return "";
    }
}