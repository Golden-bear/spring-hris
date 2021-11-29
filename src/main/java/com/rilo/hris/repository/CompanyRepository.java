package com.rilo.hris.repository;

import com.rilo.hris.entity.Company;
import com.rilo.hris.dto.CompanyJoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

    @Query(
            value = "SELECT new com.rilo.hris.dto.CompanyJoin(a.idCompany,a.name,b.alamats,b.telp,c.namaProv,d.namaKabupaten,e.namaKecamatan,a.active) " +
                    "FROM Company a " +
                    "JOIN Alamat b ON  a.idAddress = b.idAlamat " +
                    "JOIN Provinsi c ON b.provinsi = c.idprov " +
                    "JOIN Kabupaten d ON b.kabupaten = d.idKab " +
                    "JOIN Kecamatan e ON b.kecamatan = e.idKec " +
                    "WHERE a.idCompany = ?1"
    )
    CompanyJoin getJoinInformation(int id);
}
