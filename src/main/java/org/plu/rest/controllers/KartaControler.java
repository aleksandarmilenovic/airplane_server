package org.plu.rest.controllers;


import org.plu.dao.KartaRepository;
import org.plu.entities.Karta;
import org.plu.rest.services.MailSender;
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

    @GetMapping("/new/{userid}/{korisnik}/{card}/{jmbg}/{idleta}/{sediste}/{status}/{vremerezervacije}/{veremeleta}/{odrediste}/{razdaljinaDestinacije}")
    private String addNew(@PathVariable(value = "userid") int userid,@PathVariable(value = "korisnik") String korisnik,
                          @PathVariable(value = "card")String card,@PathVariable(value = "jmbg") String jmbg,
                          @PathVariable(value = "idleta") int idleta,@PathVariable(value = "sediste") int sediste,
                          @PathVariable(value = "status") String status,@PathVariable(value = "vremerezervacije") long vremerezervacije,
                          @PathVariable(value = "veremeleta") long veremeleta,
                          @PathVariable(value = "odrediste") int odrediste, @PathVariable(value = "razdaljinaDestinacije") int razdaljinaDestinacije){


        List<Karta> karte = kartaRepository.findAll();

        for(Karta k :karte){

            if(k.getIdleta() == idleta && k.getSediste() == sediste && k.getUserid()==userid) {

                if (k.getStatus().equalsIgnoreCase("REZERVISANO") || k.getStatus().equalsIgnoreCase("KUPLJENO")) {
                    MailSender mailSender = new MailSender();
                    mailSender.infoMail(korisnik,"Zauzet je let "+idleta);
                        return "ZAUZETO!!!";

                }
            }
        }
        if(status.equalsIgnoreCase("rezervisano")){
            MailSender mailSender = new MailSender();
            mailSender.infoMail(korisnik,"Uspesno ste Napravili rezervaciju za let "+idleta);
            status = "REZERVISANO";
        }else if(status.equalsIgnoreCase("KUPLJENO")){
            MailSender mailSender = new MailSender();
            mailSender.infoMail(korisnik,"Uspesno ste kupili kartu za let "+idleta);
            status = "KUPLJENO";
        }else if(status.equalsIgnoreCase("ODUSTAO")){
            MailSender mailSender = new MailSender();
            mailSender.infoMail(korisnik,"Odustali ste od leta "+idleta);
            status  = "ODUSTAO";
        }

        Karta karta = new Karta(userid,korisnik,card,jmbg,idleta,sediste,status,vremerezervacije,veremeleta, odrediste, razdaljinaDestinacije);

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

    @GetMapping("/changestatus/{id}/{status}/{username}")
    public String changeStatus(@PathVariable(value = "id") int id,@PathVariable(value = "status") String status,
                               @PathVariable(value = "username") String username){
        Karta k = kartaRepository.findOne(id);

        if(k == null){
            return "NEMA";
        }

        if(status.equalsIgnoreCase("kupljeno")){
            MailSender mailSender = new MailSender();
            mailSender.infoMail(username,"Uspesno ste kupili kartu za let"+id);
            k.setStatus("KUPLJENO");
        }else if(status.equalsIgnoreCase("ODUSTAO")){
            MailSender mailSender = new MailSender();
            mailSender.infoMail(username,"Uspesno ste Napravili rezervaciju za let"+id);
            k.setStatus("ODUSTAO");
        }
        kartaRepository.save(k);
        return "SUCCESS";
    }

    @GetMapping("/history/{userid}")
    private List<Karta> history(@PathVariable(value = "userid")int userid){
        List<Karta> all = kartaRepository.findAll();
        List<Karta> karte = new ArrayList<>();
        for(Karta k : all){
            if(k.getUserid() == userid){

                    karte.add(k);

            }
        }
        return karte;
    }
}
