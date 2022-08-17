package com.kcs.attendancesystem.repository;

import com.kcs.attendancesystem.core.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {

	

}
