package com.rilo.hris.repository;

import com.rilo.hris.dto.LemburJoin;
import com.rilo.hris.entity.Lembur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface LemburRepository extends JpaRepository<Lembur, Integer>{

    @Query(
            value = "SELECT new com.rilo.hris.dto.LemburJoin(a.idLembur,a.idPegawai,a.tanggal,a.tugas,a.idProject,a.idDilaporkan,a.jamIn,a.jamOut,a.durasi,a.company,b.firstName,b.lastName,c.namaProyek,d.firstName,d.lastName) " +
                    "FROM Lembur a " +
                    "JOIN Pegawai b ON  a.idPegawai = b.idEmployee " +
                    "JOIN Proyek c ON a.idProject = c.idProyek " +
                    "JOIN Pegawai d ON a.idDilaporkan = d.idEmployee " +
                    "WHERE a.company = ?1"
    )
    List<LemburJoin> getJoinByComp(int idComp);

    @Query(
            value = "SELECT new com.rilo.hris.dto.LemburJoin(a.idLembur,a.idPegawai,a.tanggal,a.tugas,a.idProject,a.idDilaporkan,a.jamIn,a.jamOut,a.durasi,a.company,b.firstName,b.lastName,c.namaProyek,d.firstName,d.lastName) " +
                    "FROM Lembur a " +
                    "JOIN Pegawai b ON  a.idPegawai = b.idEmployee " +
                    "JOIN Proyek c ON a.idProject = c.idProyek " +
                    "JOIN Pegawai d ON a.idDilaporkan = d.idEmployee " +
                    "WHERE a.company = ?1 AND a.tanggal = ?2"
    )
    List<LemburJoin> getByTanggal(int idComp, Date tgl);

    @Query(
            value = "SELECT new com.rilo.hris.dto.LemburJoin(a.idLembur,a.idPegawai,a.tanggal,a.tugas,a.idProject,a.idDilaporkan,a.jamIn,a.jamOut,a.durasi,a.company,b.firstName,b.lastName,c.namaProyek,d.firstName,d.lastName) " +
                    "FROM Lembur a " +
                    "JOIN Pegawai b ON  a.idPegawai = b.idEmployee " +
                    "JOIN Proyek c ON a.idProject = c.idProyek " +
                    "JOIN Pegawai d ON a.idDilaporkan = d.idEmployee " +
                    "WHERE a.company = ?1 AND a.tanggal BETWEEN ?2 AND ?3 "
    )
    List<LemburJoin> getBytanggalRange(int idComp, Date tglStart, Date tglEnd);
}
