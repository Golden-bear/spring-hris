package com.rilo.hris.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "divisi")
public class Divisi {
    @Id
    @GeneratedValue
    private int id_divisi;
    private String name_division;
    private int company;
	public int getId_divisi() {
		return id_divisi;
	}
	public void setId_divisi(int id_divisi) {
		this.id_divisi = id_divisi;
	}
	public String getName_division() {
		return name_division;
	}
	public void setName_division(String name_division) {
		this.name_division = name_division;
	}
	public int getCompany() {
		return company;
	}
	public void setCompany(int company) {
		this.company = company;
	}
}
