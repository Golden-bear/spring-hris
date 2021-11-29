package com.rilo.hris.repository;

import com.rilo.hris.entity.Pesan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PesanRepository extends JpaRepository<Pesan, Integer> {

}
