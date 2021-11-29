package com.rilo.hris.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Logbook {
    @Id
    @GeneratedValue
    private int id_project;
    private String nama_project;
    private int status;
    private String id_divisi;
    private String id_subdiv;
    private String nama_divisi;
    private String nama_subdiv;
	public int getId_project() {
		return id_project;
	}
	public void setId_project(int id_project) {
		this.id_project = id_project;
	}
	public String getNama_project() {
		return nama_project;
	}
	public void setNama_project(String nama_project) {
		this.nama_project = nama_project;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getId_divisi() {
		return id_divisi;
	}
	public void setId_divisi(String id_divisi) {
		this.id_divisi = id_divisi;
	}
	public String getId_subdiv() {
		return id_subdiv;
	}
	public void setId_subdiv(String id_subdiv) {
		this.id_subdiv = id_subdiv;
	}
	public String getNama_divisi() {
		return nama_divisi;
	}
	public void setNama_divisi(String nama_divisi) {
		this.nama_divisi = nama_divisi;
	}
	public String getNama_subdiv() {
		return nama_subdiv;
	}
	public void setNama_subdiv(String nama_subdiv) {
		this.nama_subdiv = nama_subdiv;
	}
}
