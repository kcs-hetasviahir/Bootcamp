package com.kcs.attendancesystem.repository;

import com.kcs.attendancesystem.core.SSACluster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SSAClustersRepository extends JpaRepository<SSACluster, Long> {

    @Query("SELECT sc FROM SSACluster sc WHERE sc.districtCode = :districtCode AND sc.blockCode = :blockCode")
    List<SSACluster> findByDistrictIdAndBlockId(@Param("districtCode") Long districtCode, @Param("blockCode") Long blockCode);

    @Query("SELECT sc FROM SSACluster sc WHERE sc.crcCode = :clusterCode")
    SSACluster findByClusterCode(@Param("clusterCode") Long clusterCode);
}
