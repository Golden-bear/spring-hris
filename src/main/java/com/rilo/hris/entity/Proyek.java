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
@Table(name = "proyek")
public class Proyek {
    @Id
    @Column(name = "id_proyek")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idProyek;

    @Column(name = "nama_proyek")
    private String namaProyek;


    @Column(name = "id_company")
    private int idCompany;
}
