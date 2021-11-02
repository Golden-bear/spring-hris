package com.rilo.hris.service;

import com.rilo.hris.model.ResponseObject;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Date;
import java.util.Optional;
import java.text.SimpleDateFormat;
import com.rilo.hris.entity.Divisi;
import com.rilo.hris.repository.DivisiRepository;
import org.springframework.stereotype.Service;
import com.rilo.hris.model.ResponseList;
import com.rilo.hris.model.ResponseModify;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
@AllArgsConstructor
public class DivisiService {
//    SimpleDateFormat uniq_format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
//    SimpleDateFormat configGetDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//    SimpleDateFormat configGetTime = new SimpleDateFormat("HH:mm:ss");
    private Logger logger = LoggerFactory.getLogger(DivisiService.class);
    private DivisiRepository repository;

    public ResponseEntity<?> saveDivisi(Divisi divisi){
        try{
            repository.save(divisi);
            return new ResponseEntity(new ResponseModify(1,"Divisi Berhasil Dihapus"),HttpStatus.OK);
        }catch (Exception e){
            logger.error(e.toString());
            return new ResponseEntity(new ResponseModify(0,e.getMessage()),HttpStatus.OK);
        }

    }

    public ResponseEntity<?> getDivisi(){

        String method = "GET";
        String url = "/getalldivisi";
        try{
            logger.info(method+" "+url);
            var listDivisi  =  repository.findAll();
            return new ResponseEntity(new ResponseList(1,"get Data Sukses", listDivisi ),HttpStatus.OK);
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    public ResponseEntity<?> getDivisiById(int idDivisi){
        try{
            Optional<Divisi> dt = repository.findById(idDivisi);
            return new ResponseEntity(new ResponseObject(1,"get Data Sukses", dt ),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(new ResponseObject(0, e.getMessage()),HttpStatus.OK);
        }

    }

    public ResponseEntity<?> getDivisiByCompany(int company){
        try{
            var listDivisiByCompany  =  repository.findByCompany(company);
            return new ResponseEntity(new ResponseList(1,"get Data Sukses", listDivisiByCompany ),HttpStatus.OK);
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    public ResponseEntity<?> deleteDivisi(int id_divisi){
        try{
            repository.deleteById(id_divisi);
            return new ResponseEntity(new ResponseModify(1,"Divisi Berhasil Dihapus"),HttpStatus.OK);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<?> updateDivisi(Divisi divisi){
        try{
            Divisi existingDivisi=repository.findById(divisi.getIdDivisi()).orElse(divisi);
            existingDivisi.setNameDivision(divisi.getNameDivision());
            repository.save(existingDivisi);
            return new ResponseEntity(new ResponseModify(1,"Divisi Berhasil Diperbaruhi"),HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity(new ResponseModify(0,e.getMessage()),HttpStatus.OK);
        }

    }
}
