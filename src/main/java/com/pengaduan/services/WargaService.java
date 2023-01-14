package com.pengaduan.services;

public class WargaService {
    
    private String nama;
    private int noRt;
    private int umur;
    private String agama;
    private String jenisKelamin;
    private int noTelp;
    private String tanggalLahir;

    public WargaService(String nama, int noRt, int umur, String agama, String jenisKelamin, int noTelp, String tanggalLahir){
        this.nama = nama;
        this.noRt = noRt;
        this.umur = umur;
        this.agama = agama;
        this.jenisKelamin = jenisKelamin;
        this.noTelp = noTelp;
        this.tanggalLahir = tanggalLahir;
    }

    public String getNama(){
        return nama;
    }

    public void setNama(String nama){
        this.nama = nama;
    }

    public int getNoRt(){
        return noRt;
    }

    public void setNoRt(int noRt){
        this.noRt = noRt;
    }

    public int getUmur(){
        return umur;
    }

    public void setUmur(int umur){
        this.umur = umur;
    }

    public String getAgama(){
        return agama;
    }

    public void setAgama(String agama){
        this.agama = agama;
    }

    public String getJenisKelamin(){
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin){
        this.jenisKelamin = jenisKelamin;
    }

    public String getTanggalLahir(){
        return tanggalLahir;
    }

    public int getNoTelp(){
        return noTelp;
    }

    public void setNoTelp(int noTelp){
        this.noTelp = noTelp;
    }

    public void setTanggalLahir(String tanggalLahir){
        this.tanggalLahir = tanggalLahir;
    }

}
