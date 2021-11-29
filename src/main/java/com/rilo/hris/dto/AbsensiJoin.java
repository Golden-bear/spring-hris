package com.rilo.hris.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AbsensiJoin {
    private int idAbsen;
    private Date checkIn;
    private Date checkOut;
    private String firstName;
    private String lastName;
    private int divisi;
    private int jobLevel;
}
