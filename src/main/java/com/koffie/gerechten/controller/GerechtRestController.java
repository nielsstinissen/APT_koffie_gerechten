package com.koffie.gerechten.controller;

import com.koffie.gerechten.model.Gerecht;
import com.koffie.gerechten.repository.GerechtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
public class GerechtRestController{
    @Autowired
    private GerechtRepository gerechtRepository;

    @PostConstruct
    public void fillDB(){
        if(gerechtRepository.count()==0){
            gerechtRepository.save(new Gerecht("Vegan ijskoffie met havermelk", "", 310, true, true, true, 1, "ah.be/allerhande/recept/R-R1193909/vegan-ijskoffie-met-havermelk"));
            gerechtRepository.save(new Gerecht("tiramisu", "", 310, true, true, true, 1, "ah.be/allerhande/recept/R-R1193909/vegan-ijskoffie-met-havermelk"));
            gerechtRepository.save(new Gerecht("mokka ijs", "", 310, true, true, true, 1, "ah.be/allerhande/recept/R-R1193909/vegan-ijskoffie-met-havermelk"));
        }
    }

    @GetMapping("/gerechten/naam/{naam}")
    public Gerecht getAllByNaam(@PathVariable String naam){
        return gerechtRepository.findAllByNaamContaining(naam);
    }

    @GetMapping("/gerechten/vegan")
    public List<Gerecht> getAllByVeganIsTrue(){
        return gerechtRepository.findAllByVeganIsTrue();
    }

    @GetMapping("/gerechten/vegetarisch")
    public List<Gerecht> getAllByVegetarischIsTrue(){
        return gerechtRepository.findAllByVegetarischIsTrue();
    }

    @GetMapping("/gerechten/glutenvrij")
    public List<Gerecht> getAllByGlutenvrijIsTrue(){
        return gerechtRepository.findAllByGlutenvrijIsTrue();
    }

    @GetMapping("/gerechten/kcal/{kcal}")
    public List<Gerecht> findAllByKcalIsLessThanEqual(@PathVariable double kcal){
        return gerechtRepository.findAllByKcalIsLessThanEqual(kcal);
    }

    @GetMapping("/gerechten/personen/{aantalPersonen}")
    public List<Gerecht> findAllByAantalPersonenEquals(@PathVariable int aantalPersonen){
        return gerechtRepository.findAllByAantalPersonenEquals(aantalPersonen);
    }

    @GetMapping("/gerechten/afkomst/{afkomst}")
    public List<Gerecht> findAllByAfkomstContaining(@PathVariable String afkomst){
        return gerechtRepository.findAllByAfkomstContaining(afkomst);
    }

    @PostMapping("/gerechten/")
    public Gerecht createGerecht(@RequestBody Gerecht gerecht){
        gerechtRepository.save(gerecht);
        return gerecht;
    }

    @PutMapping("/gerechten/naam/{naam}")
    public Gerecht updateGerecht(@PathVariable String naam,@RequestBody Gerecht updatedGerecht){

        Gerecht retrievedGerecht = gerechtRepository.findAllByNaamContaining(naam);
        retrievedGerecht.setNaam(updatedGerecht.getNaam());
        retrievedGerecht.setAantalPersonen(updatedGerecht.getAantalPersonen());
        retrievedGerecht.setAfkomst(updatedGerecht.getAfkomst());
        retrievedGerecht.setKcal(updatedGerecht.getKcal());
        retrievedGerecht.setGlutenvrij(updatedGerecht.isGlutenvrij());
        retrievedGerecht.setUrl(updatedGerecht.getUrl());
        retrievedGerecht.setVegan(updatedGerecht.isVegan());
        retrievedGerecht.setVegetarisch(updatedGerecht.isVegetarisch());
        gerechtRepository.save(retrievedGerecht);
        return retrievedGerecht;
    }

    @DeleteMapping("/gerechten/naam/{naam}")
    public ResponseEntity deleteGerecht(@PathVariable String naam){
        Gerecht gerecht = gerechtRepository.findAllByNaamContaining(naam);
        if(gerecht != null){
            gerechtRepository.delete(gerecht);
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
