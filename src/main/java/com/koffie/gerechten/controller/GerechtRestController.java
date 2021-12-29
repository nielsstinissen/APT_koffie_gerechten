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
            gerechtRepository.save(new Gerecht(1, "Vegan ijskoffie met havermelk", "Nederland", 310, true, true, true, 1, "ah.be/allerhande/recept/R-R1193909/vegan-ijskoffie-met-havermelk"));
            gerechtRepository.save(new Gerecht(4, "tiramisu", "Belgie", 310, true, true, true, 1, "ah.be/allerhande/recept/R-R1193909/vegan-ijskoffie-met-havermelk12"));
            gerechtRepository.save(new Gerecht(5, "mokka ijs", "Duitsland", 310, true, true, true, 1, "ah.be/allerhande/recept/R-R1193909/vegan-ijskoffie-met-havermelk123"));
        }
    }

    @GetMapping("/gerechten")
    public List<Gerecht> getAll(){return gerechtRepository.findAll();}

    @GetMapping("/gerechten/naam/{naam}")
    public Gerecht getAllByNaam(@PathVariable String naam){
        return gerechtRepository.findAllByNaamContaining(naam);
    }

    @GetMapping("/gerechten/vegan")
    public List<Gerecht> getAllByIsVeganIsTrue(){
        return gerechtRepository.findAllByIsVeganIsTrue();
    }

    @GetMapping("/gerechten/vegetarisch")
    public List<Gerecht> getAllByIsVegetarischIsTrue(){
        return gerechtRepository.findAllByIsVegetarischIsTrue();
    }

    @GetMapping("/gerechten/glutenvrij")
    public List<Gerecht> getAllByIsGlutenvrijIsTrue(){
        return gerechtRepository.findAllByIsGlutenvrijIsTrue();
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

    @GetMapping("/gerechten/koffiedrank/{koffieDrankId}")
    public List<Gerecht> findAllByKoffieDrankId(@PathVariable int koffieDrankId){
        return gerechtRepository.findAllByKoffieDrankId(koffieDrankId);
    }

    @PostMapping("/gerechten/")
    public Gerecht createGerecht(@RequestBody Gerecht gerecht){
        gerechtRepository.save(gerecht);
        return gerecht;
    }

    @PutMapping("/gerechten/url/{url}")
    public Gerecht updateGerecht(@PathVariable String url,@RequestBody Gerecht updatedGerecht){

        Gerecht retrievedGerecht = gerechtRepository.findByUrlIs(url);
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

    @DeleteMapping("/gerechten/url/{url}")
    public ResponseEntity deleteGerecht(@PathVariable String url){
        Gerecht gerecht = gerechtRepository.findByUrlIs(url);
        if(gerecht != null){
            gerechtRepository.delete(gerecht);
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
