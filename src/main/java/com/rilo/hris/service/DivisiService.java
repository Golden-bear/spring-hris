package com.rilo.hris.service;

import com.rilo.hris.entity.Divisi;
import com.rilo.hris.model.ResponseList;
import com.rilo.hris.model.ResponseModify;
import com.rilo.hris.model.ResponseObject;
import com.rilo.hris.repository.DivisiRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.Optional;


@Service
@AllArgsConstructor
public class DivisiService {


    private DivisiRepository repository;
    //private Logger logg;

//    @Autowired
//    public DivisiService(DivisiRepository repository) {
//        this.repository =repository;
//        //logg = LoggerFactory.getLogger(DivisiService.class);
//    }

    public void save(Divisi divisi){
        this.repository.save(divisi);
    }

    public ResponseEntity<?> saveDivisi(Map<String, String> body){
        try{
            //logg.info("hit save divisi");
        	Divisi div = new Divisi();
        	div.setCompany(Integer.parseInt(body.get("idCompany")));
        	div.setNameDivision(body.get("nameDivision"));
      
            repository.save(div);
            
            return new ResponseEntity(new ResponseModify(1,"Divisi Berhasil Dihapus"),HttpStatus.OK);
        }catch (Exception e){
        	//logg.error(e.toString());
            return new ResponseEntity(new ResponseModify(0,e.getMessage()),HttpStatus.OK);
        }

    }

    public ResponseEntity<?> getDivisi(){

        String method = "GET";
        String url = "/getalldivisi";
        try{
        	System.out.println(new Date());
        	//logg.info(method+" "+url);
            var listDivisi  =  repository.findAll();
            return new ResponseEntity(new ResponseList(1,"get Data Sukses", listDivisi ),HttpStatus.OK);
        }catch (Exception e){
        	//logg.error(method+" "+url+ e.getMessage());
            throw new RuntimeException(e);
        }

    }

    public ResponseEntity<?> getDivisiById(int idDivisi){
        try{
        	//logg.info("berhasil get by id");
            Optional<Divisi> dt = repository.findById(idDivisi);
            return new ResponseEntity(new ResponseObject(1,"get Data Sukses", dt ),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(new ResponseObject(0, e.getMessage()),HttpStatus.OK);
        }

    }

    public ResponseEntity<?> getDivisiByCompany(int company){
        try{
            //logg.info("divisi byCompany");
            var listDivisiByCompany  =  repository.findByCompany(company);
            return new ResponseEntity(new ResponseList(1,"get Data Sukses", listDivisiByCompany ),HttpStatus.OK);
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    public ResponseEntity<?> deleteDivisi(int id_divisi){
        try{
            //logg.info("delete divisi");
            repository.deleteById(id_divisi);
            return new ResponseEntity(new ResponseModify(1,"Divisi Berhasil Dihapus"),HttpStatus.OK);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<?> updateDivisi(Divisi divisi){
        try{
            //logg.info("update divisi");
            Divisi existingDivisi=repository.findById(divisi.getIdDivisi()).orElse(divisi);
            existingDivisi.setNameDivision(divisi.getNameDivision());
            repository.save(existingDivisi);
            return new ResponseEntity(new ResponseModify(1,"Divisi Berhasil Diperbaruhi"),HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity(new ResponseModify(0,e.getMessage()),HttpStatus.OK);
        }

    }
}
