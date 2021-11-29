package com.rilo.hris.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rilo.hris.entity.Divisi;
import com.rilo.hris.entity.Pesan;
import com.rilo.hris.service.DivisiService;
import com.rilo.hris.service.IzinService;
import com.rilo.hris.service.PesanService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Slf4j
@Component
public class ListenerTest {

    private DivisiService service;
    private IzinService izinService;
    private PesanService pesanService;

    @JmsListener(destination = "bridginingcode-queue")
    public void messageListener(String divisi){
        //log.info("Message received : ");
        System.out.println("message received");
        ObjectMapper mapper = new ObjectMapper();
        Divisi data = null;
        try{
            data = mapper.readValue(divisi, Divisi.class);
            service.save(data);
        }catch (Exception e){
            //log.error(e.getMessage());
            e.getMessage();
        }

    }
    @JmsListener(destination = "approvalizin-queue")
    public void messageListenerApprovalIzin(String pesan){
        log.info("Message received : " + pesan);
        System.out.println("message received");
        ObjectMapper mapper = new ObjectMapper();
        Pesan data = null;
        try{
            data = mapper.readValue(pesan, Pesan.class);
            pesanService.savePesan(data);
        }catch (Exception e){
            log.error(e.getMessage());
            e.getMessage();
        }
    }
}
