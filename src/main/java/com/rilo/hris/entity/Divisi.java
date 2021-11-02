package com.rilo.hris.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "divisi")
public class Divisi {
    @Id
    @GeneratedValue
	@Column(name = "id_divisi")
    private int idDivisi;

	@Column(name = "name_division")
    private String nameDivision;
    @Column(name = "company")
    private int company;
}
