package com.kcs.attendancesystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kcs.attendancesystem.core.ServiceStatus;

@Repository
public interface ServiceStatusRepository extends JpaRepository<ServiceStatus, Long> {

	Optional<ServiceStatus> findTopByServiceNameOrderByIdDesc(String serviceName);
	
	@Query("SELECT ss FROM ServiceStatus ss WHERE ss.serviceName IN (:serviceNames) AND status = :status")
	List<ServiceStatus> findByServiceNamesAndStatus(@Param("serviceNames") List<String> serviceNames, @Param("status") String status);
}
