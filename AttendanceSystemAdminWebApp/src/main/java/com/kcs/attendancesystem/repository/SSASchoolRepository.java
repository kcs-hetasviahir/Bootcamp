package com.kcs.attendancesystem.repository;

import com.kcs.attendancesystem.core.SSABlock;
import com.kcs.attendancesystem.core.SSASchool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SSASchoolRepository extends JpaRepository<SSASchool, Long> {

    @Query("SELECT ba FROM SSASchool ba WHERE ba.blockId = :blockId")
    List<SSASchool> findByBlockId(@Param("blockId") Long blockId);

    @Query("SELECT ba FROM SSASchool ba WHERE ba.schoolCode = :schoolCode")
    SSASchool findBySchoolCode(@Param("schoolCode") Long schoolCode);

    @Query("SELECT ba FROM SSASchool ba WHERE ba.districtCode = :distId AND ba.blockId =:blockId AND ba.crcCode =:crcId")
    List<SSASchool> findByDistIdBlockIdClusterId(@Param("distId") Long distId,
                                                 @Param("blockId") Long blockId,
                                                 @Param("crcId") Long crcId);

}
