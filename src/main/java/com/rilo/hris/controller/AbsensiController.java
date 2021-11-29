package com.rilo.hris.controller;

import com.rilo.hris.model.ResponseModify;
import com.rilo.hris.model.ResponseObject;
import com.rilo.hris.service.AbsensiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.Map;

@Slf4j
@RestController
public class AbsensiController {
	
	@Autowired
	private AbsensiService service;
	
	@GetMapping("absen/generate")
    public ResponseEntity generateAbsen(){

		Date logDate = new Date();
		try {
			log.info("absen/generate " + logDate);
			return service.generateAbsensi();
		}catch (Exception e){
			log.error("absen/generate error : "+logDate+" "+e.getMessage());
			return new ResponseEntity(new ResponseModify(0, e.getMessage()),HttpStatus.OK);
		}

    }
	
	@GetMapping("absen/company/{company}")
    public ResponseEntity getByCompany(@PathVariable int company){
		Date logDate = new Date();
		try {
			log.info("absen/company/"+company+" "+logDate);
			return service.getByCompany(company);
		}catch (Exception e){
			log.error("absen/company/{company} : "+e.getMessage() +" "+logDate);
			return new ResponseEntity(new ResponseModify(0, e.getMessage()),HttpStatus.OK);
		}

    }

	@GetMapping("absen/today/{company}")
	public ResponseEntity getCheckinsByComp(@PathVariable int company){
		Date logDate = new Date();
		try{
			log.info("absen/today/{company} " + logDate);
			return service.getCheckinTodayByCompany(company);
		}catch (Exception e){
			log.error("absen/today/{company} : "+logDate+" "+e.getMessage());
			return new ResponseEntity(new ResponseModify(0, e.getMessage()),HttpStatus.OK);
		}

	}
	
	@PostMapping("absen/masuk")
	 public ResponseEntity absenMasuk(@RequestParam Map<String, String> body, @RequestParam("foto_in") MultipartFile file){
		if(body.get("idPegawai").toString().isEmpty() || body.get("idPegawai") == null) return new ResponseEntity(new ResponseModify(0, "idPegawai harus di isi"),HttpStatus.OK);
		if(body.get("CaptionIn").toString().isEmpty() || body.get("CaptionIn")==null) return new ResponseEntity(new ResponseModify(0, "CaptionIn harus di isi"),HttpStatus.OK);
		if(body.get("LongIn").toString().isEmpty() || body.get("LongIn")==null) return new ResponseEntity(new ResponseModify(0, "LongIn harus di isi"),HttpStatus.OK);
		if(body.get("LatIn").toString().isEmpty() || body.get("LatIn")==null) return new ResponseEntity(new ResponseModify(0, "LatIn harus di isi"),HttpStatus.OK);
		if(body.get("Type").toString().isEmpty() || body.get("Type")==null) return new ResponseEntity(new ResponseModify(0, "Type harus di isi"),HttpStatus.OK);
		if(!body.get("Type").contentEquals("check_in")) return new ResponseEntity(new ResponseModify(0, "Type Salah"),HttpStatus.OK);

		Date logDate = new Date();
		try{
			log.info("absen/masuk "+logDate);
			return service.absensiMasuk(body, file);
		} catch (Exception e){
			log.error("absen/masuk : "+e.getMessage()+" "+logDate);
			e.printStackTrace();
			return new ResponseEntity(new ResponseObject(0,"Error"),HttpStatus.OK);
		}

    }


	@PostMapping("absen/keluar")
	public ResponseEntity absenKeluar(@RequestParam Map<String, String> body, @RequestParam("foto_out") MultipartFile file){
		if(body.get("idPegawai").toString().isEmpty() || body.get("idPegawai") == null) return new ResponseEntity(new ResponseModify(0, "idPegawai harus di isi"),HttpStatus.OK);
		if(body.get("CaptionOut").toString().isEmpty() || body.get("CaptionOut")==null) return new ResponseEntity(new ResponseModify(0, "CaptionIn harus di isi"),HttpStatus.OK);
		if(body.get("LongOut").toString().isEmpty() || body.get("LongOut")==null) return new ResponseEntity(new ResponseModify(0, "LongIn harus di isi"),HttpStatus.OK);
		if(body.get("LatOut").toString().isEmpty() || body.get("LatOut")==null) return new ResponseEntity(new ResponseModify(0, "LatIn harus di isi"),HttpStatus.OK);
		if(body.get("Type").toString().isEmpty() || body.get("Type")==null) return new ResponseEntity(new ResponseModify(0, "Type harus di isi"),HttpStatus.OK);
		if(!body.get("Type").contentEquals("check_out")) return new ResponseEntity(new ResponseModify(0, "Type Salah"),HttpStatus.OK);

		Date logDate = new Date();
		try{
			log.info("absen/keluar "+logDate);
			return service.absensiKeluar(body, file);
		} catch (Exception e){
			log.error("absen/keluar : "+e.getMessage()+" "+logDate);
			e.printStackTrace();
			return new ResponseEntity(new ResponseObject(0,"Error"),HttpStatus.OK);
		}

	}

	@PutMapping(value = "absen/updatecheckin/{id}")
	public ResponseEntity updateCheckin(@RequestBody Map<String, String> body, @PathVariable int id){

		if(body.get("statusIn") == null) return new ResponseEntity(new ResponseModify(0, "StatusIn harus di isi"),HttpStatus.OK);

		Date logDate = new Date();
		try{
			log.info("absen/updatecheckin/{id} "+logDate );
			return service.updateAbsenCheckinByTingkat(body, id);
		} catch (Exception e){
			log.error("absen/updatecheckin/{id} : "+e.getMessage()+" "+logDate);
			e.printStackTrace();
			return new ResponseEntity(new ResponseObject(0,"Error"),HttpStatus.OK);
		}

	}

	@PutMapping(value = "absen/updatecheckout/{id}")
	public ResponseEntity updateCheckout(@RequestBody Map<String, String> body, @PathVariable int id){

		if(body.get("statusOut") == null) return new ResponseEntity(new ResponseModify(0, "statusOut harus di isi"),HttpStatus.OK);

		Date logDate = new Date();
		try{
			log.info("absen/updatecheckout/{id} "+logDate);
			return service.updateAbsenCheckoutByTingkat(body, id);
		} catch (Exception e){
			log.error("absen/updatecheckout/{id} : "+e.getMessage() + " "+logDate);
			e.printStackTrace();
			return new ResponseEntity(new ResponseObject(0,"Error"),HttpStatus.OK);
		}

	}


	@PostMapping("absen/getbyrangetanggal/{id_company}")
	public ResponseEntity getByRangeDate(@RequestBody Map<String, String> body, @PathVariable int id_company){

		if(body.get("tanggalStart") == null) return new ResponseEntity(new ResponseModify(0, "tanggalStart harus di isi"),HttpStatus.OK);

		Date logDate = new Date();
		try{
			log.info("absen/getbyrangetanggal/{id_company} "+logDate);
			return service.getTanggalByCompany(id_company, body);
		} catch (Exception e){
			log.error("absen/getbyrangetanggal/{id_company} : "+e.getMessage()+" "+logDate);
			e.printStackTrace();
			return new ResponseEntity(new ResponseObject(0,"Error"),HttpStatus.OK);
		}

	}
}
