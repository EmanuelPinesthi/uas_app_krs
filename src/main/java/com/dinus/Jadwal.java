package com.dinus;

public class Jadwal {
    String kelas;
    String kodeMk;
    String namaMk;
    String hari;
    String jam;
    String ruang;
    Jadwal(String kodeMk,String namaMk,String kelas,String hari,String jam,String ruang){
        this.kelas=kelas;
        this.kodeMk=kodeMk;
        this.namaMk=namaMk;
        this.hari=hari;
        this.jam=jam;
        this.ruang=ruang;

    }
    public String getKodeMk(){
        return kodeMk;
    }
    public String getKelas(){
        return kelas;
    }
    public String getNamaMk(){
        return namaMk;
    }
    public String getHari(){
        return hari;
    }
    public String getJam(){
        return jam;
    }
    public String getRuang(){
        return ruang;
    }
}
