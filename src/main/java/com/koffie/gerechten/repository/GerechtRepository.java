package com.koffie.gerechten.repository;

import com.koffie.gerechten.model.Gerecht;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GerechtRepository extends MongoRepository<Gerecht, String> {
    List<Gerecht> findAll();
    Gerecht findAllByNaamContaining(String naam);
    List<Gerecht> findAllByIsVeganIsTrue();
    List<Gerecht> findAllByKcalIsLessThanEqual(double kcal);
    List<Gerecht> findAllByIsVegetarischIsTrue();
    List<Gerecht> findAllByAantalPersonenEquals(int aantalPersonen);
    List<Gerecht> findAllByAfkomstContaining(String afkomst);
    List<Gerecht> findAllByIsGlutenvrijIsTrue();
    List<Gerecht> findAllByKoffieDrankNaamContaining(String koffieDrankNaam);
    Gerecht findByNaamIs(String naam);
}
