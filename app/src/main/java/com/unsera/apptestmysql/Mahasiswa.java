package com.unsera.apptestmysql;

public class Mahasiswa {
    String NIM;
    String Nama;
    String Jurusan;
    String blobImage;

    public String getBlobImage() {
        return blobImage;
    }

    public void setBlobImage(String blobImage) {
        this.blobImage = blobImage;
    }

    public String getNIM() {
        return NIM;
    }

    public void setNIM(String NIM) {
        this.NIM = NIM;
    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

    public String getJurusan() {
        return Jurusan;
    }

    public void setJurusan(String jurusan) {
        Jurusan = jurusan;
    }
}
