package com.rilo.hris.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@ToString
@Data
@Entity
@Table(name = "employee_cuti")
public class StockCuti {

    @Id
    @Column(name = "id_employee_cuti")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idEmployeeCuti;

    @Column(name = "id_employee")
    private int idEmployee;

    @Column(name = "id_jenis_cuti")
    private int idJenisCuti;


    @Column(name = "jumlah_sisa_pemakaian")
    private int jmlhSisa;

    @Column(name = "id_company")
    private String idComp;

    @Column(name = "histori")
    private int histori;




}
