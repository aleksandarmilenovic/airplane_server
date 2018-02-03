package org.plu.rest.controllers;


import org.plu.dao.LetRepository;
import org.plu.dao.AerodromRepository;
import org.plu.dao.AvionRepository;
import org.plu.entities.Aerodrom;
import org.plu.entities.Avion;
import org.plu.entities.Let;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/let")
public class LetControler {

    @Autowired
    private AvionRepository avionRepository;

    @Autowired
    private AerodromRepository aerodromRepository;

    @Autowired
    private LetRepository letRepository;

    @GetMapping("/all")
    public List<Let> getAllLetove(){
        return letRepository.findAll();
    }

    @GetMapping("/delete/{idLeta}")
    public String delete(@PathVariable(value = "idLeta") int idLeta){
        letRepository.delete(idLeta);
        return "Success!!!";
    }

    // http://localhost:8080/let/new/1/1/3/8/2017_12_23_14_23/900
    @GetMapping("/new/{brojLeta}/{idAviona}/{polaziste}/{odrediste}/{vremePolaska}/{razdaljinaDestinacije}/{cena}")
    public String addNew(@PathVariable(value = "brojLeta") int brojLeta,@PathVariable(value = "idAviona") int idAviona,
                         @PathVariable(value = "polaziste") int polaziste,@PathVariable(value = "odrediste") int odrediste,
                         @PathVariable(value = "vremePolaska") String vremePolaska,@PathVariable(value = "razdaljinaDestinacije") int razdaljinaDestinacije,
                         @PathVariable(value = "cena") int cena){


        Let let;

        // automatski sracuna za let >>>>>

        // vreme polaska
        long vremePolaskaSekunde = vremePolaskaSekunde(vremePolaska);

        // brzina aviona
        int brzinaAviona = brzinaAviona(idAviona); // TODO

        // vreme putovanja
        int vremePutovanjaSekunde = razdaljinaDestinacije / (brzinaAviona / 60) * 60; // izrazeno u sekundama

        // vreme dolaska
        long vremeDolaskaSekunde = vremePolaskaSekunde + vremenskaZona(polaziste) * 3600 * -1 + vremePutovanjaSekunde + vremenskaZona(odrediste) * 3600; // izrazeno u sekundama TODO

        // gate
        int gate = gate(polaziste, vremePolaskaSekunde); // TODO
        if(gate == -1) return "Nema slobodnog gate na aerodromu, haha :(";

        // provera redosleda letenja -> isti avion ne moze sleteti u vremensku zonu -4, pa odma zatim poleteti iz vremenske zone 2
        if(!proveraRedosledaLetenja(idAviona,vremePolaskaSekunde,vremenskaZona(polaziste), vremePutovanjaSekunde)) return "Pogresio si. Redosled letenja ti nije dobar tupane. Ne moze avion da poleti u toj vremenskoj zoni nakon. :(";

//        if(true) return "" + vremePolaskaSekunde;
        // <<<<


        let = new Let(brojLeta, idAviona, polaziste, odrediste, vremePolaskaSekunde, vremeDolaskaSekunde, vremePutovanjaSekunde, razdaljinaDestinacije,gate,cena);

        if (let != null)
        {
            letRepository.save(let);
            return "Success!!!";
        }

        return "There was a mistake.Try again later";
    }





    // >>>>>>>>>>>>>>> POMOCNE FUNKCIJE >>>>>>>>>>>>>>>>>>>>
    private long vremePolaskaSekunde(String vremePolaska)
    {
        String polazakGodina = vremePolaska.split("_")[0];
        String polazakMesec = vremePolaska.split("_")[1];
        String polazakDan = vremePolaska.split("_")[2];
        String polazakSat = vremePolaska.split("_")[3];
        String polazakMin = vremePolaska.split("_")[4];

        return Timestamp.valueOf(polazakGodina + "-" + polazakMesec + "-" + polazakDan + " " + polazakSat + ":" + polazakMin + ":00").getTime() / 1000; // vreme polaska u sekundama od 1900,
    }

    private int brzinaAviona(int idAviona)
    {
        List<Avion> avioni = avionRepository.findAll();

        for (Avion a: avioni)
        {
            if (a.getId() == idAviona) return a.getBrzinaLetenja();
        }

        return -1;
    }

    private int vremenskaZona(int idAerodroma)
    {
        List<Aerodrom> aerodromi = aerodromRepository.findAll();
        for(Aerodrom a : aerodromi)
        {
            if(a.getId() == idAerodroma) return a.getVremenskaZona();
        }

        return -1;
    }

    private int gate(int idAerodroma, long vremePolaskaSekunde)
    {
        List<Aerodrom> aerodromi = aerodromRepository.findAll();
        int maxGate = -1;
        for(Aerodrom a : aerodromi)
        {
            if(a.getId() == idAerodroma)
            {
                maxGate = a.getMaxGate();
                break;
            }
        }

        List<Let> letovi = letRepository.findAll();
        List<Integer> zauzetiGateovi = new ArrayList<Integer>();
        for(Let l : letovi)
        {
            if(l.getPolaziste() == idAerodroma && (l.getVremePolaska() - 10 * 60 <= vremePolaskaSekunde && l.getVremePolaska() + 10 * 60 >= vremePolaskaSekunde)) // zauzeti gateovi su oni koji imaju let 10 minuta pre i posle vremenaPolaskaAviona
            {
                zauzetiGateovi.add(l.getGate());
            }
        }

        if(zauzetiGateovi.size() >= maxGate) return - 1;

        for(int i = 0; i < maxGate; i++)
        {
            if(!zauzetiGateovi.contains(i))
            {
                return i;
            }
        }

        return -1;
    }

    private boolean proveraRedosledaLetenja(int idAviona, long vremePolaskaSekunde, int vremenskaZonaPolazista, int vremePutovanjaSekundeNovi)
    {
        List<Let> letovi = letRepository.findAll();
        long polazakPoVremenskojZoni0Novi = vremePolaskaSekunde + vremenskaZonaPolazista * 3600 * -1; // 3600 je za sekunde, a -1 da bi se nastelovalo na 0 vremensku zonu
        int duzinaVremenskeZone = 500; // na svakih 500km ulazi u drugu vremensku zonu
        int brzinaAviona = brzinaAviona(idAviona);
        for(Let l : letovi)
        {
            int kolikoVremenskihZoniTrebaDaPredje = Math.abs(vremenskaZona(l.getOdrediste()) - vremenskaZonaPolazista);
            long kolikoMuTrebaDaPredje =  kolikoVremenskihZoniTrebaDaPredje * (duzinaVremenskeZone / (brzinaAviona / 60) * 60);// koliko mu treba da predje od poslednjeg dolaska pa do polaska (izrazeno u sekundama)//, stavljeno fiksno 3 sata (jer je u zadatku samo receno - da avion ne moze odma zatim da poleti iz druge vremenske zone), inace bi moglo da se sracuna otprilike pomocu duzinaVremenskeZone, brzinaAviona i razlike vremenskih zona dolaska i polaska

            long polazakPoVremenskojZoni0Stari = l.getVremePolaska() + vremenskaZona(l.getPolaziste()) * 3600 * -1; // 3600 je za sekunde, a -1 da bi se nastelovalo na 0 vremensku zonu
            long duzinaLetaStari = Math.abs(l.getVremeDolaska() - l.getVremePolaska());
            // if polazakStari <= polazakNovi <= polazakStari + duzinaLeta + kolikoMuTrebaDaPredje -> ako je ovaj uslov ispunjen vrati FALSE !!!!!
            if(l.getIdAviona() == idAviona && (polazakPoVremenskojZoni0Stari <= polazakPoVremenskojZoni0Novi && polazakPoVremenskojZoni0Novi <= polazakPoVremenskojZoni0Stari + duzinaLetaStari + kolikoMuTrebaDaPredje))
            {
                return false;
            }

            if(l.getIdAviona() == idAviona && (polazakPoVremenskojZoni0Novi <= polazakPoVremenskojZoni0Stari && polazakPoVremenskojZoni0Novi + vremePutovanjaSekundeNovi >= polazakPoVremenskojZoni0Stari))
            {
                return false;
            }
        }

        return true;
    }
    // <<<<<<<<<<<<<<<< POMOCNE FUNKCIJE <<<<<<<<<<<<<<<<

}

