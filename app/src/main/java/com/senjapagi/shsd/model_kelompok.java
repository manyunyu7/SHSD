package com.senjapagi.shsd;

public class model_kelompok {
    String id,kode,nama,line,kontak,idGrup;

    public model_kelompok(String id, String kode, String nama, String line, String kontak, String idGrup) {
        this.id = id;
        this.kode = kode;
        this.nama = nama;
        this.line = line;
        this.kontak = kontak;
        this.idGrup = idGrup;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getKontak() {
        return kontak;
    }

    public void setKontak(String kontak) {
        this.kontak = kontak;
    }

    public String getIdGrup() {
        return idGrup;
    }

    public void setIdGrup(String idGrup) {
        this.idGrup = idGrup;
    }
}

