package com.kcs.attendancesystem.repository;

import com.kcs.attendancesystem.core.Taluka;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TalukaRepository extends JpaRepository<Taluka, Long> {

    @Query("SELECT ta FROM Taluka ta WHERE ta.distId = :distId")
    List<Taluka> findByDistrictId(@Param("distId") Long distId);

}
