package com.rilo.hris.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
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
}
