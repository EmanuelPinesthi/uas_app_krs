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

public class MhsSearchKrsController implements Initializable {
    ObservableList<MhsSearch> listMhs;
    Stage stage;

    @FXML
    private Label lblErr;

    @FXML
    private Button btnPilih;

    @FXML
    private TextField tfCariNama;

    @FXML
    private TableColumn<MhsSearch, String> nim;

    @FXML
    private TableColumn<MhsSearch, String> nama;

    @FXML
    private TableColumn<MhsSearch, String> alamat;

    @FXML
    private TableView<MhsSearch> tvMhs;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listMhs = FXCollections.observableArrayList();
        initTabel();
        loadData();
        setFilter();
    }

    void initTabel() {
        nim.setCellValueFactory(new PropertyValueFactory<MhsSearch, String>("nim"));
        nama.setCellValueFactory(new PropertyValueFactory<MhsSearch, String>("nama"));
        alamat.setCellValueFactory(new PropertyValueFactory<MhsSearch, String>("alamat"));
    }

    void loadData() {
        listMhs = AksesDB.getDataMhsSearch();
        tvMhs.setItems(listMhs);
    }

    void setFilter() {
        FilteredList<MhsSearch> filterData = new FilteredList<>(listMhs, b -> true);
        tfCariNama.textProperty().addListener((observable, oldValue, newValue) -> {
            filterData.setPredicate(mhs -> {
                if (newValue.isEmpty() || newValue == null) {
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();
                if (mhs.getNama().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (mhs.getNim().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else
                    return false;
            });
        });
        SortedList<MhsSearch> sortedData = new SortedList<>(filterData);
        sortedData.comparatorProperty().bind(tvMhs.comparatorProperty());
        tvMhs.setItems(sortedData);
    }

    @FXML
    void pilih(ActionEvent event) {
        stage = (Stage) btnPilih.getScene().getWindow();
        MhsSearch m = tvMhs.getSelectionModel().getSelectedItem();
        if (m != null) {
            stage.setUserData(m);
            stage.close();
        } else {
            lblErr.setText("Silakan pilih mahasiswa terlebih dahulu!");
        }
    }
}