package com.dinus;

public class Krs {
    private String kodeMk;
    private String kelas;
    private String nim;
    private String namaMk;
    private String namaMhs;
    private String status;
    
    // constructor
    public Krs(String kodeMk, String kelas, String nim, String namaMk, String namaMhs, String status) {
        this.kodeMk = kodeMk;
        this.kelas = kelas;
        this.nim = nim;
        this.namaMk = namaMk;
        this.namaMhs = namaMhs;
        this.status = status;
    }
    
    public String getKodeMk() {
        return kodeMk;
    }
    
    public String getKelas() {
        return kelas;
    }
    
    public String getNim() {
        return nim;
    }
    
    public String getNamaMk() {
        return namaMk;
    }
    
    public String getNamaMhs() {
        return namaMhs;
    }
    
    public String getStatus() {
        return status;
    }
}