package com.dinus;

public class JadwalSearch {
    private String kodeJadwal;
    private String kodeMk;
    private String namaMk;
    private String kelas;
    private String hari;
    private String jam;
    private String ruang;
    
    // Constructor
    public JadwalSearch(String kodeJadwal, String kodeMk, String namaMk, String kelas, String hari, String jam, String ruang) {
        this.kodeJadwal = kodeJadwal;
        this.kodeMk = kodeMk;
        this.namaMk = namaMk;
        this.kelas = kelas;
        this.hari = hari;
        this.jam = jam;
        this.ruang = ruang;
    }
    
    // Getters
    public String getKodeJadwal() {
        return kodeJadwal;
    }
    
    public String getKodeMk() {
        return kodeMk;
    }
    
    public String getNamaMk() {
        return namaMk;
    }
    
    public String getKelas() {
        return kelas;
    }
    
    public String getHari() {
        return hari;
    }
    
    public String getJam() {
        return jam;
    }
    
    public String getRuang() {
        return ruang;
    }
}