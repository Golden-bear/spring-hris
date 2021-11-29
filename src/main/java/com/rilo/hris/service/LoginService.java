package com.rilo.hris.service;

import com.rilo.hris.model.ResponseObject;
import com.rilo.hris.repository.PegawaiRepository;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.AllArgsConstructor;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import com.rilo.hris.dto.CompanyJoin;
import org.springframework.http.HttpStatus;
import com.rilo.hris.entity.Login;
import com.rilo.hris.entity.Pegawai;
import com.rilo.hris.entity.Company;

import com.rilo.hris.repository.CompanyRepository;
import com.rilo.hris.repository.LoginRepository;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;

import com.rilo.hris.model.ResponLogin;
import com.rilo.hris.model.ResponseModify;
import org.springframework.http.RequestEntity;

import com.rilo.hris.config.JwtTokenUtil;

@Service
@AllArgsConstructor
public class LoginService {

    private LoginRepository repository;

    private PegawaiRepository pegawaiRepository;
    private JwtTokenUtil jwtTokenUtil;

    private CompanyRepository companyRepository;


    @SneakyThrows
    public ResponseEntity<?> logins(Login login){


        if(login.getUsername().isEmpty() || login.getPassword().isEmpty() ){
            throw new RuntimeException("Username dan Password harus diisi");
        }
        Login result = repository.findById(login.getUsername()).orElseThrow();


        var password = login.getPassword();
        MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			 md.update(password.getBytes());
			 byte[] digest = md.digest();
			 String myHash = DatatypeConverter.printHexBinary(digest).toLowerCase(Locale.ROOT);
			 if(!myHash.equals(result.getPassword())){
		            System.out.println("isi myHash : "+myHash);
		            throw new RuntimeException("Login Gagal");

		        }

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(result.getIdEmployee() == 0){

            //Optional<Company> comp = companyRepository.findById(result.getIdCompany());
            CompanyJoin c = companyRepository.getJoinInformation(result.getIdCompany());
            String token = jwtTokenUtil.generateToken(result);
            return new ResponseEntity(new ResponLogin(1,"Login Sukses",c , result,token),HttpStatus.OK);
        }else{
            Optional<Pegawai> pegawai = pegawaiRepository.findById(result.getIdEmployee());
            String token = jwtTokenUtil.generateToken(result);
            return new ResponseEntity(new ResponLogin(1,"Login Sukses",pegawai , result,token),HttpStatus.OK);
        }

       

    }

    public ResponseModify AuthenticateMiddleware(String uniq, String authorization) {

        try {

            if(authorization == null || authorization.contentEquals("")) {
                return new ResponseModify(0, "Need header authorizarion!");
            }

            if(!authorization.startsWith("Bearer")) {
                return new ResponseModify(0, "Authorization start with 'Bearer '!");
            }
            String jwtToken = authorization.substring(7);
            String username = "";
            try {
                username = jwtTokenUtil.getPegawainameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                String errUniq = "["+uniq+"]";

                return new ResponseModify(0, "Unable to get JWT Token!");
            } catch (ExpiredJwtException e) {
                String errUniq = "["+uniq+"]";
                return new ResponseModify(2, "JWT Token has expired!");
            }

            Login user = repository.findById(username).orElseThrow();

            if(user == null) {
                return new ResponseModify(0, "Unable to get JWT Token!");
            }


            boolean check = jwtTokenUtil.validateToken(jwtToken, user);
            if(!check) { return new ResponseModify(2, "No Authorized!");}
            return new ResponseModify(1, "Success!");

        } catch (Exception e) {
            //TODO: handle exception
            String errUniq = "["+uniq+"]";
            return new ResponseModify(0,e.getMessage());
        }
    }


}
