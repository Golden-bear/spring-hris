package com.rilo.hris.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.GenerationType;
import java.io.Serializable;
import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "divisi")
public class Divisi implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto Increment
	@Column(name = "id_divisi")
    private int idDivisi;


	@Column(name = "name_division")
    private String nameDivision;
    @Column(name = "company")
    private int company;
	public int getIdDivisi() {
		return idDivisi;
	}
	public void setIdDivisi(int idDivisi) {
		this.idDivisi = idDivisi;
	}
	public String getNameDivision() {
		return nameDivision;
	}
	public void setNameDivision(String nameDivision) {
		this.nameDivision = nameDivision;
	}
	public int getCompany() {
		return company;
	}
	public void setCompany(int company) {
		this.company = company;
	}
}
