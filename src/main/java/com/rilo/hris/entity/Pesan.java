package com.rilo.hris.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "pesan")
public class Pesan {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="id_pesan")
    private int idPesan;

    @Column(name="status")
    private int status;

    @Column(name="dari")
    private int dari;

    @Column(name="kepada")
    private int kepada;

    @Column(name="flag")
    private int flag;

    @Column(name="tanggal")
    private Date tanggal;

    @Column(name="tingkat")
    private int tingkat;

    @Column(name="tipe")
    private int tipe;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPesan() {
        return idPesan;
    }

    public void setIdPesan(int idPesan) {
        this.idPesan = idPesan;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getDari() {
        return dari;
    }

    public void setDari(int dari) {
        this.dari = dari;
    }

    public int getKepada() {
        return kepada;
    }

    public void setKepada(int kepada) {
        this.kepada = kepada;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public int getTingkat() {
        return tingkat;
    }

    public void setTingkat(int tingkat) {
        this.tingkat = tingkat;
    }

    public int getTipe() {
        return tipe;
    }

    public void setTipe(int tipe) {
        this.tipe = tipe;
    }


}
