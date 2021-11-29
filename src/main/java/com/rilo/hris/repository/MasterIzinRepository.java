package com.rilo.hris.repository;

import com.rilo.hris.entity.MasterIzin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MasterIzinRepository extends JpaRepository<MasterIzin, Integer> {

    @Query("SELECT u FROM MasterIzin u WHERE id = ?1 AND company = ?2")
    MasterIzin findExistData(int idMIzin, int idComp);

}
