package com.rilo.hris.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data //tidak perlu membuat getter and setter jika menggunakan @Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "menu")
public class Menu {

    @Id
    @GeneratedValue
    @Column(name = "id_menu")
    private int idMenu;

    @Column(name = "nama_menu")
    private String nameMenu;

    @Column(name = "controller_menu")
    private String controllerMenu;


    @Column(name = "parent_menu")
    private int parentMenu;

    @Column(name="level_menu")
    private int levelMenu;

    @Column(name = "status")
    private int status;

    @Column(name="access_role")
    private String accessRole;

    @Column(name="icon")
    private String icon;

}
