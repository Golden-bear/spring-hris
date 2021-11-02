package com.rilo.hris.service;

import lombok.AllArgsConstructor;
import java.util.List;
import java.util.Optional;

import com.rilo.hris.entity.Divisi;
import com.rilo.hris.repository.DivisiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DivisiService {
   
    private DivisiRepository repository;

    public Divisi saveDivisi(Divisi divisi){

        return repository.save(divisi);
    }

    public List<Divisi> getDivisi(){
        try{
            var listDivisi  =  repository.findAll();
            return listDivisi;
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    public Divisi getDivisiById(int idDivisi){
        Optional<Divisi> dt = repository.findById(idDivisi);
        return dt.orElseThrow();
    }

    public List<Divisi> getDivisiByCompany(int company){
        return repository.findByCompany(company);
    }

    public String deleteDivisi(int id_divisi){
        repository.deleteById(id_divisi);
        return "Divisi Dihapus "+id_divisi;
    }

    public Divisi updateDivisi(Divisi divisi){
        Divisi existingDivisi=repository.findById(divisi.getIdDivisi()).orElse(divisi);
        existingDivisi.setNameDivision(divisi.getNameDivision());
        return repository.save(existingDivisi);
    }
}
