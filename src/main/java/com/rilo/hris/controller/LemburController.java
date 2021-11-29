package com.rilo.hris.controller;

import com.rilo.hris.model.ResponseModify;
import com.rilo.hris.model.ResponseObject;
import com.rilo.hris.service.LemburService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@Slf4j
@AllArgsConstructor
@RestController
public class LemburController {
    private LemburService lemburService;

    @PostMapping(value="lembur/tambahlembur")
    public ResponseEntity addDivisi(@RequestBody Map<String, String> body){
        if(body.get("idPegawai") == null) return new ResponseEntity(new ResponseModify(0, "idPegawai harus di isi"),HttpStatus.OK);
        if(body.get("tanggal")==null) return new ResponseEntity(new ResponseModify(0, "tanggal harus di isi"),HttpStatus.OK);
        if(body.get("tugas")==null) return new ResponseEntity(new ResponseModify(0, "tugas harus di isi"),HttpStatus.OK);
        if(body.get("idProject")==null) return new ResponseEntity(new ResponseModify(0, "idProject harus di isi"),HttpStatus.OK);
        if(body.get("idDilaporkan")==null) return new ResponseEntity(new ResponseModify(0, "idDilpaorkan harus di isi"),HttpStatus.OK);
        if(body.get("jamIn")==null) return new ResponseEntity(new ResponseModify(0, "jamIn harus di isi"),HttpStatus.OK);
        if(body.get("company")==null) return new ResponseEntity(new ResponseModify(0, "company harus di isi"),HttpStatus.OK);

        Date logDate = new Date();
        try{
            log.info("lembur/tambahlembur "+logDate);
            return lemburService.save(body);
       }catch(Exception e){
            log.error("lembur/tambahlembur "+logDate +" "+e.getMessage());
           return new ResponseEntity(new ResponseModify(0, e.getMessage()),HttpStatus.OK);
       }
    }

    @PutMapping(value = "lembur/update/{id}")
    public ResponseEntity updateCheckin(@RequestBody Map<String, String> body, @PathVariable int id){

        if(body.get("jamOut") == null) return new ResponseEntity(new ResponseModify(0, "jamOut harus di isi"),HttpStatus.OK);

        Date logDate = new Date();
        try{
            log.info("lembur/update/ "+id+" "+logDate);
            return lemburService.updateLembur(id, body);
        } catch (Exception e){
            log.error("lembur/update/ "+id+" "+logDate +" "+e.getMessage());
            e.printStackTrace();
            return new ResponseEntity(new ResponseObject(0, e.getMessage()),HttpStatus.OK);
        }

    }

    @GetMapping("lembur/bycomp/{id}")
    public ResponseEntity getByComp(@PathVariable int id){

        Date logDate = new Date();
        try{
            log.info("lembur/bycomp/ "+id+" "+logDate);
            return lemburService.getByCompany(id);
        } catch (Exception e){
            log.error("lembur/bycomp/ "+id+" "+logDate +" "+e.getMessage());
            e.printStackTrace();
            return new ResponseEntity(new ResponseObject(0, e.getMessage()),HttpStatus.OK);
        }
    }

    @PostMapping(value="/lembur/getrange/{id}")
    public ResponseEntity addDivisi(@PathVariable int id, @RequestBody Map<String, String> body){

        if(body.get("tanggalStart") == null) return new ResponseEntity(new ResponseModify(0, "tanggalStart harus di isi"),HttpStatus.OK);

        Date logDate = new Date();
        try{
            log.info("/lembur/getrange/ "+id+" "+logDate);
            return lemburService.getRangeTanggalByComp(id, body);
        }catch(Exception e){
            log.error("/lembur/getrange/ "+id+" "+logDate +" "+e.getMessage());
            return new ResponseEntity(new ResponseModify(0, e.getMessage()),HttpStatus.OK);
        }
    }
}
