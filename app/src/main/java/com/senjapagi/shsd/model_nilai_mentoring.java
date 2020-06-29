package com.senjapagi.shsd;

public class model_nilai_mentoring {
    String tipe,judul,nilai_kultum,nilai_kehadiran;

    public model_nilai_mentoring(String tipe, String judul, String nilai_kultum, String nilai_kehadiran) {
        this.tipe = tipe;
        this.judul = judul;
        this.nilai_kultum = nilai_kultum;
        this.nilai_kehadiran = nilai_kehadiran;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getNilai_kultum() {
        return nilai_kultum;
    }

    public void setNilai_kultum(String nilai_kultum) {
        this.nilai_kultum = nilai_kultum;
    }

    public String getNilai_kehadiran() {
        return nilai_kehadiran;
    }

    public void setNilai_kehadiran(String nilai_kehadiran) {
        this.nilai_kehadiran = nilai_kehadiran;
    }
}
