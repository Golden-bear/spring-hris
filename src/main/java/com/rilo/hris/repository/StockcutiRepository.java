package com.rilo.hris.repository;

import com.rilo.hris.entity.StockCuti;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StockcutiRepository extends  JpaRepository<StockCuti,Integer>{

    @Query("SELECT u FROM StockCuti u WHERE idEmployee = ?1 AND idJenisCuti = ?2")
    StockCuti cekJenis(int idEmp, int idJenisCuti);
}
