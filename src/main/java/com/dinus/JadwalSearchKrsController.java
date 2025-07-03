package com.dinus;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class JadwalSearchKrsController implements Initializable {
    ObservableList<JadwalSearch> listJadwal;
    Stage stage;

    @FXML
    private Label lblErr;

    @FXML
    private Button btnPilih;

    @FXML
    private TextField tfCariNama;

    @FXML
    private TableColumn<JadwalSearch, String> kodeJadwal;

    @FXML
    private TableColumn<JadwalSearch, String> kodeMk;

    @FXML
    private TableColumn<JadwalSearch, String> namaMk;

    @FXML
    private TableColumn<JadwalSearch, String> kelas;

    @FXML
    private TableColumn<JadwalSearch, String> hari;

    @FXML
    private TableColumn<JadwalSearch, String> jam;

    @FXML
    private TableColumn<JadwalSearch, String> ruang;

    @FXML
    private TableView<JadwalSearch> tvJadwal;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listJadwal = FXCollections.observableArrayList();
        initTabel();
        loadData();
        setFilter();
    }

    void initTabel() {
        kodeJadwal.setCellValueFactory(new PropertyValueFactory<JadwalSearch, String>("kodeJadwal"));
        kodeMk.setCellValueFactory(new PropertyValueFactory<JadwalSearch, String>("kodeMk"));
        namaMk.setCellValueFactory(new PropertyValueFactory<JadwalSearch, String>("namaMk"));
        kelas.setCellValueFactory(new PropertyValueFactory<JadwalSearch, String>("kelas"));
        hari.setCellValueFactory(new PropertyValueFactory<JadwalSearch, String>("hari"));
        jam.setCellValueFactory(new PropertyValueFactory<JadwalSearch, String>("jam"));
        ruang.setCellValueFactory(new PropertyValueFactory<JadwalSearch, String>("ruang"));
    }

    void loadData() {
        listJadwal = AksesDB.getDataJadwalSearch();
        tvJadwal.setItems(listJadwal);
    }

    void setFilter() {
        FilteredList<JadwalSearch> filterData = new FilteredList<>(listJadwal, b -> true);
        tfCariNama.textProperty().addListener((observable, oldValue, newValue) -> {
            filterData.setPredicate(jadwal -> {
                if (newValue.isEmpty() || newValue == null) {
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();
                if (jadwal.getNamaMk().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (jadwal.getKodeMk().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (jadwal.getKodeJadwal().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else
                    return false;
            });
        });
        SortedList<JadwalSearch> sortedData = new SortedList<>(filterData);
        sortedData.comparatorProperty().bind(tvJadwal.comparatorProperty());
        tvJadwal.setItems(sortedData);
    }

    @FXML
    void pilih(ActionEvent event) {
        stage = (Stage) btnPilih.getScene().getWindow();
        JadwalSearch j = tvJadwal.getSelectionModel().getSelectedItem();
        if (j != null) {
            stage.setUserData(j);
            stage.close();
        } else {
            lblErr.setText("Silakan pilih jadwal terlebih dahulu!");
        }
    }
}