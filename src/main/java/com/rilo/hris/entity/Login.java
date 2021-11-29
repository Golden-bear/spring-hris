package com.rilo.hris.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data //tidak perlu membuat getter and setter jika menggunakan @Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class Login {
    @Id
    @GeneratedValue
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name= "id_employee")
	private int idEmployee;

    @Column(name = "id_company")
	private int idCompany;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
