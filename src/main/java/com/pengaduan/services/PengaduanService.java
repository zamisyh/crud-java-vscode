package com.pengaduan.services;

public class PengaduanService {

    private int pengaduanId;
    private String judulPengaduan;
    private String namaWarga;
    private String tanggalPengaduan;
    private String deskripsiPengaduan;

    public PengaduanService(int pengaduanId, String judulPengaduan, String namaWarga, String tanggalPengaduan, String deskripsiPengaduan){
        this.pengaduanId = pengaduanId;
        this.judulPengaduan = judulPengaduan;
        this.namaWarga = namaWarga;
        this.tanggalPengaduan = tanggalPengaduan;
        this.deskripsiPengaduan = deskripsiPengaduan;
    }


    public int getPengaduanId(){ return pengaduanId; }
    public void setPengaduanId(int pengaduanId){ this.pengaduanId = pengaduanId; }
    
    public String getJudulPengaduan() { return judulPengaduan; }
    public void setJudulPengaduan(String judulPengaduan) { this.judulPengaduan = judulPengaduan; }
    
    public String getNamaWarga() { return namaWarga; }
    public void setNamaWarga(String namaWarga) { this.namaWarga = namaWarga; }

    
    public String getTanggalPengaduan() { return tanggalPengaduan; }
    public void setTanggalPengaduan(String tanggalPengaduan) { this.tanggalPengaduan = tanggalPengaduan; }
    
    public String getDeskripsiPengaduan() { return deskripsiPengaduan; }
    public void setDeskripsiPengaduan(String deskripsiPengaduan) { this.deskripsiPengaduan = deskripsiPengaduan; }
    
}
