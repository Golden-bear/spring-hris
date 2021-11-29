package com.rilo.hris.util;

import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ConvertDate {
    public Date convertStringToHour(String jam){
        DateFormat dt = new SimpleDateFormat("HH:mm:ss");

        Date x = null;
        try {
            x = dt.parse(jam);
        }catch (Exception e){
            e.getMessage();
        }

        return x;
    }

    public Date convertDateToHour(Date jam){
        DateFormat dt = new SimpleDateFormat("HH:mm:ss");

        Date hasil = null;
        try {
            hasil = dt.parse(dt.format(jam));
        }catch (Exception e){
            e.getMessage();
        }

        return hasil;
    }

    public Date convertStringToDate(String tgl){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        Date hasil = null;
        try {
            //mengambil tanggal saja dari today
            hasil = df.parse(tgl);

        } catch (Exception e) {
            e.getMessage();
        }

        return hasil;
    }

    public Date convertStringToDateLengkap(String tgl){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date hasil = null;
        try {
            //mengambil tanggal saja dari today
            hasil = df.parse(tgl);

        } catch (Exception e) {
            e.getMessage();
        }

        return hasil;
    }

    public Date getTglByDate(Date tgl){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        Date hasil = null;
        try {
            //mengambil tanggal saja dari today
            hasil = df.parse(df.format(tgl));

        } catch (Exception e) {
            e.getMessage();
        }

        return hasil;
    }
}
