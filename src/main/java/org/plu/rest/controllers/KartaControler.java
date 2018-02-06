package org.plu.rest.controllers;


import org.plu.dao.KartaRepository;
import org.plu.entities.Karta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/karta")
public class KartaControler {

    @Autowired
    private KartaRepository kartaRepository;

    @GetMapping("/new/{userid}/{korisnik}/{card}/{jmbg}/{idleta}/{sediste}/{status}/{vremerezervacije}/{veremeleta}")
    private String addNew(@PathVariable(value = "userid") int userid,@PathVariable(value = "korisnik") String korisnik,
                          @PathVariable(value = "card")String card,@PathVariable(value = "jmbg") String jmbg,
                          @PathVariable(value = "idleta") int idleta,@PathVariable(value = "sediste") int sediste,
                          @PathVariable(value = "status") String status,@PathVariable(value = "vremerezervacije") long vremerezervacije,
                          @PathVariable(value = "veremeleta") long veremeleta){
        if(status.equalsIgnoreCase("rezervisano")){
            status = "REZERVISANO";
        }else if(status.equalsIgnoreCase("KUPLJENO")){
            status = "KUPLJENO";
        }else if(status.equalsIgnoreCase("ODUSTAO")){
            status  = "ODUSTAO";
        }

        List<Karta> karte = kartaRepository.findAll();

        for(Karta k :karte){

            if(k.getIdleta() == idleta && k.getSediste() == sediste && k.getUserid()==userid) {

                if (k.getStatus().equalsIgnoreCase("REZERVISANO") || k.getStatus().equalsIgnoreCase("KUPLJENO")) {

                        return "ZAUZETO!!!";

                }
            }
        }

        Karta karta = new Karta(userid,korisnik,card,jmbg,idleta,sediste,status,vremerezervacije,veremeleta);

        if(karta!=null){
            kartaRepository.save(karta);
            return "Success!!!";
        }

        return "There was a mistake.Try again later";

    }

    @GetMapping("/gett/{userid}")
    private List<Karta> getAllToBeCaseled(@PathVariable(value = "userid")int userid){
        List<Karta> all = kartaRepository.findAll();
        List<Karta> karte = new ArrayList<>();
        for(Karta k : all){
            if(k.getUserid() == userid){
                if(!k.getStatus().equalsIgnoreCase("kupljeno") && !k.getStatus().equalsIgnoreCase("odustao")){
                    karte.add(k);
                }
            }
        }
        return karte;
    }
    @GetMapping("/all")
    public List<Karta> getAll(){
        return kartaRepository.findAll();
    }

    @GetMapping("/changestatus/{id}/{status}")
    public String changeStatus(@PathVariable(value = "id") int id,@PathVariable(value = "status") String status){
        Karta k = kartaRepository.findOne(id);

        if(k == null){
            return "NEMA";
        }

        if(status.equalsIgnoreCase("kupljeno")){
            k.setStatus("KUPLJENO");
        }else if(status.equalsIgnoreCase("ODUSTAO")){
            k.setStatus("ODUSTAO");
        }
        kartaRepository.save(k);
        return "SUCCESS";
    }
}
