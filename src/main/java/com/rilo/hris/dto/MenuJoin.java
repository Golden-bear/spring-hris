package com.rilo.hris.dto;

import com.rilo.hris.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

@Data //tidak perlu membuat getter and setter jika menggunakan @Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuJoin {

    private int idMenu;
    private String nameMenu;
    private String controllerMenu;
    private int parentMenu;
    private int levelMenu;
    private int status;
    private String accessRole;

    private String icon;
    private List<MenuJoin> child;
}
