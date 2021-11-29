package com.rilo.hris.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Table(name = "lembur")
public class Lembur {

    @Id
    @Column(name = "id_lembur")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idLembur;

    @Column(name="id_employee")
    private int idPegawai;

    @Column(name="tanggal")
    private Date tanggal;

    @Column(name="tugas")
    private String tugas;

    @Column(name="id_proyek")
    private int idProject;

    @Column(name="id_dilaporkan")
    private int idDilaporkan;

    @Column(name="jam_in")
    private Date jamIn;

    @Column(name="jam_out")
    private Date jamOut;

    @Column(name="durasi")
    private String durasi;

    @Column(name="id_company")
    private int company;


}
