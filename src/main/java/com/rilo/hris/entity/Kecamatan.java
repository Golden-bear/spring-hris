package com.rilo.hris.entity;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "wilayah_kecamatan")
public class Kecamatan {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idKec;

//    @OneToMany(targetEntity = Kabupaten.class,cascade = CascadeType.ALL)
//    @JoinColumn(name="id_kab",referencedColumnName = "id")
    @Column(name = "id_kab")
    private int idKabupaten;

    @Column(name = "nama")
    private String namaKecamatan;
}
