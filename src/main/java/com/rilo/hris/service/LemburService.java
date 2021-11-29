package com.rilo.hris.service;

import com.rilo.hris.dto.LemburJoin;
import com.rilo.hris.entity.Lembur;
import com.rilo.hris.model.ResponseList;
import com.rilo.hris.model.ResponseModify;
import com.rilo.hris.repository.LemburRepository;
import com.rilo.hris.util.ConvertDate;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor //jadi tidak usah menggunakan @autowired
public class LemburService {

    private LemburRepository lemburRepository;
    private ConvertDate convertDate;

    public ResponseEntity<?> save(Map<String, String> body){

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat dt = new SimpleDateFormat("HH:mm:ss");

        System.out.println(body.get("tanggal"));
        System.out.println(body.get("jamIn"));
        String batas = "17:00:00";
        Date jam = convertDate.convertStringToHour(body.get("jamIn"));
        Date tgl = convertDate.convertStringToDate(body.get("tanggal"));
        Date batasJam = convertDate.convertStringToHour(batas);


        //jika pegawai request sebelum jam pulang kantor
        if(jam.before(batasJam)){
            return new ResponseEntity(new ResponseModify(1,"Belum memasuki waktu lembur"),HttpStatus.OK);
        }

        System.out.println(jam);
        System.out.println(tgl);

        Lembur lembur = new Lembur();
        lembur.setIdPegawai(Integer.parseInt(body.get("idPegawai")));
        lembur.setTanggal(tgl);
        lembur.setTugas(body.get("tugas"));
        lembur.setIdProject(Integer.parseInt(body.get("idProject")));
        lembur.setIdDilaporkan(Integer.parseInt(body.get("idDilaporkan")));
        lembur.setJamIn(jam);
        lembur.setCompany(Integer.parseInt(body.get("company")));

        lemburRepository.save(lembur);

        return new ResponseEntity(new ResponseModify(1,"Lembur berhasil disimpan"),HttpStatus.OK);
    }

    public ResponseEntity<?> updateLembur(int id_lembur, Map<String,String> body){

        Optional<Lembur> l = lemburRepository.findById(id_lembur);

        Date jamIn = convertDate.convertDateToHour(l.get().getJamIn());
        Date jamOut = convertDate.convertStringToHour(body.get("jamOut"));


        long diff = jamOut.getTime() - jamIn.getTime();
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;


        Lembur input = l.get();
        input.setJamOut(jamOut);
        input.setDurasi(Long.toString(diffHours) + " Jam " + Long.toString(diffMinutes) + " Menit");
        lemburRepository.save(input);

        return new ResponseEntity(new ResponseModify(1,"Lembur berhasil di update"),HttpStatus.OK);

    }

    public ResponseEntity<?> getByCompany(int comp){
        List<LemburJoin> lj  = lemburRepository.getJoinByComp(comp);

        return new ResponseEntity(new ResponseList(1,"Berhasil Get Data",lj),HttpStatus.OK);
    }

    public ResponseEntity<?> getRangeTanggalByComp(int comp, Map<String,String> body){

        Date tglStart = convertDate.convertStringToDate(body.get("tanggalStart"));

        //jika kedua nya di isi
        if(body.get("tanggalStart") != null && body.get("tanggalEnd") != null){
            Date tglEnd = convertDate.convertStringToDate(body.get("tanggalEnd"));
            List<LemburJoin> lj = lemburRepository.getBytanggalRange(comp, tglStart, tglEnd);
            return new ResponseEntity(new ResponseList(1,"Berhasil Get Data",lj),HttpStatus.OK);
        }

        List<LemburJoin> lj = lemburRepository.getByTanggal(comp, tglStart);
        return new ResponseEntity(new ResponseList(1,"Berhasil Get Data",lj),HttpStatus.OK);
    }

}
