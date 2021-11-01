package com.rilo.hris.service;

import java.util.List;
import java.util.Optional;

import com.rilo.hris.entity.Divisi;
import com.rilo.hris.repository.DivisiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DivisiService {
    @Autowired
    private DivisiRepository repository;

    public Divisi saveDivisi(Divisi divisi){
        return repository.save(divisi);
    }

    public List<Divisi> getDivisi(){
        return repository.findAll();
    }

    public Divisi getDivisiById(int id_divisi){
         Optional<?> dt = findById(id_divisi);
    }

    public Divisi getDivisiByCompany(int company){
        return repository.findByCompany(company);
    }

    public String deleteDivisi(int id_divisi){
        repository.deleteById(id_divisi);
        return "Divisi Dihapus "+id_divisi;
    }

    public Divisi updateDivisi(Divisi divisi){
        Divisi existingDivisi=repository.findById(divisi.getId_divisi()).orElse(divisi);
        existingDivisi.setName_division(divisi.getName_division());
        return repository.save(existingDivisi);
    }
}
