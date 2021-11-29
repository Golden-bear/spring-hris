package com.rilo.hris.controller;

import com.rilo.hris.entity.Izin;
import com.rilo.hris.model.ResponseModify;
import com.rilo.hris.model.ResponseObject;
import com.rilo.hris.service.IzinService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@Slf4j
@AllArgsConstructor
@RestController
public class IzinController {

    private IzinService izinService;
    private JmsTemplate jmsTemplate;
    @PostMapping("izin/create/{id}")
    public ResponseEntity absenMasuk(@RequestBody Map<String, String> body, @PathVariable int id){
        if(body.get("idEmployee") == null) return new ResponseEntity(new ResponseModify(0, "idEmployee harus di isi"),HttpStatus.OK);
        if(body.get("jamIzin")==null) return new ResponseEntity(new ResponseModify(0, "jamIzin harus di isi"),HttpStatus.OK);
        if(body.get("tanggalIzin")==null) return new ResponseEntity(new ResponseModify(0, "tanggalIzin harus di isi"),HttpStatus.OK);
        if(body.get("keterangan")==null) return new ResponseEntity(new ResponseModify(0, "keterangan harus di isi"),HttpStatus.OK);
        if(body.get("idMasterizin")==null) return new ResponseEntity(new ResponseModify(0, "idMasterizin harus di isi"),HttpStatus.OK);

        Date logDate = new Date();
        try{
            log.info("izin/create/"+ id +" "+logDate);
           return izinService.saveIzin(id, body);
        } catch (Exception e){
            log.error("izin/create/"+ id +" "+logDate + " "+e.getMessage());
            e.printStackTrace();
            return new ResponseEntity(new ResponseObject(0,"Error : " + e.getMessage()),HttpStatus.OK);
        }

    }

    @PutMapping("izin/updateapproval")
    public ResponseEntity absenMasuk(@RequestBody Izin data) {
        if(Integer.toString(data.getId()) == null) return new ResponseEntity(new ResponseModify(0, "id harus di isi"),HttpStatus.OK);
        if(Integer.toString(data.getStatusIzin()) == null) return new ResponseEntity(new ResponseModify(0, "statusIzin harus di isi"),HttpStatus.OK);

        Date logDate = new Date();
        try {
            log.info("izin/updateapproval " + logDate);
            return izinService.updateTingkatApproval(data);
        }catch (Exception e){
            log.error("izin/updateapproval " + e.getMessage());
            return new ResponseEntity(new ResponseModify(0,e.getMessage()),HttpStatus.OK);
        }
    }

    @GetMapping("izin/viewbytingkat/{idApprov}")
    public ResponseEntity byTingkat(@PathVariable int idApprov){
        Date logDate = new Date();
        try{
            log.info("izin/vievByTingkat/"+ idApprov +" "+logDate);
            return izinService.viewForByTingkatApproval(idApprov);
        }catch (Exception e){
            log.error("izin/vievByTingkat/"+ idApprov +" "+logDate + " "+ e.getMessage());
            return new ResponseEntity(new ResponseModify(0,e.getMessage()),HttpStatus.OK);
        }
    }

    @PostMapping("izin/getrangetanggal")
    public ResponseEntity getIzinByRangeTanggal(@RequestBody Map<String, String> body){
        if(body.get("tanggalStart") == null) return new ResponseEntity(new ResponseModify(0, "tanggalStart harus di isi"),HttpStatus.OK);
        if(body.get("idCompany")==null) return new ResponseEntity(new ResponseModify(0, "idCompany harus di isi"),HttpStatus.OK);

        Date logDate = new Date();
        try{
            log.info("izin/getrangetanggal/ "+logDate);
            return izinService.getByRangeTgl(body);
        } catch (Exception e){
            log.error("izin/getrangetanggal/ "+logDate + " "+e.getMessage());
            e.printStackTrace();
            return new ResponseEntity(new ResponseObject(0,"Error : " + e.getMessage()),HttpStatus.OK);
        }

    }
}
