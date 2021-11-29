package com.rilo.hris.model;

import com.rilo.hris.entity.Login;


public class ResponLogin {
    private int status;
    private String message;
    private Object user;
    private Login logins;
    private String token;

    public ResponLogin(int status, String message,Object user, Login logins, String token){
        this.status = status;
        this.message = message;
        this.user = user;
        this.logins = logins;
        this.token = token;
    }



    public Login getLogins() {
        return logins;
    }

    public void setLogins(Login logins) {
        this.logins = logins;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getUser() {
        return user;
    }

    public void setUser(Object user) {
        this.user = user;
    }
}
