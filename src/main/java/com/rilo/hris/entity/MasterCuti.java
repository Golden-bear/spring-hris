package com.rilo.hris.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "jenis_cuti")
public class MasterCuti {

    @Id
    @Column(name = "id_jenis_cuti")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nama")
    private String namaJenisCuti;

    @Column(name = "jumlah_pemakaian")
    private int jmlhPemakaian;

    @Column(name = "id_company")
    private int idComp;

    @Column(name = "status_mastercuti")
    private int status;


}
