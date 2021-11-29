package com.rilo.hris.repository;


import com.rilo.hris.entity.Absensi;
import com.rilo.hris.dto.AbsensiJoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface AbsensiRepository extends JpaRepository<Absensi, Integer> {
	
	List<Absensi> findByCompany(int company);
	
	@Query("select u from Absensi u where idPegawai = ?1 and tanggalIn = ?2")
	Absensi findByToday(int idKaryawan, Date start);

	@Query("select u from Absensi u where id = ?1 and statusIn = ?2")
	Absensi findByIdStatusIn(int idAbsen, int status);

	@Query("select u from Absensi u where id = ?1 and statusOut = ?2")
	Absensi findByIdStatusOut(int idAbsen, int status);

	@Query(
			value = "SELECT new com.rilo.hris.dto.AbsensiJoin(a.id,a.absenIn,a.absenOut,b.firstName,b.lastName,b.divisi,b.jobLevel) " +
					"FROM Absensi a " +
					"JOIN Pegawai b ON  a.idPegawai = b.idEmployee " +
					"WHERE a.company = ?1 AND a.tanggalIn = ?2 AND a.captionIn != '' "
	)
	List<AbsensiJoin> absenTodayByCompany(int idComp, Date tgl);

	@Query(
			value = "SELECT new com.rilo.hris.dto.AbsensiJoin(a.id,a.absenIn,a.absenOut,b.firstName,b.lastName,b.divisi,b.jobLevel) " +
					"FROM Absensi a " +
					"JOIN Pegawai b ON  a.idPegawai = b.idEmployee " +
					"WHERE a.company = ?1 AND a.captionIn != '' AND a.tanggalIn BETWEEN ?2 AND ?3 "
	)
	List<AbsensiJoin> getAbsenByRange(int idComp, Date start, Date end);

}
