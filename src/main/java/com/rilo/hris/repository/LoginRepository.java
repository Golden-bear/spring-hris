package com.rilo.hris.repository;

import com.rilo.hris.entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

//dalam jpa, sebelah kiri object ke table, sebelah kanan type field id dari kelas divisi
public interface LoginRepository extends JpaRepository<Login,String> {
    //@Query(value = "SELECT * FROM divisi WHERE company = ?1",nativeQuery = true)
    //List<Login> findByCompany(int company); //custom query karena defauld by id doang

}
