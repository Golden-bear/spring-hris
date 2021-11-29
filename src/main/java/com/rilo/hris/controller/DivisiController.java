package com.rilo.hris.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rilo.hris.entity.Divisi;
import com.rilo.hris.model.ResponseModify;
import com.rilo.hris.service.DivisiService;
import com.rilo.hris.service.LoginService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Slf4j
@RestController
public class DivisiController {

    @Autowired
    private DivisiService service;


    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    LoginService auth;

    SimpleDateFormat uniq_format = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    @PostMapping("divisi/tambahdivisi")
    public ResponseEntity addDivisi(@RequestBody Map<String, String> body){
        if(body.get("nameDivision")== null || body.get("nameDivision").toString().isEmpty()) return new ResponseEntity(new ResponseModify(0, "nameDivision harus di isi"),HttpStatus.OK);

        Date logDate = new Date();
        try {
            log.info("/tambahdivisi tanggal : " + logDate);
            return service.saveDivisi(body);
        }catch (Exception e){
            log.error("/tambahdivisi : "+logDate + " "+e.getMessage());
            return new ResponseEntity(new ResponseModify(0, e.getMessage()),HttpStatus.OK);
        }

    }

    @PostMapping("divisi/savedivisibyactivemq")
    public ResponseEntity saveDivisiActiveMq(@RequestBody Divisi divisi) {
        Date logDate = new Date();
        try {
            log.info("/savedivisibyactivemq " + logDate);
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = "";
            jsonString = mapper.writeValueAsString(divisi);
            jmsTemplate.convertAndSend("bridginingcode-queue",jsonString);
            return new ResponseEntity(new ResponseModify(1,"terkirim ke antrian"),HttpStatus.OK);
        }catch (Exception e){
            log.error("/savedivisibyactivemq : "+logDate+" "+e.getMessage());
            return new ResponseEntity(new ResponseModify(0,e.getMessage()),HttpStatus.OK);
        }
    }

    @GetMapping("divisi/getalldivisi")
    public ResponseEntity findAllDivisi(@RequestHeader(name="Authorization",required = false, defaultValue = "") String Authorization){
        if(Authorization.toString().isEmpty() || Authorization == null){
            return new ResponseEntity(new ResponseModify(0, "endpoint ini membutuhkan authorization"),HttpStatus.OK);
        }
        String uniq = uniq_format.format(new Date());
        ResponseModify resp = auth.AuthenticateMiddleware(uniq,Authorization);
        if(resp.getStatus() != 1){
            return new ResponseEntity(new ResponseModify(0, resp.getMessage()),HttpStatus.OK);
        }

        Date logDate = new Date();

        try {
            log.info("/getalldivisi "+logDate);
            return service.getDivisi();
        }catch (Exception e){
            log.error("/getalldivisi : "+logDate+" "+e.getMessage());
            return new ResponseEntity(new ResponseModify(0,e.getMessage()),HttpStatus.OK);
        }

    }

    @GetMapping("/divisi/{id_divisi}")
    public ResponseEntity findDivisiById(@PathVariable int id_divisi){

        Date logDate = new Date();

        try{
            log.info("/divisi/{id_divisi} "+logDate);
            return service.getDivisiById(id_divisi);
        }catch (Exception e){
            log.error("/divisi/{id_divisi} : "+logDate+" "+e.getMessage());
            return new ResponseEntity(new ResponseModify(0,e.getMessage()),HttpStatus.OK);
        }

    }

    @GetMapping("divisi/bycompany/{company}")
    public ResponseEntity findDivisiyComapny(@PathVariable int company){

        try{
            log.info("/divisibycompany/" + company);
            return service.getDivisiByCompany(company);
        }catch (Exception e){
            log.error("/divisibycompany/"+ company +" : "+e.getMessage());
            return new ResponseEntity(new ResponseModify(0,e.getMessage()),HttpStatus.OK);
        }

    }

    @PutMapping("divisi/update")
    public ResponseEntity findDivisiyComapny(@RequestBody Divisi divisi){

        try{
            log.info("/divisiupdate");
            return service.updateDivisi(divisi);
        }catch (Exception e){
            log.error("/divisiupdate/ : "+e.getMessage());
            return new ResponseEntity(new ResponseModify(0,e.getMessage()),HttpStatus.OK);
        }

    }

    @DeleteMapping("/divisi/{id_divisi}")
    public ResponseEntity deleteDivision(@PathVariable int id_divisi){
        try{
            log.info("/divisi/"+ id_divisi);
            return service.deleteDivisi(id_divisi);
        }catch (Exception e){
            log.error("/divisi/"+ id_divisi +" :" +e.getMessage());
            return new ResponseEntity(new ResponseModify(0,e.getMessage()),HttpStatus.OK);
        }

    }
}
