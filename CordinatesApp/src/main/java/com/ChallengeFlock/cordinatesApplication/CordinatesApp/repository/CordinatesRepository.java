package com.ChallengeFlock.cordinatesApplication.CordinatesApp.repository;

import com.ChallengeFlock.cordinatesApplication.CordinatesApp.model.ProvinceCordinates;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CordinatesRepository extends JpaRepository<ProvinceCordinates,String> {

    //List<ProvinceCordinates> findByName(String name);
    ProvinceCordinates findByName(String name);
    boolean findByNameAndId(Optional<String> name, Long id);
    List<ProvinceCordinates> findAll();
    ProvinceCordinates save(ProvinceCordinates prov);

}
