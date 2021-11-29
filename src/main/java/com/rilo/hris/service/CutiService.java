package com.rilo.hris.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rilo.hris.entity.*;
import com.rilo.hris.model.ResponseModify;
import com.rilo.hris.repository.CutiRepository;
import com.rilo.hris.repository.LevelApprovalRepository;
import com.rilo.hris.repository.PegawaiRepository;

import com.rilo.hris.repository.StockcutiRepository;
import com.rilo.hris.util.ConvertDate;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor //jadi tidak usah menggunakan @autowired
public class CutiService {

    private PegawaiRepository pegawaiRepository;
    private LevelApprovalRepository levelApprovalRepository;
    private StockcutiRepository stockcutiRepository;
    private ConvertDate convertDate;
    private CutiRepository cutiRepository;
    private JmsTemplate jmsTemplate;

    public ResponseEntity addCuti(Map<String, String> body){
        int jmlPakai = Integer.parseInt(body.get("JumlahPakai"));
        int idPegawai = Integer.parseInt(body.get("idPegawai"));
        int idJenisCuti = Integer.parseInt(body.get("idJenisCuti"));

        Optional<Pegawai> dataPegawai = pegawaiRepository.findById(idPegawai);

        if(dataPegawai == null){
            return new ResponseEntity(new ResponseModify(1,"Pegawai Tidak Ditemukan"),HttpStatus.OK);
        }


        int div = dataPegawai.get().getDivisi();
        int jobLvl = dataPegawai.get().getJobLevel();
        int comp = dataPegawai.get().getCompany();
        int tingkat = 1;

        //cek atasan ada atau tidak
        LevelApproval lvlApproval = levelApprovalRepository.findExistApproval(div, jobLvl,tingkat, comp);
        if(lvlApproval == null){
            return new ResponseEntity(new ResponseModify(1, "Data Atasan Tidak Ditemukan"), HttpStatus.OK);
        }

        //cek disposisi
        int id_atasans = 0;
        if (lvlApproval.getDisposisi() == null || lvlApproval.getDisposisi() == 0) {
            id_atasans = lvlApproval.getApprovalId();
        } else {
            id_atasans = lvlApproval.getDisposisi();
        }

        //cek atasan aktif atau tidak
        Optional<Pegawai> atasan = pegawaiRepository.findById(id_atasans);
        if(atasan.get().getStatus() == 0){
            return new ResponseEntity(new ResponseModify(1, "Atasan Tidak Aktif"), HttpStatus.OK);
        }

        //cek jenis cuti pengaju
        StockCuti cekJenis = stockcutiRepository.cekJenis(idPegawai, idJenisCuti);
        System.out.println(cekJenis.toString());
        if(cekJenis == null){
            return new ResponseEntity(new ResponseModify(1, "Pegawai tidak memiliki jenis cuti tersebut"), HttpStatus.OK);
        }

        //cek stock cuti
        if(cekJenis.getJmlhSisa() - jmlPakai < 0){
            if(cekJenis.getHistori() != 0){
                if(cekJenis.getHistori() - jmlPakai < 0){
                    return new ResponseEntity(new ResponseModify(1, "Jumlah Pengajuan Melebihi Batas"), HttpStatus.OK);
                }
            }
        }


        Date tglMulai = convertDate.convertStringToDate(body.get("tglMulai"));
        Date tglSelesai = convertDate.convertStringToDate(body.get("tglSelesai"));
        Date today = new Date();

        Cuti saveCuti = new Cuti();
        saveCuti.setIdEmployee(idPegawai);
        saveCuti.setIdJenisCuti(idJenisCuti);
        saveCuti.setTglMulai(tglMulai);
        saveCuti.setTglSelesai(tglSelesai);
        saveCuti.setKeterangan(body.get("keterangan"));
        saveCuti.setAddress(body.get("address"));
        saveCuti.setStatusCuti(0);
        saveCuti.setJumlahPakai(jmlPakai);
        saveCuti.setTingkatApproval(tingkat);
        saveCuti.setApprovalId(id_atasans);
        System.out.println(saveCuti.toString());
        int idCuti = cutiRepository.save(saveCuti).getId();


        //kirim pesan
        Pesan msg = new Pesan();
        msg.setIdPesan(idCuti);
        msg.setStatus(0);
        msg.setDari(idPegawai);
        msg.setKepada(id_atasans);
        msg.setFlag(0);
        msg.setTanggal(today);
        msg.setTingkat(tingkat);
        msg.setTipe(4);
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = "";
            jsonString = mapper.writeValueAsString(msg);
            jmsTemplate.convertAndSend("approvalizin-queue",jsonString);
        }catch (Exception e){
            return new ResponseEntity(new ResponseModify(0,e.getMessage()),HttpStatus.OK);
        }

        return new ResponseEntity(new ResponseModify(1, "Berhasil Mengajukan Cuti"), HttpStatus.OK);

    }

}
