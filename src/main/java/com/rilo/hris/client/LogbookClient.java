package com.rilo.hris.client;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.rilo.hris.util.MyResponseErrorHandler;
import com.rilo.hris.entity.Logbook;

@Component
public class LogbookClient {

    @Autowired
    RestTemplateBuilder builder;

    @Value("${logbook.token.authorization}")
    String authorization_logbook;

    @Value(value = "${base.url}")
    private String basUrl;

   
    RestTemplate restTemplate;

    String urlLogbook = "http://aplikasi2.gratika.id/coreapi/core-logbook";
    String urlSimpok = "http://www.gratika.co.id/apigrtk/index.php/web_service/api";
    @PostConstruct
	public void init() {
		// TODO Auto-generated constructor stub
		this.restTemplate = this.builder.errorHandler(new MyResponseErrorHandler()).build();
	}
       
        @SuppressWarnings({ "unchecked", "rawtypes" }) //supaya menghilangkan warning (opsional)
        
        //<logbook> balikan sesuai dengan apa yg ada di entity
        public List<Logbook> getProjectByNIK(String nik) {

            String jsonRequest = null;
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Authorization", authorization_logbook);
            HttpEntity request = new HttpEntity(jsonRequest, headers);
            String API = urlLogbook+"/project/nik/"+nik;


            //balikan dari api yg di hit, di anologikan jadi string
            ResponseEntity<String> response = restTemplate.exchange(API,
                    HttpMethod.GET, request, new ParameterizedTypeReference<String>() {
                    });

            
            JSONObject respon = new JSONObject(response.getBody());
            
            List<Logbook> data = new ArrayList<Logbook>();
            if(respon.getString("code").contentEquals("0")) {

                System.out.println("Data Pegawai tidak ada");
            } else {

                JSONArray ar = respon.getJSONArray("data");
                for (int i = 0; i < ar.length(); i++) {
                    JSONObject proapi = new JSONObject();
                    Logbook dt = new Logbook();
                    proapi = ar.getJSONObject(i);
                    dt.setId_project(proapi.getInt("id_project"));
                    dt.setNama_project(proapi.getString("nama_project"));
                    dt.setStatus(proapi.getInt("status"));
                    dt.setId_divisi(proapi.getString("id_divisi"));
                    dt.setId_subdiv(proapi.getString("id_subdiv"));
                    dt.setNama_divisi(proapi.getString("nama_divisi"));
                    dt.setNama_subdiv(proapi.getString("nama_subdiv"));
                    data.add(dt);
                }
            }
            return data;
        }

  
    public String getAtasanByNIK(String id) {
        HttpHeaders headers = new HttpHeaders(); //headers
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
        body.add("apiKey", "350ea7e2af60c9d3824791dd122272d8");
        body.add("access", "G16001K");
        body.add("apicode", "2");
        body.add("nik", id);

        String url = this.urlSimpok + "/11/NIK/0/0/0";

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, request , String.class);

        JSONObject respon = new JSONObject(response.getBody());

        String atasan = "";

        atasan = respon.getString("info");

        return atasan;
    }

}
