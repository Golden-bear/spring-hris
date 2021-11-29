package com.rilo.hris.controller;

import com.rilo.hris.model.ResponseModify;
import com.rilo.hris.model.ResponseObject;
import com.rilo.hris.service.CutiService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

@Slf4j
@AllArgsConstructor
@RestController
public class CutiController {
    private CutiService cutiService;

    @PostMapping("cuti/add")
    public ResponseEntity addCuti(@RequestBody Map<String, String> body){
        if(body.get("idPegawai") == null) return new ResponseEntity(new ResponseModify(0, "idPegawai harus di isi"),HttpStatus.OK);
        if(body.get("JumlahPakai")==null) return new ResponseEntity(new ResponseModify(0, "JumlahPakai harus di isi"),HttpStatus.OK);
        if(body.get("idJenisCuti")==null) return new ResponseEntity(new ResponseModify(0, "idJenisCuti harus di isi"),HttpStatus.OK);
        if(body.get("keterangan")==null) return new ResponseEntity(new ResponseModify(0, "keterangan harus di isi"),HttpStatus.OK);
        if(body.get("tglMulai")==null) return new ResponseEntity(new ResponseModify(0, "tglMulai harus di isi"),HttpStatus.OK);
        if(body.get("tglSelesai")==null) return new ResponseEntity(new ResponseModify(0, "tglSelesai harus di isi"),HttpStatus.OK);
        if(body.get("address")==null) return new ResponseEntity(new ResponseModify(0, "address harus di isi"),HttpStatus.OK);

        Date logDate = new Date();
        try{
            log.info("cuti/add "+logDate);
            return cutiService.addCuti(body);
        } catch (Exception e){
            log.error("cuti/add : "+e.getMessage()+" "+logDate);
            e.printStackTrace();
            return new ResponseEntity(new ResponseObject(0,"Error"),HttpStatus.OK);
        }
    }
}
