package com.dinus;

public class MhsSearch {
    private String nim;
    private String nama;
    private String alamat;
    
    // Constructor
    public MhsSearch(String nim, String nama, String alamat) {
        this.nim = nim;
        this.nama = nama;
        this.alamat = alamat;
    }
    
    // Getters
    public String getNim() {
        return nim;
    }
    
    public String getNama() {
        return nama;
    }
    
    public String getAlamat() {
        return alamat;
    }
}