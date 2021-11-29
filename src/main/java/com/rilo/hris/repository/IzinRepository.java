package com.rilo.hris.repository;
import com.rilo.hris.entity.Izin;
import com.rilo.hris.dto.IzinJoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface IzinRepository extends JpaRepository<Izin, Integer>{

    @Query(
            value = "SELECT new com.rilo.hris.dto.IzinJoin(a.id,a.idPegawai,a.jamIzin,a.tanggalIzin,a.keterangan,b.firstName,b.lastName,b.nik,c.idDivisi,c.nameDivision,a.idMasterIzin,d.jenisIzin) " +
                    "FROM Izin a " +
                    "JOIN Pegawai b ON  a.idPegawai = b.idEmployee " +
                    "JOIN Divisi c ON b.divisi = c.idDivisi " +
                    "JOIN MasterIzin d ON a.idMasterIzin = d.id " +
                    "WHERE a.approvalId = ?1 AND a.statusIzin = ?2"
    )
    List<IzinJoin> izinJoin(int idApproval, int statusIzin);

    @Query(
            value = "SELECT new com.rilo.hris.dto.IzinJoin(a.id,a.idPegawai,a.jamIzin,a.tanggalIzin,a.keterangan,b.firstName,b.lastName,b.nik,c.idDivisi,c.nameDivision,a.idMasterIzin,d.jenisIzin) " +
                    "FROM Izin a " +
                    "JOIN Pegawai b ON  a.idPegawai = b.idEmployee " +
                    "JOIN Divisi c ON b.divisi = c.idDivisi " +
                    "JOIN MasterIzin d ON a.idMasterIzin = d.id " +
                    "WHERE a.company = ?1 AND a.tanggalIzin BETWEEN ?2 AND ?3"
    )
    List<IzinJoin> rangeTanggal(int idCompany, Date tglStart, Date tglEnd);

    @Query(
            value = "SELECT new com.rilo.hris.dto.IzinJoin(a.id,a.idPegawai,a.jamIzin,a.tanggalIzin,a.keterangan,b.firstName,b.lastName,b.nik,c.idDivisi,c.nameDivision,a.idMasterIzin,d.jenisIzin) " +
                    "FROM Izin a " +
                    "JOIN Pegawai b ON  a.idPegawai = b.idEmployee " +
                    "JOIN Divisi c ON b.divisi = c.idDivisi " +
                    "JOIN MasterIzin d ON a.idMasterIzin = d.id " +
                    "WHERE a.company = ?1 AND a.tanggalIzin = ?2"
    )
    List<IzinJoin> byTanggal(int idCompany, Date tgl);
}
