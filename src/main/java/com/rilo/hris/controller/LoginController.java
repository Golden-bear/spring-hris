package com.rilo.hris.controller;

import com.rilo.hris.entity.Login;
import com.rilo.hris.service.LoginService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
@RestController
@AllArgsConstructor
public class LoginController {


    private LoginService service;

    @PostMapping("/login")
    public ResponseEntity loginUser(@RequestBody Login login){
        return service.logins(login);
    }
}
