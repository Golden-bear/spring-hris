package com.rilo.hris.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class CompanyJoin {
    private int IdCompany;
    private String NamaCompany;
    private String Alamats;
    private String Telp;
    private String NamaProv;
    private String NamaKabupaten;
    private String NamaKecamatan;
    private int Active;
}
