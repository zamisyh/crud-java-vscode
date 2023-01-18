package com.pengaduan.services;

public class AcaraService {

    private int acaraId;
    private int noRtId;
    private String judulAcara;
    private String tanggalMulai;
    private String deskripsiAcara;
    private String jamMulai;
    private String jamSelesai;

    public AcaraService(int acaraId, String judulAcara, int noRtId, String deskripsiAcara, String tanggalMulai, String jamMulai, String jamSelesai){
        this.acaraId = acaraId;
        this.judulAcara = judulAcara;
        this.noRtId = noRtId;
        this.deskripsiAcara = deskripsiAcara;
        this.tanggalMulai = tanggalMulai;
        this.jamMulai = jamMulai;
        this.jamSelesai = jamSelesai;
    }

    public int getAcaraId(){
        return acaraId;
    }

    public void setAcaraId(int acaraId){
        this.acaraId = acaraId;
    }

    public String getJudulAcara(){
        return judulAcara;
    }

    public void setJudulAcara(String judulAcara){
        this.judulAcara = judulAcara;
    }

    public int getNoRtId(){
        return noRtId;
    }

    public void setNoRtId(int noRtId){
        this.noRtId = noRtId;
    }

    public String getTanggalMulai(){
        return tanggalMulai;
    }

    public void setTanggalMulai(String tanggalMulai){
        this.tanggalMulai = tanggalMulai;
    }

    public String getDeskripsiAcara(){
        return deskripsiAcara;
    }

    public void setDeskripsiAcara(String deskripsiAcara){
        this.deskripsiAcara = deskripsiAcara;
    }

    public String getJamMulai(){
        return jamMulai;
    }

    public void setJamMulai(String jamMulai){
        this.jamMulai = jamMulai;
    }

    public String getJamSelesai(){
        return jamSelesai;
    }

    public void setJamSelesai(String jamSelesai){
        this.jamSelesai = jamSelesai;
    }
}
