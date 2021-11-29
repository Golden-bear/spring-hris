package com.rilo.hris.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class IzinJoin {
    private int idIzin;
    private int idEmployee;
    private Date jamIzin;
    private Date tglIzin;
    private String keterangan;
    private String firstName;
    private String lastName;
    private String nik;
    private int idDivisi;
    private String nameDivision;
    private int idMasterIzin;
    private String nameMasterIzin;
}
