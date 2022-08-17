package com.kcs.attendancesystem.repository;

import com.kcs.attendancesystem.core.District;
import com.kcs.attendancesystem.core.SSADistrict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SSADistrictRepository extends JpaRepository<SSADistrict, Long> {

	/*
	 * @Query("SELECT sd FROM SSADistrict sd WHERE sd.districtCode = :districtCode")
	 * SSADistrict findByDistrictCode(@Param("districtCode") Long districtCode);
	 */
    SSADistrict findByDistrictCode(Long districtCode);
}
