package org.plu.rest.controllers;


import org.plu.dao.AvionRepository;
import org.plu.entities.Avion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/avion")
public class AvionControler {

    @Autowired
    private AvionRepository avionRepository;

    @GetMapping("/all")
    public List<Avion> getAllAvione(){
        return avionRepository.findAll();
    }

    @GetMapping("/delete/{idAviona}")
    public String delete(@PathVariable(value = "idAviona") int idAviona){
        avionRepository.delete(idAviona);
        return "Success!!!";
    }

    // http://localhost:8080/avion/new/marka/model/63/9/2_2_3/2000/600
    @GetMapping("/new/{marka}/{model}/{brojSedista}/{brojKolona}/{redovi}/{maksimalniDolet}/{brzinaLetenja}")
    public String addNew(@PathVariable(value = "marka") String marka,@PathVariable(value = "model") String model,
                         @PathVariable(value = "brojSedista") int brojSedista,@PathVariable(value = "brojKolona") int brojKolona,
                         @PathVariable(value = "redovi") String redovi,
                         @PathVariable(value = "maksimalniDolet") int maksimalniDolet,@PathVariable(value = "brzinaLetenja") int brzinaLetenja){

        Avion avion;

        String[] brojSedistaURedu = redovi.split("_");
        int brojSedistaUKoloni = 0;

        for (int i = 0; i < brojSedistaURedu.length; i++)
        {
            brojSedistaUKoloni += Integer.parseInt(brojSedistaURedu[i]);
        }

        if(brojSedista != brojKolona * brojSedistaUKoloni)
        {
            return "Matematicki broj sedista/redova/kolona se ne poklapa, tupane :(";
        }

        avion = new Avion(marka, model, brojSedista, brojKolona, redovi, maksimalniDolet, brzinaLetenja);

        if (avion != null)
        {
            avionRepository.save(avion);
            return "Success!!!";
        }

        return "There was a mistake.Try again later.";
    }


}

