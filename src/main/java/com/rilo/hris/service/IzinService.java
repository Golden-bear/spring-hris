package com.rilo.hris.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rilo.hris.entity.*;
import com.rilo.hris.dto.IzinJoin;
import com.rilo.hris.model.ResponseList;
import com.rilo.hris.model.ResponseModify;
import com.rilo.hris.repository.*;
import com.rilo.hris.util.ConvertDate;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
@AllArgsConstructor //jadi tidak usah menggunakan @autowired
public class IzinService {

    private IzinRepository izinRepository;
    private CompanyRepository companyRepository;
    private PegawaiRepository pegawaiRepository;
    private LevelApprovalRepository levelApprovalRepository;
    private ConvertDate convertDate;
    private PesanRepository pesanRepository;
    private MasterIzinRepository masterIzinRepository;
    private JmsTemplate jmsTemplate;


    public ResponseEntity<?> saveIzin(int idComp, Map<String, String> body){

        Pegawai pegawai = pegawaiRepository.getByIdAndComp(Integer.parseInt(body.get("idEmployee")),idComp);
        if(pegawai == null){
            return new ResponseEntity(new ResponseModify(0,"Pegawai Tidak Ditemukan"), HttpStatus.OK);
        }

        MasterIzin masterIzin = masterIzinRepository.findExistData(Integer.parseInt(body.get("idMasterizin")),idComp);
        if(masterIzin == null){
            return new ResponseEntity(new ResponseModify(0,"Jenis Izin Tidak Ditemukan"), HttpStatus.OK);
        }

        if(masterIzin.getStatusMasterIzin() == 0){
            return new ResponseEntity(new ResponseModify(0,"Jenis Izin Tidak Aktif"), HttpStatus.OK);
        }

        Date gabunganJamDanTgl = convertDate.convertStringToDateLengkap(body.get("tanggalIzin") + " " + body.get("jamIzin"));

        Date jamIzin = convertDate.convertDateToHour(gabunganJamDanTgl);
        Date tglIzin = convertDate.getTglByDate(gabunganJamDanTgl);

        int divisi = pegawai.getDivisi();
        int jobLevel = pegawai.getJobLevel();
        int tingkatApproval = 1;

        LevelApproval levelApproval = levelApprovalRepository.findExistApproval(divisi, jobLevel, tingkatApproval, idComp);
        if(levelApproval == null){
            return new ResponseEntity(new ResponseModify(0,"Atasan Pegawai Tersebut Tidak Ditemukan"), HttpStatus.OK);
        }

        int approval;
        //cek apakah atasan mengalihkan ke disposisi
        if(levelApproval.getDisposisi() == null || levelApproval.getDisposisi() == 0){
            approval = levelApproval.getApprovalId();
        }else{
            approval = levelApproval.getDisposisi();
        }

        Optional<Pegawai> atasan = pegawaiRepository.findById(approval);
        if(atasan.get().getStatus() == 0){
            return new ResponseEntity(new ResponseModify(0,"Atasan Sudah Tidak Aktif, Please Contact Admin"), HttpStatus.OK);
        }

        //save izin
        Izin izin = new Izin();
        izin.setIdPegawai(Integer.parseInt(body.get("idEmployee")));
        izin.setJamIzin(jamIzin);
        izin.setTanggalIzin(tglIzin);
        izin.setKeterangan(body.get("keterangan"));
        izin.setStatusIzin(0);
        izin.setTingkatApproval(tingkatApproval);
        izin.setApprovalId(approval);
        izin.setCompany(idComp);
        izin.setIdMasterIzin(Integer.parseInt(body.get("idMasterizin")));
        int idIzin = izinRepository.save(izin).getId();


        Date tgl = new Date();
        //kirim pesan
        Pesan msg = new Pesan();
        msg.setIdPesan(idIzin);
        msg.setStatus(0);
        msg.setDari(pegawai.getIdEmployee());
        msg.setKepada(approval);
        msg.setFlag(0);
        msg.setTanggal(tgl);
        msg.setTingkat(tingkatApproval);
        msg.setTipe(3);
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = "";
            jsonString = mapper.writeValueAsString(msg);
            jmsTemplate.convertAndSend("approvalizin-queue",jsonString);
        }catch (Exception e){
            return new ResponseEntity(new ResponseModify(0,e.getMessage()),HttpStatus.OK);
        }

        return new ResponseEntity(new ResponseModify(1,"Berhasil Mengajukan Izin"), HttpStatus.OK);
    }

    public ResponseEntity<?> updateTingkatApproval(Izin data){
        int idIzin = data.getId();
        int statusIzin = data.getStatusIzin();

        Optional<Izin> izin = izinRepository.findById(idIzin);
        if(izin == null){
            return new ResponseEntity(new ResponseModify(0,"Data Tidak Ditemukan"), HttpStatus.OK);
        }

        if(izin.get().getStatusIzin() != 0){
            return new ResponseEntity(new ResponseModify(0,"Data Izin Sudah Di update sebelumnya"), HttpStatus.OK);
        }

        Optional<Pegawai> pegawai = pegawaiRepository.findById(izin.get().getIdPegawai());

        Date tanggal = new Date();
        //jika pengajuan izin ditolak
        if(statusIzin != 1){
            Izin tolak = izin.get();
            tolak.setStatusIzin(statusIzin);
            izinRepository.save(tolak);

            //kirim pesan
            Pesan msg = new Pesan();
            msg.setIdPesan(idIzin);
            msg.setStatus(2);
            msg.setDari(izin.get().getApprovalId());
            msg.setKepada(pegawai.get().getIdEmployee());
            msg.setFlag(0);
            msg.setTanggal(tanggal);
            msg.setTingkat(izin.get().getTingkatApproval());
            msg.setTipe(3);
            try {
                ObjectMapper mapper = new ObjectMapper();
                String jsonString = "";
                jsonString = mapper.writeValueAsString(msg);
                jmsTemplate.convertAndSend("approvalizin-queue",jsonString);
            }catch (Exception e){
                return new ResponseEntity(new ResponseModify(0,e.getMessage()),HttpStatus.OK);
            }

            return new ResponseEntity(new ResponseModify(1,"Approval Izin Berhasil Diupdate"), HttpStatus.OK);
        }

        int div = pegawai.get().getDivisi();
        int jobLevel = pegawai.get().getJobLevel();
        int company = pegawai.get().getCompany();
        int incr = izin.get().getTingkatApproval() + 1;

        LevelApproval levelApproval = levelApprovalRepository.findExistApproval(div, jobLevel, incr, company);

        //jika atasan masih ada
        if(levelApproval != null){
            //cek disposisi
            int approval = 0;
            if(levelApproval.getDisposisi() == null || levelApproval.getDisposisi() == 0){
                approval = levelApproval.getApprovalId();
            }else{
                approval = levelApproval.getDisposisi();
            }

            Optional<Pegawai> cekStatusAtasan = pegawaiRepository.findById(approval);
            if(cekStatusAtasan.get().getStatus() == 0){
                return new ResponseEntity(new ResponseModify(1,"Atasan Tidak Aktif"), HttpStatus.OK);
            }

            Izin saveizin = izin.get();
            saveizin.setTingkatApproval(incr);
            saveizin.setApprovalId(approval);
            izinRepository.save(saveizin);

            //kirim pesan
            Pesan msg = new Pesan();
            msg.setIdPesan(idIzin);
            msg.setStatus(0);
            msg.setDari(izin.get().getApprovalId());
            msg.setKepada(approval);
            msg.setFlag(0);
            msg.setTanggal(tanggal);
            msg.setTingkat(incr);
            msg.setTipe(3);

            try {
                ObjectMapper mapper = new ObjectMapper();
                String jsonString = "";
                jsonString = mapper.writeValueAsString(msg);
                jmsTemplate.convertAndSend("approvalizin-queue",jsonString);
            }catch (Exception e){
                return new ResponseEntity(new ResponseModify(0,e.getMessage()),HttpStatus.OK);
            }

            return new ResponseEntity(new ResponseModify(1,"Approval Izin Berhasil Diupdate"), HttpStatus.OK);
        }

        //jika sudah sampai akhir approval
        Izin saveAkhir = izin.get();
        saveAkhir.setStatusIzin(statusIzin);
        izinRepository.save(saveAkhir);

        //kirim pesan
        Pesan msg = new Pesan();
        msg.setIdPesan(idIzin);
        msg.setStatus(statusIzin);
        msg.setDari(izin.get().getApprovalId());
        msg.setKepada(pegawai.get().getIdEmployee());
        msg.setFlag(0);
        msg.setTanggal(tanggal);
        msg.setTingkat(izin.get().getTingkatApproval());
        msg.setTipe(3);


        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = "";
            jsonString = mapper.writeValueAsString(msg);
            jmsTemplate.convertAndSend("approvalizin-queue",jsonString);
        }catch (Exception e){
            return new ResponseEntity(new ResponseModify(0,e.getMessage()),HttpStatus.OK);
        }

        return new ResponseEntity(new ResponseModify(1,"Approval Izin Berhasil Diupdate"), HttpStatus.OK);
    }

    public ResponseEntity<?> viewForByTingkatApproval(int idApproval){
        List<IzinJoin> ij = izinRepository.izinJoin(idApproval,0);
        return new ResponseEntity(new ResponseList(1, "get Data Sukses", ij), HttpStatus.OK);

    }

    public ResponseEntity<?> getByRangeTgl(Map<String,String> body){
        Date tglStart = convertDate.convertStringToDate(body.get("tanggalStart"));


        if(body.get("tanggalStart") != null && body.get("tanggalEnd") != null){
            Date tglEnd = convertDate.convertStringToDate(body.get("tanggalEnd"));
            List<IzinJoin> izinJoins = izinRepository.rangeTanggal(Integer.parseInt(body.get("idCompany")), tglStart, tglEnd);
            return new ResponseEntity(new ResponseList(1, "get Data Sukses", izinJoins), HttpStatus.OK);
        }

        List<IzinJoin> izinJoin = izinRepository.byTanggal(Integer.parseInt(body.get("idCompany")), tglStart);
        return new ResponseEntity(new ResponseList(1, "get Data Sukses", izinJoin), HttpStatus.OK);


    }
}
