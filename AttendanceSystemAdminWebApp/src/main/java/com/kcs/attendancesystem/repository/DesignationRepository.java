package com.kcs.attendancesystem.repository;

import com.kcs.attendancesystem.core.Designation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DesignationRepository extends JpaRepository<Designation, Long> {

    @Query("SELECT de FROM Designation de WHERE de.id = :designationId")
    Designation findByDesignationId(@Param("designationId") long designationId);
}
