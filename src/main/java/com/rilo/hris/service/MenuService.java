package com.rilo.hris.service;

import com.rilo.hris.dto.MenuJoin;
import com.rilo.hris.entity.Menu;
import com.rilo.hris.model.ResponseList;
import com.rilo.hris.model.ResponseModify;
import com.rilo.hris.repository.MenuRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor //jadi tidak usah menggunakan @autowired
public class MenuService {
    private MenuRepository menuRepository;

    public ResponseEntity<?> getByRelation(int id){
        List<Menu> menu = null;
        if(id == 0){
            menu = menuRepository.byRelation(id);
        }else{
            menu = menuRepository.byIds(id);
        }
        List<MenuJoin> act = this.getChild(menu);
        return new ResponseEntity(new ResponseList(1, "get Data Sukses", act), HttpStatus.OK);

    }

    public List<MenuJoin> getChild(List<Menu> data){
        List<MenuJoin> resp = new ArrayList<MenuJoin>();

        for(Menu d : data){
            MenuJoin menuJoin = new MenuJoin();
            menuJoin.setIdMenu(d.getIdMenu());
            menuJoin.setNameMenu(d.getNameMenu());
            menuJoin.setControllerMenu(d.getControllerMenu());
            menuJoin.setParentMenu(d.getParentMenu());
            menuJoin.setLevelMenu(d.getLevelMenu());
            menuJoin.setStatus(d.getStatus());
            menuJoin.setAccessRole(d.getAccessRole());
            menuJoin.setIcon(d.getIcon());
            menuJoin.setChild(this.getChild(menuRepository.byRelation(d.getIdMenu())));
            resp.add(menuJoin);
        }
        return resp;
    }

    public ResponseEntity<?> updateMenu(Menu menu){
        Optional<Menu> data = menuRepository.findById(menu.getIdMenu());
        Menu updt = data.get();
        if(menu.getIcon() != null && !menu.getIcon().isEmpty()){
            updt.setIcon(menu.getIcon());
        }
        updt.setNameMenu(menu.getNameMenu());
        updt.setControllerMenu(menu.getControllerMenu());
        updt.setParentMenu(menu.getParentMenu());
        updt.setLevelMenu(menu.getLevelMenu());
        updt.setStatus(menu.getStatus());
        updt.setAccessRole(menu.getAccessRole());

        menuRepository.save(updt);
        return new ResponseEntity(new ResponseModify(1, "Berhasil Update Menu"), HttpStatus.OK);
    }

    public ResponseEntity<?> deleteMenu(int idmenu){
        menuRepository.deleteById(idmenu);
        return new ResponseEntity(new ResponseModify(1, "Data Berhasil Dihapus"), HttpStatus.OK);
    }
}
