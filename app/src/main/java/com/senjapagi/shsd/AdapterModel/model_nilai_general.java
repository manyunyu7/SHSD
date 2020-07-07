package com.senjapagi.shsd.AdapterModel;

public class model_nilai_general {
    String judul, nilai;

    public model_nilai_general(String judul, String nilai) {
        this.judul = judul;
        this.nilai = nilai;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getNilai() {
        return nilai;
    }

    public void setNilai(String nilai) {
        this.nilai = nilai;
    }
}

