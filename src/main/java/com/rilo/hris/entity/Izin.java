package com.rilo.hris.entity;

import lombok.Data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "izin")
public class Izin {

    @Id
    @Column(name = "id_izin")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="id_employee")
    private int idPegawai;

    @Column(name="jam_izin")
    private Date jamIzin;

    @Column(name="tgl_izin")
    private Date tanggalIzin;

    @Column(name="keterangan")
    private String keterangan;

    @Column(name="status_izin")
    private int statusIzin;

    @Column(name="tingkat_approval")
    private int tingkatApproval;

    @Column(name="approval_id")
    private int approvalId;

    @Column(name="id_company")
    private int company;

    @Column(name="id_masterizin")
    private int idMasterIzin;


}
