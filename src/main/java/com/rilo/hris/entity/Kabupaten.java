package com.rilo.hris.entity;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "wilayah_kabupaten")
public class Kabupaten {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idKab;

//    @OneToMany(targetEntity = Provinsi.class,cascade = CascadeType.ALL)
//    @JoinColumn(name="id_prov",referencedColumnName = "id")
    @Column(name = "id_prov")
    private int idProvinsi;

    @Column(name = "nama")
    private String namaKabupaten;
}
