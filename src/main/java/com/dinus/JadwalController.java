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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Node;

public class JadwalController implements Initializable {
    ObservableList<Jadwal> listJadwal ;
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
    private Button btnPilih;

    @FXML
    private TableColumn<Jadwal, String> hari;

    @FXML
    private TableColumn<Jadwal, String> jam;

    @FXML
    private TableColumn<Jadwal, String> kelas;

    @FXML
    private TableColumn<Jadwal, String> kodeMk;

    @FXML
    private TableColumn<Jadwal, String> namaMk;

    @FXML
    private TableColumn<Jadwal, String> ruang;

    @FXML
    private TableView<Jadwal> tbJadwal;

    @FXML
    private TextField tfHari;

    @FXML
    private TextField tfJam;

    @FXML
    private TextField tfKelas;

    @FXML
    private TextField tfKodematkul;

    @FXML
    private TextField tfNmMatkul;

    @FXML
    private TextField tfRuang;
    
    @FXML
    void pilihMatkul(ActionEvent event) {
        //
       Stage stage = new Stage();
       Parent root;       
        try {
            root = FXMLLoader.load(MatkulSearchController.class.getResource("fmatkulSearch.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Daftar Matakuliah");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node)event.getSource()).getScene().getWindow() );
            stage.showAndWait();         
            Matakuliah m ;
            m = (Matakuliah) stage.getUserData();            
            tfKodematkul.setText(m.getKodeMk());
            tfNmMatkul.setText(m.getNamaMk());
        } catch (IOException ex) {
            Logger.getLogger(JadwalController.class.getName()).log(Level.SEVERE, null, ex);
        }         
        //
    }
    @FXML
    void add(ActionEvent event) {
        flagEdit=false;
        teksAktif(true);
        buttonAktif(true);
        tfKodematkul.requestFocus();
    }

    @FXML
    void cancel(ActionEvent event) {
        teksAktif(false);
        clearTeks();
        buttonAktif(false);
    }

    @FXML
    void delete(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "hapus data ?", ButtonType.YES,  ButtonType.CANCEL);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            int idx=tbJadwal.getSelectionModel().getSelectedIndex();            
            String vKodeMk=tbJadwal.getItems().get(idx).getKodeMk();
            String vKelas=tbJadwal.getItems().get(idx).getKelas();
            AksesDB.delDataJadwal(vKodeMk,vKelas);
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
        flagEdit=true;			
        int idx=tbJadwal.getSelectionModel().getSelectedIndex();
        tfKodematkul.setText(tbJadwal.getItems().get(idx).getKodeMk());
        tfKelas.setText(tbJadwal.getItems().get(idx).getKelas());
        tfNmMatkul.setText(tbJadwal.getItems().get(idx).getNamaMk());        
        tfHari.setText(tbJadwal.getItems().get(idx).getHari());
        tfJam.setText(tbJadwal.getItems().get(idx).getJam());
        tfRuang.setText(tbJadwal.getItems().get(idx).getRuang());
        tfKodematkul.requestFocus();
    }

    @FXML
    void update(ActionEvent event) {
       String vKelas,vKodeMk,vHari,vJam,vRuang;
        vKelas=tfKelas.getText();
        vKodeMk=tfKodematkul.getText();
        //vNamaMk=tfNmMatkul.getText();
        vHari=tfHari.getText();
        vJam=tfJam.getText();
        vRuang=tfRuang.getText();

        if (flagEdit==false){
            AksesDB.addDataJadwal( vKodeMk,vKelas, vHari, vJam, vRuang);	
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Insert Data Sukses..");
            alert.show();			
        }else {
            int idx=tbJadwal.getSelectionModel().getSelectedIndex();
            vKelas=tbJadwal.getItems().get(idx).getKelas();
            AksesDB.updateDataJadwal(vKodeMk, vKelas, vHari, vJam, vRuang);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Update Data Berhasil");
            alert.show();
        }
        loadData();
        flagEdit=false;
        teksAktif(false);
        clearTeks();
        buttonAktif(false);    
    }
	public void buttonAktif(boolean nonAktif){
		btnAdd.setDisable(nonAktif);
		btnEdit.setDisable(nonAktif);
		btnDelete.setDisable(nonAktif);
		btnUpdate.setDisable(!nonAktif);
		btnCancel.setDisable(!nonAktif);
	}     
	public void teksAktif(boolean aktif){
		tfKelas.setEditable(aktif);
		tfKodematkul.setEditable(aktif);
		tfNmMatkul.setEditable(aktif);
        tfHari.setEditable(aktif);
        tfJam.setEditable(aktif);
        tfRuang.setEditable(aktif);
	}
	public void clearTeks(){
		tfKelas.setText("");
		tfKodematkul.setText("");
		tfNmMatkul.setText("");
        tfHari.setText("");
        tfJam.setText("");
        tfRuang.setText("");
	}   
    void initTabel(){
        kelas.setCellValueFactory(new PropertyValueFactory<Jadwal,String>("kelas"));
        kodeMk.setCellValueFactory(new PropertyValueFactory<Jadwal,String>("kodeMk"));
        namaMk.setCellValueFactory(new PropertyValueFactory<Jadwal,String>("namaMk"));
        hari.setCellValueFactory(new PropertyValueFactory<Jadwal,String>("hari"));
        jam.setCellValueFactory(new PropertyValueFactory<Jadwal,String>("jam"));
        ruang.setCellValueFactory(new PropertyValueFactory<Jadwal,String>("ruang"));
    } 
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listJadwal = FXCollections.observableArrayList() ;        
        initTabel();
        loadData();
        setFilter();
        buttonAktif(false);
        teksAktif(false);
        flagEdit=false;
    }      
    void loadData(){
        listJadwal=AksesDB.getDataJadwal();
        tbJadwal.setItems(listJadwal);
    }      
    void setFilter(){
        FilteredList<Jadwal> filterData= new FilteredList<>(listJadwal,b->true);
        tfCari.textProperty().addListener((observable,oldValue,newValue)->{
        filterData.setPredicate(Jadwal->{
          if (newValue.isEmpty()  || newValue==null){
             return true;
           }
          String searchKeyword=newValue.toLowerCase();
          if (Jadwal.getNamaMk().toLowerCase().indexOf(searchKeyword)> -1){
              return true;
          }else if (Jadwal.getKodeMk().toLowerCase().indexOf(searchKeyword)>-1){
              return true;
          }else
              return false;
       });           
       });
       SortedList<Jadwal> sortedData = new SortedList<>(filterData);
       sortedData.comparatorProperty().bind(tbJadwal.comparatorProperty());
       tbJadwal.setItems(sortedData);
    }      
}
