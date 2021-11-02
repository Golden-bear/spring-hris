package com.rilo.hris.service;

import lombok.AllArgsConstructor;

import java.security.MessageDigest;

import java.util.Locale;

import org.springframework.http.HttpStatus;
import com.rilo.hris.entity.Login;
import com.rilo.hris.repository.LoginRepository;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;

import com.rilo.hris.model.ResponseObject;
import com.rilo.hris.model.ResponseModify;
import org.springframework.http.RequestEntity;
@Service
@AllArgsConstructor
public class LoginService {

    private LoginRepository repository;

    @SneakyThrows
    public ResponseEntity<?> logins(Login login){


        if(login.getUsername().isEmpty() || login.getPassword().isEmpty() ){
            throw new RuntimeException("Username dan Password harus diisi");
        }
        Login result = repository.findById(login.getUsername()).orElseThrow();

        var password = login.getPassword();
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String myHash = DatatypeConverter.printHexBinary(digest).toLowerCase(Locale.ROOT);

        //bandingin
        if(!myHash.equals(result.getPassword())){
            System.out.println("isi myHash : "+myHash);
            throw new RuntimeException("Login Gagal");

        }

        return new ResponseEntity(new ResponseModify(1,"Login Sukses"),HttpStatus.OK);
    }


}
