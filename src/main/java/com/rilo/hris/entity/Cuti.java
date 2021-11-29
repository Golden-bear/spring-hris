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
@Table(name = "absen_cuti")
public class Cuti {
    @Id
    @Column(name = "id_absen_cuti")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "id_employee")
    private int idEmployee;

    @Column(name = "id_jenis_cuti")
    private int idJenisCuti;

    @Column(name = "tgl_mulai")
    private Date tglMulai;

    @Column(name = "tgl_selesai")
    private Date tglSelesai;

    @Column(name = "keterangan")
    private String keterangan;

    @Column(name = "address")
    private String address;

    @Column(name = "status_cuti")
    private  int statusCuti;

    @Column(name = "jumlah_pakai")
    private  int jumlahPakai;

    @Column(name = "tingkat_approval")
    private  int tingkatApproval;

    @Column(name = "approval_id")
    private  int approvalId;
}
