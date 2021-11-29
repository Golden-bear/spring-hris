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
@Table(name = "wilayah_desa")
public class Desa {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idDesa;

    @Column(name="id_kec")
    private int idKecamatan;

    @Column(name = "nama")
    private String namaDesa;
}
