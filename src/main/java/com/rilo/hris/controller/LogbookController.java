package com.rilo.hris.controller;


import java.util.List;
import com.rilo.hris.entity.Divisi;
import com.rilo.hris.entity.Logbook;
import com.rilo.hris.model.ResponseList;
import com.rilo.hris.service.DivisiService;
import com.rilo.hris.service.LogbookService;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rilo.hris.client.LogbookClient;


@RestController
public class LogbookController {

	private Logger logg = LoggerFactory.getLogger(LogbookController.class);
	
    @Autowired
    private LogbookService service;

    @Autowired
    LogbookClient logbookClient;

    @GetMapping("/logbookNw/{id}")
    public String logbookById(@PathVariable String id){
        String nik_atasan = logbookClient.getAtasanByNIK(id);
        return nik_atasan;
    }
    
    @GetMapping("/logbookbynik/{id}")
    public ResponseEntity<?> logbookbynik(@PathVariable String id){
    	logg.info("berhasil hit logbookbynik");
    	
    	//<logbook> balikan sesuai dengan apa yg ada di entity
        List<Logbook> dataku = logbookClient.getProjectByNIK(id);
        return new ResponseEntity(new ResponseList(1,"get Data Sukses",dataku),HttpStatus.OK);
    }

}
