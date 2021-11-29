package com.rilo.hris.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data //tidak perlu membuat getter and setter jika menggunakan @Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "company")
public class Company {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int idCompany;

    @Column(name = "name")
    private String name;

//    @OneToMany(targetEntity = Alamat.class,cascade = CascadeType.ALL)
//    @JoinColumn(name="id_address",referencedColumnName = "id")
    @Column(name = "id_address")
    private int idAddress;

    @Column(name="active")
    private int active;
}
