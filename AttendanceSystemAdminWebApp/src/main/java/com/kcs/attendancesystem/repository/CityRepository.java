package com.kcs.attendancesystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kcs.attendancesystem.core.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

}
