package com.rilo.hris.repository;


import com.rilo.hris.entity.LevelApproval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


//Integer dipilih karena tipe id dari entity integer
public interface LevelApprovalRepository extends JpaRepository<LevelApproval, Integer>{

	@Query("select u from LevelApproval u where divisi = ?1 and jobLevel = ?2 and tingkat = ?3 and company = ?4")
	LevelApproval findExistApproval(int divisi, int joblvl,int tingkat,int company);
}
