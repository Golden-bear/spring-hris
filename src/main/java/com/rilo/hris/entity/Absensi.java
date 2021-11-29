package com.rilo.hris.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "absen")
public class Absensi {

	//consructor jika ini dibuat maka harus di isi sesuai parameter
//	public Absensi(int company, Date tanngalIn){
//		this.company = company;
//		this.tanggalIn = tanngalIn;
//	}

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="id_pegawai")
	private int idPegawai;
	
	@Column(name="company")
	private int company;
	
	@Column(name="tanggal_in")
	private Date tanggalIn;
	
	@Column(name="tanggal_out")
	private Date tanggalOut;
	
	@Column(name="absen_in")
	private Date absenIn;
	
	@Column(name="absen_out")
	private Date absenOut;
	
	@Column(name="keterangan")
	private String keterangan;	
	
	@Column(name="caption_in")
	private String captionIn;
	
	@Column(name="caption_out")
	private String captionOut;
	
	@Column(name="lat_in")
	private String latIn;
	
	@Column(name="lat_out")
	private String latOut;
	
	@Column(name="long_in")
	private String longIn;
	
	@Column(name="long_out")
	private String longOut;
	
	@Column(name="status_in")
	private int statusIn;
	
	@Column(name="status_out")
	private int statusOut;
	
	@Column(name="tingkat_approval_in")
	private int tingkatApprovalIn;
	
	@Column(name="tingkat_approval_out")
	private int tingkatApprovalOut;
	
	
	@Column(name="approval_id_in")
	private int approvalIdIn;
	
	@Column(name="approval_id_out")
	private int approvalIdOut;
	
	@Column(name="foto_in")
	private String fotoIn;
	
	@Column(name="foto_out")
	private String fotoOut;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdPegawai() {
		return idPegawai;
	}

	public void setIdPegawai(int idPegawai) {
		this.idPegawai = idPegawai;
	}

	public int getCompany() {
		return company;
	}

	public void setCompany(int company) {
		this.company = company;
	}

	public Date getTanggalIn() {
		return tanggalIn;
	}

	public void setTanggalIn(Date tanggalIn) {
		this.tanggalIn = tanggalIn;
	}

	public Date getTanggalOut() {
		return tanggalOut;
	}

	public void setTanggalOut(Date tanggalOut) {
		this.tanggalOut = tanggalOut;
	}

	public Date getAbsenIn() {
		return absenIn;
	}

	public void setAbsenIn(Date absenIn) {
		this.absenIn = absenIn;
	}

	public Date getAbsenOut() {
		return absenOut;
	}

	public void setAbsenOut(Date absenOut) {
		this.absenOut = absenOut;
	}

	public String getKeterangan() {
		return keterangan;
	}

	public void setKeterangan(String keterangan) {
		this.keterangan = keterangan;
	}

	public String getCaptionIn() {
		return captionIn;
	}

	public void setCaptionIn(String captionIn) {
		this.captionIn = captionIn;
	}

	public String getCaptionOut() {
		return captionOut;
	}

	public void setCaptionOut(String captionOut) {
		this.captionOut = captionOut;
	}

	public String getLatIn() {
		return latIn;
	}

	public void setLatIn(String latIn) {
		this.latIn = latIn;
	}

	public String getLatOut() {
		return latOut;
	}

	public void setLatOut(String latOut) {
		this.latOut = latOut;
	}

	public String getLongIn() {
		return longIn;
	}

	public void setLongIn(String longIn) {
		this.longIn = longIn;
	}

	public String getLongOut() {
		return longOut;
	}

	public void setLongOut(String longOut) {
		this.longOut = longOut;
	}

	public int getStatusIn() {
		return statusIn;
	}

	public void setStatusIn(int statusIn) {
		this.statusIn = statusIn;
	}

	public int getStatusOut() {
		return statusOut;
	}

	public void setStatusOut(int statusOut) {
		this.statusOut = statusOut;
	}

	public int getTingkatApprovalIn() {
		return tingkatApprovalIn;
	}

	public void setTingkatApprovalIn(int tingkatApprovalIn) {
		this.tingkatApprovalIn = tingkatApprovalIn;
	}


	public int getApprovalIdIn() {
		return approvalIdIn;
	}

	public void setApprovalIdIn(int approvalIdIn) {
		this.approvalIdIn = approvalIdIn;
	}

	public int getApprovalIdOut() {
		return approvalIdOut;
	}

	public void setApprovalIdOut(int approvalIdOut) {
		this.approvalIdOut = approvalIdOut;
	}

	public String getFotoIn() {
		return fotoIn;
	}

	public void setFotoIn(String fotoIn) {
		this.fotoIn = fotoIn;
	}

	public String getFotoOut() {
		return fotoOut;
	}

	public void setFotoOut(String fotoOut) {
		this.fotoOut = fotoOut;
	}

	public int getTingkatApprovalOut() {
		return tingkatApprovalOut;
	}

	public void setTingkatApprovalOut(int tingkatApprovalOut) {
		this.tingkatApprovalOut = tingkatApprovalOut;
	}


}
