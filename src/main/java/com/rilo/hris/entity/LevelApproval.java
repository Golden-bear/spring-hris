package com.rilo.hris.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "level_approval")
public class LevelApproval {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY) //appan ini?
	private int id;
	
	@Column(name = "divisi")
	private int divisi;
	
	@Column(name = "job_level")
	private int jobLevel;
	
	@Column(name = "approval_id")
	private int ApprovalId;
	
	@Column(name = "tingkat")
	private int tingkat;
	
	@Column(name = "company")
	private int company;
	
	@Column(name = "disposisi")
	private Integer disposisi;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDivisi() {
		return divisi;
	}

	public void setDivisi(int divisi) {
		this.divisi = divisi;
	}

	public int getJobLevel() {
		return jobLevel;
	}

	public void setJobLevel(int jobLevel) {
		this.jobLevel = jobLevel;
	}

	public int getApprovalId() {
		return ApprovalId;
	}

	public void setApprovalId(int approvalId) {
		ApprovalId = approvalId;
	}

	public int getTingkat() {
		return tingkat;
	}

	public void setTingkat(int tingkat) {
		this.tingkat = tingkat;
	}

	public int getCompany() {
		return company;
	}

	public void setCompany(int company) {
		this.company = company;
	}

	public Integer getDisposisi() {
		return disposisi;
	}

	public void setDisposisi(Integer disposisi) {
		this.disposisi = disposisi;
	}


	@Override
	public String toString() {
		return "LevelApproval{" +
				"id=" + id +
				", divisi=" + divisi +
				", jobLevel=" + jobLevel +
				", ApprovalId=" + ApprovalId +
				", tingkat=" + tingkat +
				", company=" + company +
				", disposisi=" + disposisi +
				'}';
	}
}
