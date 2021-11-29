package com.rilo.hris.repository;

import com.rilo.hris.dto.MenuJoin;
import com.rilo.hris.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Integer> {

    @Query("SELECT u from Menu u WHERE parentMenu = ?1")
    List<Menu> byRelation(int parent);

    @Query("SELECT u from Menu u WHERE idMenu = ?1")
    List<Menu> byIds(int parent);
}
