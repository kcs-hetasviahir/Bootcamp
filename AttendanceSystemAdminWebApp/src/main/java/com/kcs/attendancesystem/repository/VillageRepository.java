package com.kcs.attendancesystem.repository;

import com.kcs.attendancesystem.core.Taluka;
import com.kcs.attendancesystem.core.Village;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VillageRepository extends JpaRepository<Village, Long> {

    @Query("SELECT ta FROM Village ta WHERE ta.talukaId = :talukaId")
    List<Village> findByTalukaId(@Param("talukaId") Long talukaId);

}
