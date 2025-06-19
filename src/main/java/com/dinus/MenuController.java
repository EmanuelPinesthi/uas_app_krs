package com.dinus;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class MenuController implements Initializable{
    @FXML
    private Button btnSelesai;

    @FXML
    private Button btnHome;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnMhs;

    @FXML
    private Button btnMatkul;

    @FXML
    private Button btnJadwal;
    @FXML
    private Button btnKrs;

    @FXML
    void home(ActionEvent event) {

    }


    @FXML
    void p1(ActionEvent event) {

    }

    @FXML
    void p2(ActionEvent event) {

    }

    @FXML
    void p3(ActionEvent event) {

    }
    @FXML
    private StackPane contentArea;

    @FXML
    private Label lbTeks;
    public void logout(ActionEvent event){
        DBUtil.changeScene(event, "fLogin.fxml", "Login", null);
    }
    public void initialize(URL location,ResourceBundle resources) {
        /* 
        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("fJual.fxml"));
            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(fxml);
            
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        */
    }
    @FXML
    public void homee() throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("fHome.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }
    @FXML
    public void mhs() throws IOException {
        //Parent fxml = FXMLLoader.load(getClass().getResource("fproductSearch.fxml"));
        Parent fxml = FXMLLoader.load(getClass().getResource("fmhs.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }
    @FXML
    public void matkul() throws IOException {
        //Parent fxml = FXMLLoader.load(getClass().getResource("fJadwal.fxml"));
        Parent fxml = FXMLLoader.load(getClass().getResource("fMatkul.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }
    @FXML
    public void jadwal() throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("fJadwal.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }
    @FXML
    public void krs() throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("fKrs.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }
    @FXML
    public void selesai(ActionEvent event) throws IOException {
         DBUtil.changeScene(event, "fLogin.fxml", "Login", null);
    }
    public void setUserInfo(String userName) {
       lbTeks.setText("Selamat Datang " + userName);
    }
}
