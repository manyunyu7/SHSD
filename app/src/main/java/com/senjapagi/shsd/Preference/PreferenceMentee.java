package com.senjapagi.shsd.Preference;

import android.content.Context;
import android.content.SharedPreferences;

import com.senjapagi.shsd.ConstantMentee;

public class PreferenceMentee {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;
    int private_mode = 0;
    private static final String PREF_NAME="pref_mentee";
    String nim, nama, mentee_id,fakultas,jurusan,kelompok,mentor_name,
    mentor_line,mentor_telp,password,mentee_line,mentee_telp;

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getMentee_id() {
        return mentee_id;
    }

    public void setMentee_id(String mentee_id) {
        this.mentee_id = mentee_id;
    }

    public String getFakultas() {
        return fakultas;
    }

    public void setFakultas(String fakultas) {
        this.fakultas = fakultas;
    }

    public String getJurusan() {
        return jurusan;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }

    public String getKelompok() {
        return kelompok;
    }

    public void setKelompok(String kelompok) {
        this.kelompok = kelompok;
    }

    public String getMentor_name() {
        return mentor_name;
    }

    public void setMentor_name(String mentor_name) {
        this.mentor_name = mentor_name;
    }

    public String getMentor_line() {
        return mentor_line;
    }

    public void setMentor_line(String mentor_line) {
        this.mentor_line = mentor_line;
    }

    public String getMentor_telp() {
        return mentor_telp;
    }

    public void setMentor_telp(String mentor_telp) {
        this.mentor_telp = mentor_telp;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMentee_line() {
        return mentee_line;
    }

    public void setMentee_line(String mentee_line) {
        this.mentee_line = mentee_line;
    }

    public String getMentee_telp() {
        return pref.getString(ConstantMentee.mentee_telp,null);
    }

    public void setMentee_telp(String mentee_telp) {
        editor.putString(ConstantMentee.mentee_telp,mentee_telp);
        editor.commit();
    }

    public PreferenceMentee(Context context){
        this.context=context;
        pref=context.getSharedPreferences(PREF_NAME,0);
        editor= pref.edit();
    }

    public void setID(String id){
        editor.putString("id",id);
        editor.commit();
    }
    public String getID(){
        return pref.getString("id",null);
    }

    public void logout(){
        editor.clear();
        editor.apply();
    }
}
