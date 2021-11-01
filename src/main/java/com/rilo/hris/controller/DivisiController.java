package com.rilo.hris.controller;

import java.util.List;
import com.rilo.hris.entity.Divisi;
import com.rilo.hris.service.DivisiService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class DivisiController {

    @Autowired
    private DivisiService service;

    @PostMapping("/tambahdivisi")
    public Divisi addDivisi(@RequestBody Divisi divisi){
        return service.saveDivisi(divisi);
    }

    @GetMapping("/getalldivisi")
    public List<Divisi> findAllDivisi(){
        return service.getDivisi();
    }

    @GetMapping("/divisi/{id_divisi}")
    public Divisi findDivisiById(@PathVariable int id_divisi){
        return service.getDivisiById(id_divisi);
    }

    @GetMapping("/divisibycompany/{company}")
    public Divisi findDivisiyComapny(@PathVariable int company){
        return service.getDivisiByCompany(company);
    }

    @PutMapping("/divisiupdate/{company}")
    public Divisi findDivisiyComapny(@RequestBody Divisi divisi){
        return service.updateDivisi(divisi);
    }

    @DeleteMapping("/divisi/{id_divisi}")
    public String deleteDivision(@PathVariable int id_divisi){
        return service.deleteDivisi(id_divisi);
    }
}
