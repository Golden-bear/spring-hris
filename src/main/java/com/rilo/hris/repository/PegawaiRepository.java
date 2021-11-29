package com.rilo.hris.repository;

import com.rilo.hris.entity.Pegawai;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PegawaiRepository extends JpaRepository<Pegawai, Integer> {

    @Query("SELECT u FROM Pegawai u WHERE idEmployee = ?1 AND company = ?2")
    Pegawai getByIdAndComp(int id, int comp);
}
