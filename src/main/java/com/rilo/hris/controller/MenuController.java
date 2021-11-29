package com.rilo.hris.controller;

import com.rilo.hris.entity.Menu;
import com.rilo.hris.model.ResponseModify;
import com.rilo.hris.model.ResponseObject;
import com.rilo.hris.service.MenuService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.Map;

@Slf4j
@AllArgsConstructor
@RestController
public class MenuController {

    private MenuService menuService;
    @GetMapping("menu/getbyrelation/{id}")
    public ResponseEntity generateAbsen(@PathVariable int id){

        Date logDate = new Date();
        try {
            log.info("menu/getbyrelation/"+id+" " + logDate);
            return menuService.getByRelation(id);
        }catch (Exception e){
            log.error("menu/getbyrelation/"+id+" " + logDate +" "+e.getMessage());
            return new ResponseEntity(new ResponseModify(0, e.getMessage()),HttpStatus.OK);
        }

    }

    @PutMapping("menu/update")
    public ResponseEntity updateMenu(@RequestBody Menu data){
        Date logDate = new Date();
        try {
            log.info("menu/update/ " + logDate);
            return menuService.updateMenu(data);
        }catch (Exception e){
            log.error("menu/update/  " + logDate +" "+e.getMessage());
            return new ResponseEntity(new ResponseModify(0, e.getMessage()),HttpStatus.OK);
        }
    }

    @DeleteMapping("menu/delete/{idMenu}")
    public ResponseEntity deleteMenu(@PathVariable int idMenu){
        Date logDate = new Date();
        try {
            log.info("menu/delete/"+idMenu+" " + logDate);
            return menuService.deleteMenu(idMenu);
        }catch (Exception e){
            log.error("menu/delete/"+idMenu+" " + logDate +" "+e.getMessage());
            return new ResponseEntity(new ResponseModify(0, e.getMessage()),HttpStatus.OK);
        }
    }
}
