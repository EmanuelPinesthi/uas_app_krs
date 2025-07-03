package com.dinus;

public class Dosen {
    private String kodeDsn;
    private String namaDsn;
    
    // constructor
    public Dosen(String kodeDsn, String namaDsn) {
        this.kodeDsn = kodeDsn;
        this.namaDsn = namaDsn;
    }
    
    public String getKodeDsn() {
        return kodeDsn;
    }
    
    public String getNamaDsn() {
        return namaDsn;
    }
}