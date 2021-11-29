package com.rilo.hris.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "master_izin")
public class MasterIzin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto Increment
    @Column(name = "id_masterizin")
    private int id;

    @Column(name = "jenis_izin")
    private String jenisIzin;

    @Column(name = "id_company")
    private int company;

    @Column(name = "status_masterizin")
    private int statusMasterIzin;
}
