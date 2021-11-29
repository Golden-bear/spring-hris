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
@Table(name = "alamat")
public class Alamat {
    @Id
    @Column(name = "id_address")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAlamat;

    @Column(name = "alamat")
    private String alamats;


    @Column(name = "prov")
    private int provinsi;

    @Column(name = "kab")
    private int kabupaten;

    @Column(name = "kec")
    private int kecamatan;


    @Column(name = "kenegaraan")
    private String kenegaraan;

    @Column(name = "telephone")
    private String telp;

    @Column(name = "email")
    private  String emails;

}
