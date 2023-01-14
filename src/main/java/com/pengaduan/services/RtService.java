package com.pengaduan.services;

public class RtService {

    private int noRt;
    private int rtId;

    public RtService(int rtId, int noRt){
        this.rtId = rtId;
        this.noRt = noRt;
    }


    public int getNoRt(){
        return noRt;
    }

    public void setNoRt(int noRt){
        this.noRt = noRt;
    }

    public int getRtId(){
        return rtId;
    }

    public void setRtId(int rtId){
        this.rtId = rtId;
    }
}
