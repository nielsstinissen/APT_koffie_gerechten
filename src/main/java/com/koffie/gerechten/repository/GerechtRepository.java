package com.koffie.gerechten.repository;

import com.koffie.gerechten.model.Gerecht;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GerechtRepository extends MongoRepository<Gerecht, String> {
    Gerecht findAllByNaamContaining(String naam);
    List<Gerecht> findAllByVeganIsTrue();
    List<Gerecht> findAllByKcalIsLessThanEqual(double kcal);
    List<Gerecht> findAllByVegetarischIsTrue();
    List<Gerecht> findAllByAantalPersonenEquals(int aantalPersonen);
    List<Gerecht> findAllByAfkomstContaining(String afkomst);
    List<Gerecht> findAllByGlutenvrijIsTrue();
}
