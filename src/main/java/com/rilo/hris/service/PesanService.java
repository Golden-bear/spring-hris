package com.rilo.hris.service;

import com.rilo.hris.entity.Pesan;
import com.rilo.hris.repository.PesanRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

//@Slf4j
@Service
@AllArgsConstructor //jadi tidak usah menggunakan @autowired
public class PesanService {
    private PesanRepository pesanRepository;

    public void savePesan(Pesan p){
        pesanRepository.save(p);
    }
}
