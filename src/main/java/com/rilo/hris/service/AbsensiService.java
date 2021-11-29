package com.rilo.hris.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rilo.hris.dto.AbsensiJoin;
import com.rilo.hris.entity.Absensi;
import com.rilo.hris.entity.LevelApproval;
import com.rilo.hris.entity.Pegawai;
import com.rilo.hris.entity.Pesan;
import com.rilo.hris.model.ResponseList;
import com.rilo.hris.model.ResponseModify;
import com.rilo.hris.property.FileStorageProperties;
import com.rilo.hris.repository.AbsensiRepository;
import com.rilo.hris.repository.LevelApprovalRepository;
import com.rilo.hris.repository.PegawaiRepository;
import com.rilo.hris.repository.PesanRepository;
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
public class AbsensiService {
    //private Logger logg = LoggerFactory.getLogger(AbsensiService.class);
    private AbsensiRepository repositoryAbsen;
    private PegawaiRepository repositoryPegawai;
    private LevelApprovalRepository repositoryLevelApproval;
    private UploadService uploadService;
    private PesanRepository repositoryPesan;
    private ConvertDate convertDate;
    FileStorageProperties fileStorageProperties;
    private JmsTemplate jmsTemplate;

    public ResponseEntity<?> generateAbsensi() {

        try {

            List<Pegawai> user = repositoryPegawai.findAll();

            //looping data pegawai untuk diambil idEmployee dan dijadikan generate absen
            for (Pegawai pegawai : user) {
                //cek pegawai yang aktif dengan parameter status pegawai
                if (pegawai.getStatus() == 1) {
                    Date today = new Date();

                    Absensi ab = new Absensi();
                    ab.setIdPegawai(pegawai.getIdEmployee());
                    ab.setCompany(pegawai.getCompany());
                    ab.setTanggalIn(today);
                    ab.setKeterangan("a");
                    ab.setCaptionIn("");
                    ab.setCaptionOut("");
                    ab.setLatIn("");
                    ab.setLatOut("");
                    ab.setLongIn("");
                    ab.setLongOut("");
                    ab.setFotoIn("");
                    ab.setFotoOut("");
                    ab.setStatusIn(0);
                    ab.setStatusOut(0);
                    ab.setTingkatApprovalIn(0);
                    ab.setTingkatApprovalOut(0);
                    ab.setApprovalIdIn(0);
                    ab.setApprovalIdOut(0);

                    repositoryAbsen.save(ab);
                }
            }
            return new ResponseEntity(new ResponseModify(1, "Generate Absensi Sukses"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new ResponseModify(0, e.getMessage()), HttpStatus.OK);
        }

    }

    @Transactional
    public ResponseEntity<?> absensiMasuk(Map<String, String> body, MultipartFile file) {


        //optional bisa kosong atau isi
        Optional<Pegawai> dataPegawai = repositoryPegawai.findById(Integer.parseInt(body.get("idPegawai")));

        if (!dataPegawai.isPresent()){
            return new ResponseEntity(new ResponseModify(1, "Data Pegawai Tidak Ditemukan"), HttpStatus.OK);
        }

        Date today = new Date();
        Date todays = convertDate.getTglByDate(today);

        Absensi absens = repositoryAbsen.findByToday(Integer.parseInt(body.get("idPegawai")), todays);

        int idDivisi = dataPegawai.get().getDivisi();
        int job_level = dataPegawai.get().getJobLevel();
        int id_company = dataPegawai.get().getCompany();
        int tingkat = 1;

        //cek duplikat absen
        if (!absens.getCaptionIn().isEmpty()){
            return new ResponseEntity(new ResponseModify(0, "Anda Sudah Absen"), HttpStatus.OK);
        }

        //cek atasan ada atau tidak
        LevelApproval lvlApproval = repositoryLevelApproval.findExistApproval(idDivisi, job_level,tingkat, id_company);

        if (lvlApproval == null){
            return new ResponseEntity(new ResponseModify(1, "Data Atasan Tidak Ditemukan"), HttpStatus.OK);
        }

        int id_atasans = 0;
        if (lvlApproval.getDisposisi() == null || lvlApproval.getDisposisi() == 0) {
            id_atasans = lvlApproval.getApprovalId();
        } else {
            id_atasans = lvlApproval.getDisposisi();
        }

        //cek atasan aktif atau tidak
        Optional<Pegawai> cekAtasan = repositoryPegawai.findById(id_atasans);
        if (cekAtasan.get().getStatus() == 0)
            return new ResponseEntity(new ResponseModify(1, "Atasan Tidak Aktif"), HttpStatus.OK);


        //upload file
        String fileName = uploadService.storeFile(file, "foto_checkin");

        //update absen
        absens.setAbsenIn(today);
        absens.setCaptionIn(body.get("CaptionIn"));
        absens.setLatIn(body.get("LatIn"));
        absens.setLongIn(body.get("LongIn"));
        absens.setStatusIn(0);
        absens.setTingkatApprovalIn(tingkat);
        absens.setApprovalIdIn(id_atasans);
        absens.setFotoIn(fileName);

        this.repositoryAbsen.save(absens);

        //kirim pesan
        Pesan msg = new Pesan();
        msg.setIdPesan(absens.getId());
        msg.setStatus(0);
        msg.setDari(Integer.parseInt(body.get("idPegawai")));
        msg.setKepada(id_atasans);
        msg.setFlag(0);
        msg.setTanggal(today);
        msg.setTingkat(tingkat);
        msg.setTipe(1);
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = "";
            jsonString = mapper.writeValueAsString(msg);
            jmsTemplate.convertAndSend("approvalizin-queue",jsonString);
        }catch (Exception e){
            return new ResponseEntity(new ResponseModify(0,e.getMessage()),HttpStatus.OK);
        }

        return new ResponseEntity(new ResponseModify(1, "Berhasil Absen"), HttpStatus.OK);


    }

    @Transactional
    public ResponseEntity<?> absensiKeluar(Map<String, String> body, MultipartFile file) {

        //optional bisa kosong atau isi
        Optional<Pegawai> dataPegawai = repositoryPegawai.findById(Integer.parseInt(body.get("idPegawai")));

        if (!dataPegawai.isPresent()){
            return new ResponseEntity(new ResponseModify(1, "Data Pegawai Tidak Ditemukan"), HttpStatus.OK);
        }


        Date today = new Date();

        Date todays = convertDate.getTglByDate(today);

        Absensi absens = repositoryAbsen.findByToday(Integer.parseInt(body.get("idPegawai")), todays);

        int idDivisi = dataPegawai.get().getDivisi();
        int job_level = dataPegawai.get().getJobLevel();
        int id_company = dataPegawai.get().getCompany();
        int tingkat = 1;

        //cek duplikat absen
        if (!absens.getCaptionOut().isEmpty()){
            return new ResponseEntity(new ResponseModify(0, "Anda Sudah Checkout"), HttpStatus.OK);
        }

        //cek checkin sudah di approv apa belum
        if(absens.getStatusIn() == 0){
            return new ResponseEntity(new ResponseModify(0, "Checkin Anda Belum Di Approv"), HttpStatus.OK);
        }else if(absens.getStatusIn() == 2){
            return new ResponseEntity(new ResponseModify(0, "Absen Checkin Anda Ditolak, Maka Tidak Bisa Melakukan Checkout"), HttpStatus.OK);
        }

        //cek atasan ada atau tidak
        LevelApproval lvlApproval = repositoryLevelApproval.findExistApproval(idDivisi, job_level,tingkat, id_company);

        if (lvlApproval == null){
            return new ResponseEntity(new ResponseModify(1, "Data Atasan Tidak Ditemukan"), HttpStatus.OK);
        }

        int id_atasans = 0;
        if (lvlApproval.getDisposisi() == null || lvlApproval.getDisposisi() == 0) {
            id_atasans = lvlApproval.getApprovalId();
        } else {
            id_atasans = lvlApproval.getDisposisi();
        }

        //cek atasan aktif atau tidak
        Optional<Pegawai> cekAtasan = repositoryPegawai.findById(id_atasans);
        if (cekAtasan.get().getStatus() == 0)
            return new ResponseEntity(new ResponseModify(1, "Atasan Tidak Aktif"), HttpStatus.OK);


        //upload file
        String fileName = uploadService.storeFile(file, "foto_checkout");

        //update absen
        absens.setAbsenOut(today);
        absens.setCaptionOut(body.get("CaptionOut"));
        absens.setLatOut(body.get("LatOut"));
        absens.setLongOut(body.get("LongOut"));
        absens.setStatusOut(0);
        absens.setTingkatApprovalOut(tingkat);
        absens.setApprovalIdOut(id_atasans);
        absens.setFotoOut(fileName);

        this.repositoryAbsen.save(absens);

        //kirim pesan
        Pesan msg = new Pesan();
        msg.setIdPesan(absens.getId());
        msg.setStatus(0);
        msg.setDari(Integer.parseInt(body.get("idPegawai")));
        msg.setKepada(id_atasans);
        msg.setFlag(0);
        msg.setTanggal(today);
        msg.setTingkat(tingkat);
        msg.setTipe(2);

        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = "";
            jsonString = mapper.writeValueAsString(msg);
            jmsTemplate.convertAndSend("approvalizin-queue",jsonString);
        }catch (Exception e){
            return new ResponseEntity(new ResponseModify(0,e.getMessage()),HttpStatus.OK);
        }

        return new ResponseEntity(new ResponseModify(1, "Berhasil Absen"), HttpStatus.OK);


    }


    @Transactional
    public ResponseEntity<?> updateAbsenCheckinByTingkat(Map<String, String> body, int idAbsen){
        //cek absen apakah sudah di update atau belum
        Absensi absensi = repositoryAbsen.findByIdStatusIn(idAbsen,0);
        if(absensi == null){
            return new ResponseEntity(new ResponseModify(0, "absensi sudah di update sebelumnya"), HttpStatus.OK);
        }

        if(absensi == null){
            return new ResponseEntity(new ResponseModify(0, "Data Absen Tidak Ditemukan"), HttpStatus.OK);
        }

        //get data pegawai
        Optional<Pegawai> pegawai = repositoryPegawai.findById(absensi.getIdPegawai());
        if(pegawai == null){
            return new ResponseEntity(new ResponseModify(0, "Data Pegawai Tidak Ditemukan"), HttpStatus.OK);
        }

        int div = pegawai.get().getDivisi();
        int jobLevel = pegawai.get().getJobLevel();
        int comp = pegawai.get().getCompany();
        int approvLama = absensi.getApprovalIdIn();

        int incr = absensi.getTingkatApprovalIn() + 1;
        int idEmp = absensi.getIdPegawai();
        int tingkat = absensi.getTingkatApprovalIn();

        //jika status absen tidak disetujui
        if(Integer.parseInt(body.get("statusIn")) == 2){
            System.out.println("ini jika tidak disetujui");

            //update absen
            absensi.setStatusIn(2);
            this.repositoryAbsen.save(absensi);

            //kirim pesan
            Pesan msg = new Pesan();
            msg.setIdPesan(absensi.getId());
            msg.setStatus(2);
            msg.setDari(approvLama);
            msg.setKepada(idEmp);
            msg.setFlag(0);
            msg.setTanggal(new Date());
            msg.setTingkat(tingkat);
            msg.setTipe(1);
            try {
                ObjectMapper mapper = new ObjectMapper();
                String jsonString = "";
                jsonString = mapper.writeValueAsString(msg);
                jmsTemplate.convertAndSend("approvalizin-queue",jsonString);
            }catch (Exception e){
                return new ResponseEntity(new ResponseModify(0,e.getMessage()),HttpStatus.OK);
            }

            return new ResponseEntity(new ResponseModify(1, "Pengajuan Absen Berhasil Diupdate"), HttpStatus.OK);
        }

        //cek atasan ada lebih dari 1 tingkat apa tidak
        LevelApproval lvlApproval = repositoryLevelApproval.findExistApproval(div, jobLevel,incr, comp);

        if(lvlApproval != null){
            System.out.println("ini jika atasan masih ada lagi");
            int idAtasan = 0;
            if(lvlApproval.getDisposisi() == 0){
                idAtasan = lvlApproval.getApprovalId();
            }else{
                idAtasan = lvlApproval.getDisposisi();
            }

            Pegawai cekStatus = repositoryPegawai.findById(idAtasan).orElseThrow();
            if(cekStatus.getStatus() == 0){
                return new ResponseEntity(new ResponseModify(0, "Atasan Sudah Tidak Aktif"), HttpStatus.OK);
            }

            //update absen
            absensi.setTingkatApprovalIn(incr);
            absensi.setApprovalIdIn(idAtasan);
            this.repositoryAbsen.save(absensi);

            //kirim pesan
            Pesan msg = new Pesan();
            msg.setIdPesan(absensi.getId());
            msg.setStatus(0);
            msg.setDari(approvLama);
            msg.setKepada(idAtasan);
            msg.setFlag(0);
            msg.setTanggal(new Date());
            msg.setTingkat(incr);
            msg.setTipe(1);
            try {
                ObjectMapper mapper = new ObjectMapper();
                String jsonString = "";
                jsonString = mapper.writeValueAsString(msg);
                jmsTemplate.convertAndSend("approvalizin-queue",jsonString);
            }catch (Exception e){
                return new ResponseEntity(new ResponseModify(0,e.getMessage()),HttpStatus.OK);
            }

            return new ResponseEntity(new ResponseModify(0, "Berhasil Di update"), HttpStatus.OK);
        }

        //jika tingkat approval sudah sampai akhir
        absensi.setStatusIn(Integer.parseInt(body.get("statusIn")));
        this.repositoryAbsen.save(absensi);

        //kirim pesan
        Pesan msg = new Pesan();
        msg.setIdPesan(absensi.getId());
        msg.setStatus(1);
        msg.setDari(approvLama);
        msg.setKepada(idEmp);
        msg.setFlag(0);
        msg.setTanggal(new Date());
        msg.setTingkat(tingkat);
        msg.setTipe(1);
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = "";
            jsonString = mapper.writeValueAsString(msg);
            jmsTemplate.convertAndSend("approvalizin-queue",jsonString);
        }catch (Exception e){
            return new ResponseEntity(new ResponseModify(0,e.getMessage()),HttpStatus.OK);
        }

        return new ResponseEntity(new ResponseModify(1, "Pengajuan Absen Berhasil Diupdate"), HttpStatus.OK);

    }


    @Transactional
    public ResponseEntity<?> updateAbsenCheckoutByTingkat(Map<String, String> body, int idAbsen){

        Absensi absensi = repositoryAbsen.findByIdStatusOut(idAbsen,0);

        //cek absen apakah sudah di update atau belum
        if(absensi == null){
            return new ResponseEntity(new ResponseModify(0, "absensi sudah di update sebelumnya"), HttpStatus.OK);
        }

        //cek apakah sudah melakukan checkout atau belum
        if(absensi.getCaptionOut().isEmpty() || absensi.getCaptionOut() == null){
            return new ResponseEntity(new ResponseModify(0, "pegawai belum melakukan checkout"), HttpStatus.OK);
        }

        //get data pegawai
        Optional<Pegawai> pegawai = repositoryPegawai.findById(absensi.getIdPegawai());

        Pegawai x = pegawai.get();


        if(pegawai == null){
            return new ResponseEntity(new ResponseModify(0, "Data Pegawai Tidak Ditemukan"), HttpStatus.OK);
        }

        int div = pegawai.get().getDivisi();
        int jobLevel = pegawai.get().getJobLevel();
        int comp = pegawai.get().getCompany();
        int approvLama = absensi.getApprovalIdOut();

        int incr = absensi.getTingkatApprovalOut() + 1;
        int idEmp = absensi.getIdPegawai();
        int tingkat = absensi.getTingkatApprovalOut();

        //jika status absen tidak disetujui
        if(Integer.parseInt(body.get("statusOut")) == 2){
            System.out.println("ini jika tidak disetujui");

            //update absen
            absensi.setStatusOut(2);
            this.repositoryAbsen.save(absensi);

            //kirim pesan
            Pesan msg = new Pesan();
            msg.setIdPesan(absensi.getId());
            msg.setStatus(2);
            msg.setDari(approvLama);
            msg.setKepada(idEmp);
            msg.setFlag(0);
            msg.setTanggal(new Date());
            msg.setTingkat(tingkat);
            msg.setTipe(2);
            try {
                ObjectMapper mapper = new ObjectMapper();
                String jsonString = "";
                jsonString = mapper.writeValueAsString(msg);
                jmsTemplate.convertAndSend("approvalizin-queue",jsonString);
            }catch (Exception e){
                return new ResponseEntity(new ResponseModify(0,e.getMessage()),HttpStatus.OK);
            }

            return new ResponseEntity(new ResponseModify(1, "Pengajuan Absen Berhasil Diupdate"), HttpStatus.OK);
        }

        //cek atasan ada lebih dari 1 tingkat apa tidak
        LevelApproval lvlApproval = repositoryLevelApproval.findExistApproval(div, jobLevel,incr, comp);

        if(lvlApproval != null){
            System.out.println("ini jika atasan masih ada lagi");
            int idAtasan = 0;
            if(lvlApproval.getDisposisi() == 0){
                idAtasan = lvlApproval.getApprovalId();
            }else{
                idAtasan = lvlApproval.getDisposisi();
            }

            Pegawai cekStatus = repositoryPegawai.findById(idAtasan).orElseThrow();
            if(cekStatus.getStatus() == 0){
                return new ResponseEntity(new ResponseModify(0, "Atasan Sudah Tidak Aktif"), HttpStatus.OK);
            }

            //update absen
            absensi.setTingkatApprovalOut(incr);
            absensi.setApprovalIdOut(idAtasan);
            this.repositoryAbsen.save(absensi);

            //kirim pesan
            Pesan msg = new Pesan();
            msg.setIdPesan(absensi.getId());
            msg.setStatus(0);
            msg.setDari(approvLama);
            msg.setKepada(idAtasan);
            msg.setFlag(0);
            msg.setTanggal(new Date());
            msg.setTingkat(incr);
            msg.setTipe(2);
            try {
                ObjectMapper mapper = new ObjectMapper();
                String jsonString = "";
                jsonString = mapper.writeValueAsString(msg);
                jmsTemplate.convertAndSend("approvalizin-queue",jsonString);
            }catch (Exception e){
                return new ResponseEntity(new ResponseModify(0,e.getMessage()),HttpStatus.OK);
            }

            return new ResponseEntity(new ResponseModify(0, "Berhasil Di update"), HttpStatus.OK);
        }

        //jika tingkat approval sudah sampai akhir
        absensi.setStatusOut(Integer.parseInt(body.get("statusOut")));
        this.repositoryAbsen.save(absensi);

        //kirim pesan
        Pesan msg = new Pesan();
        msg.setIdPesan(absensi.getId());
        msg.setStatus(1);
        msg.setDari(approvLama);
        msg.setKepada(idEmp);
        msg.setFlag(0);
        msg.setTanggal(new Date());
        msg.setTingkat(tingkat);
        msg.setTipe(2);
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = "";
            jsonString = mapper.writeValueAsString(msg);
            jmsTemplate.convertAndSend("approvalizin-queue",jsonString);
        }catch (Exception e){
            return new ResponseEntity(new ResponseModify(0,e.getMessage()),HttpStatus.OK);
        }

        return new ResponseEntity(new ResponseModify(1, "Pengajuan Absen Berhasil Diupdate"), HttpStatus.OK);

    }

    public ResponseEntity<?> getCheckinTodayByCompany(int company) {

        Date today = new Date();
        Date todays = convertDate.getTglByDate(today);

        System.out.println(todays);
        List<AbsensiJoin> listAbsenByCompany = repositoryAbsen.absenTodayByCompany(company, todays);
        return new ResponseEntity(new ResponseList(1, "get Data Sukses", listAbsenByCompany), HttpStatus.OK);

    }

    public ResponseEntity<?> getTanggalByCompany(int company, Map<String, String> body){

        //jika keduanya di isi
        if(body.get("tanggalStart") != null && body.get("tanggalEnd") != null){
            System.out.println("ini jika kedua nya terisi");

            Date end = convertDate.convertStringToDate(body.get("tanggalEnd"));
            Date start = convertDate.convertStringToDate(body.get("tanggalStart"));

            List<AbsensiJoin> absen = repositoryAbsen.getAbsenByRange(company, start, end);
            return new ResponseEntity(new ResponseList(1, "get Data Sukses", absen), HttpStatus.OK);
        }

        //jika salah satu di isi
        Date start = convertDate.convertStringToDate(body.get("tanggalStart"));

        List<AbsensiJoin> ab = repositoryAbsen.absenTodayByCompany(company, start);
        return new ResponseEntity(new ResponseList(1, "get Data Sukses", ab), HttpStatus.OK);
    }

    public ResponseEntity<?> getByCompany(int company) {
        //log.info("hit getBycompany Absen");
        try {
            List<Absensi> listAbsenByCompany = repositoryAbsen.findByCompany(company);
            return new ResponseEntity(new ResponseList(1, "get Data Sukses", listAbsenByCompany), HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


}
