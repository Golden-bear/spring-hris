package com.rilo.hris.controller;

import com.rilo.hris.entity.Login;
import com.rilo.hris.model.ResponseModify;
import com.rilo.hris.service.LoginService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
@RestController
@AllArgsConstructor
public class LoginController {


    private LoginService service;

    @PostMapping("/login")
    public ResponseEntity loginUser(@RequestBody Login login){
        Date logDate = new Date();
        try{
            log.info("/login");
            return service.logins(login);
        }catch (Exception e){
            log.error("/login "+logDate +" "+e.getMessage());
            return new ResponseEntity(new ResponseModify(0, e.getMessage()), HttpStatus.OK);
        }

    }
}
