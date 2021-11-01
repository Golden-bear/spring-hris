package com.rilo.hris.repository;

import com.rilo.hris.entity.Divisi;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DivisiRepository extends JpaRepository<Divisi,Integer> {
    Divisi findByCompany(int company);
}
