package com.dinus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;


public class AksesDB {
	public static ObservableList<Mhs> getDataMhs(){
		ObservableList<Mhs> listMhs = FXCollections.observableArrayList();
		try {
			Connection c = KoneksiDB.getConn();
			String sql="select * from mhs";
			PreparedStatement ps =c.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				Mhs m = new Mhs(rs.getString("nim"),rs.getString("nama"),rs.getString("alamat"));
				listMhs.add(m);
			}
			return listMhs;
		}catch (SQLException ex) {
			Logger.getLogger(AksesDB.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}
	public static void addDataMhs(String nim,String nama,String alamat){
		String sql="insert into mhs(nim,nama,alamat) values (?,?,?)";
            try {
				Connection c = KoneksiDB.getConn();
                PreparedStatement st = c.prepareStatement(sql);        
                st.setString(1,nim);
                st.setString(2,nama);
				st.setString(3,alamat);
                st.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }		
	}
	public static void updateDataMhs(String nim,String nama,String nim_lama,String alamat){
		String sql="update mhs set nim=?,nama=?,alamat=? where nim=?";
            try {
				Connection c = KoneksiDB.getConn();
                PreparedStatement st = c.prepareStatement(sql);        
                st.setString(1,nim);
                st.setString(2,nama);
				st.setString(3,alamat);
				st.setString(4,nim_lama);
                st.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }		
	}	
	public static void delDataMhs(String nim){
		String sql="delete from mhs where nim=?";
            try {
				Connection c = KoneksiDB.getConn();
                PreparedStatement st = c.prepareStatement(sql);        
                st.setString(1,nim);
                st.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }		
	}	
   public static void signUpUser(ActionEvent event,String userName,String password) {
      Connection conn=null;
      PreparedStatement st =null;
      ResultSet rs = null;
      conn= KoneksiDB.getConn();
      String sql="select * from user where username=?";
      try {
         st = conn.prepareStatement(sql);
         st.setString(1, userName);
         rs=st.executeQuery();
         if (rs.isBeforeFirst()) {
            System.out.println("User sdh ada..");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Anda tdk bisa pake user ini");
            alert.show();
         }else{
            sql="insert into user (username,password) values (?,?)";
            st = conn.prepareStatement(sql);
            st.setString(1, userName);
            st.setString(2, password);
            st.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("userberhasil dibuat..");
            alert.show();

            DBUtil.changeScene(event,"fMenu.fxml","SignUp",userName);

         }
      } catch (SQLException e) {
         throw new RuntimeException(e);
      }finally {
         if (rs!=null) {
            try {
               rs.close();
            } catch(SQLException e) {
               e.printStackTrace();
            }
         }
         if (st!=null) {
            try {
               st.close();
            } catch(SQLException e) {
               e.printStackTrace();
            }
         }
         if (conn!=null) {
            try {
               conn.close();
            } catch(SQLException e) {
               e.printStackTrace();
            }
         }
      }

   }
   public static void loginUser(ActionEvent event,String userName,String password) {
      Connection conn = null;
      PreparedStatement st = null;
      ResultSet rs = null;
      String sql;
      try {
         conn= KoneksiDB.getConn();
         sql="select * from user where username=?";
         st = conn.prepareStatement(sql);
         st.setString(1, userName);
         rs = st.executeQuery();
         if (!rs.isBeforeFirst()) {
            //System.out.println("User not found..");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("User Tdk ditemukan");
            alert.show();
         } else {
            while (rs.next()) {
               String rpassword=rs.getString("password");
               if (rpassword.equals(password)) {
                  DBUtil.changeScene(event,"fMenu.fxml","Login Sistem KRS",userName);
                  //changeScene(event, "signUp.fxml", "SignUP", null);
                  //System.out.println("Password  valid..");
               }else {
                  System.out.println("Password  not valid..");
                  Alert alert = new Alert(Alert.AlertType.ERROR);
                  alert.setContentText("Password Salah");
                  alert.show();
               }
            }
         }
      }
      catch(SQLException e) {
         e.printStackTrace();
      } finally {
         if (rs!=null) {
            try {
               rs.close();
            } catch(SQLException e) {
               e.printStackTrace();
            }
         }
         if (st!=null) {
            try {
               st.close();
            } catch(SQLException e) {
               e.printStackTrace();
            }
         }

         if (conn!=null) {
            try {
               conn.close();
            } catch(SQLException e) {
               e.printStackTrace();
            }
         }
      }
   }
public static ObservableList<Jadwal> getDataJadwal(){
		ObservableList<Jadwal> listJadwal = FXCollections.observableArrayList();
		try {
			Connection c = KoneksiDB.getConn();
			//String sql="SELECT j.kode_jadwal,j.kode_mk,m.nama_mk,j.hari,j.jam,j.ruang FROM jadwal j,matakuliah m WHERE j.kode_mk=m.kode_mk";
         String sql="SELECT j.kode_mk,j.kelas,m.nama_mk,j.hari,j.jam,j.ruang FROM jadwal j,matakuliah m WHERE j.kode_mk=m.kode_mk";
			PreparedStatement ps =c.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				//Jadwal j = new Jadwal(rs.getString("kode_jadwal"),rs.getString("kode_mk"),rs.getString("nama_mk"),rs.getString("hari"),rs.getString("jam"),rs.getString("ruang"));
            Jadwal j = new Jadwal(rs.getString("kode_mk"),rs.getString("nama_mk"),rs.getString("kelas"),rs.getString("hari"),rs.getString("jam"),rs.getString("ruang"));
            listJadwal.add(j);
			}
			return listJadwal;
		}catch (SQLException ex) {
			Logger.getLogger(AksesDB.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}	   
 	public static void addDataJadwal(String kodeMk,String kelas,String hari,String jam,String ruang){
		//String sql="insert into jadwal(kode_mk,nama_mk,kelas,hari,jam,ruang) values (?,?,?,?,?,?)";
      String sql="insert into jadwal(kode_mk,kelas,hari,jam,ruang) values (?,?,?,?,?)";
            try {
				Connection c = KoneksiDB.getConn();
                PreparedStatement st = c.prepareStatement(sql);        
                st.setString(1,kodeMk);                
                //st.setString(2,namaMk);
                st.setString(2,kelas);                				    
                st.setString(3,hari);
                st.setString(4,jam);
                st.setString(5,ruang);
                st.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }		
	}  
 	public static void updateDataJadwal(String kodeMk,String kelas,String hari,String jam,String ruang){
		String sql="update jadwal set hari=?,jam=?,ruang=? where kode_mk=? and kelas=? ";
            try {
				Connection c = KoneksiDB.getConn();
                PreparedStatement st = c.prepareStatement(sql);        
                st.setString(4,kodeMk);
                st.setString(5,kelas);                
                //st.setString(1,kodeMk);                
				    //st.setString(2,namaMk);
                st.setString(1,hari);
                st.setString(2,jam);
                st.setString(3,ruang);
                st.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }		
	}  
	public static void delDataJadwal(String kodeMk,String kelas){
		String sql="delete from jadwal where kode_mk=? and  kelas=? ";
            try {
				Connection c = KoneksiDB.getConn();
                PreparedStatement st = c.prepareStatement(sql);        
                st.setString(1,kodeMk);
                st.setString(2,kelas);
                
                st.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }		
	}
	public static ObservableList<Matakuliah> getDataMatakuliah(){
		ObservableList<Matakuliah> listMatakuliah = FXCollections.observableArrayList();
		try {
			Connection c = KoneksiDB.getConn();
			String sql="select * from matakuliah";
			PreparedStatement ps =c.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				Matakuliah  m = new Matakuliah(rs.getString("kode_mk"),rs.getString("nama_mk"),rs.getInt("sks"));
				listMatakuliah.add(m);
			}
			return listMatakuliah;
		}catch (SQLException ex) {
			Logger.getLogger(AksesDB.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}

}
