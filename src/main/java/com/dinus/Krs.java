package com.dinus;

public class Krs {
    private String kodeJadwal;
    private String kodeMk;
    private String namaMk;
    private String nim;
    private String namaMhs;
    private String kelas;
    private String status;
    
    // Constructor
    public Krs(String kodeJadwal, String kodeMk, String namaMk, String nim, String namaMhs, String kelas, String status) {
        this.kodeJadwal = kodeJadwal;
        this.kodeMk = kodeMk;
        this.namaMk = namaMk;
        this.nim = nim;
        this.namaMhs = namaMhs;
        this.kelas = kelas;
        this.status = status;
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
    
    public String getNim() {
        return nim;
    }
    
    public String getNamaMhs() {
        return namaMhs;
    }
    
    public String getKelas() {
        return kelas;
    }
    
    public String getStatus() {
        return status;
    }
    
    // Setters
    public void setKodeJadwal(String kodeJadwal) {
        this.kodeJadwal = kodeJadwal;
    }
    
    public void setKodeMk(String kodeMk) {
        this.kodeMk = kodeMk;
    }
    
    public void setNamaMk(String namaMk) {
        this.namaMk = namaMk;
    }
    
    public void setNim(String nim) {
        this.nim = nim;
    }
    
    public void setNamaMhs(String namaMhs) {
        this.namaMhs = namaMhs;
    }
    
    public void setKelas(String kelas) {
        this.kelas = kelas;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
}