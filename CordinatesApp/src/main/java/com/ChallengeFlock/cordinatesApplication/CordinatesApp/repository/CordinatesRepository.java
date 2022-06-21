package com.ChallengeFlock.cordinatesApplication.CordinatesApp.repository;

import com.ChallengeFlock.cordinatesApplication.CordinatesApp.model.ProvinceCordinates;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CordinatesRepository extends JpaRepository<ProvinceCordinates, String> {

    ProvinceCordinates findByName(String name);

    List<ProvinceCordinates> findAll();

    ProvinceCordinates save(ProvinceCordinates prov);

}
