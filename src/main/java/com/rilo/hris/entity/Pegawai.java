package com.rilo.hris.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "employee")
public class Pegawai {
	
	@Id
	@Column(name = "id_employee")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idEmployee;
	
	@Column(name="company")
	private int company;
	
	@Column(name="address")
	private int address;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="npwp")
	private String npwp;
	
	@Column(name="nik")
	private String nik;
	
	@Column(name="divisi")
	private int divisi;	
	
	@Column(name="grade")
	private int grade;
	
	@Column(name="job_level")
	private int jobLevel;
	
	@Column(name="struktur")
	private int struktur;
	
	@Column(name="status")
	private int status;
	
	@Column(name="work_hour")
	private int workHour;

	public int getIdEmployee() {
		return idEmployee;
	}

	public void setIdEmployee(int idEmployee) {
		this.idEmployee = idEmployee;
	}

	public int getCompany() {
		return company;
	}

	public void setCompany(int company) {
		this.company = company;
	}

	public int getAddress() {
		return address;
	}

	public void setAddress(int address) {
		this.address = address;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getNpwp() {
		return npwp;
	}

	public void setNpwp(String npwp) {
		this.npwp = npwp;
	}

	public String getNik() {
		return nik;
	}

	public void setNik(String nik) {
		this.nik = nik;
	}

	public int getDivisi() {
		return divisi;
	}

	public void setDivisi(int divisi) {
		this.divisi = divisi;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public int getJobLevel() {
		return jobLevel;
	}

	public void setJobLevel(int jobLevel) {
		this.jobLevel = jobLevel;
	}

	public int getStruktur() {
		return struktur;
	}

	public void setStruktur(int struktur) {
		this.struktur = struktur;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getWorkHour() {
		return workHour;
	}

	public void setWorkHour(int workHour) {
		this.workHour = workHour;
	}

	@Override
	public String toString() {
		return "Pegawai{" +
				"idEmployee=" + idEmployee +
				", company=" + company +
				", address=" + address +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", npwp='" + npwp + '\'' +
				", nik='" + nik + '\'' +
				", divisi=" + divisi +
				", grade=" + grade +
				", jobLevel=" + jobLevel +
				", struktur=" + struktur +
				", status=" + status +
				", workHour=" + workHour +
				'}';
	}
}
