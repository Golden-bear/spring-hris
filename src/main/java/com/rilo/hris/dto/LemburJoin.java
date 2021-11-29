package com.rilo.hris.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LemburJoin {
    private int idLembur;
    private int idEmployee;
    private Date tanggal;
    private String tugas;
    private int idProyek;
    private int idDilaporkan;
    private Date jamIn;
    private Date jamOut;
    private String durasi;
    private int company;
    private String firstNamePengaju;
    private String lastNamePengaju;
    private String namaProyek;
    private String firstNamePj;
    private String lastNamePj;

}
