package com.kcs.attendancesystem.repository;

import com.kcs.attendancesystem.core.SSABlock;
import com.kcs.attendancesystem.core.SSADistrict;
import com.kcs.attendancesystem.core.Taluka;
import com.kcs.attendancesystem.core.TeacherAttendance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SSABlockRepository extends JpaRepository<SSABlock, Long> {

    @Query("SELECT ba FROM SSABlock ba WHERE ba.distId = :distId")
    List<SSABlock> findByDistrictId(@Param("distId") Long distId);

    @Query("SELECT ba FROM SSABlock ba WHERE ba.blockCode = :blockCode")
    SSABlock findByBlockCode(@Param("blockCode") Long blockCode);
    
    

}
