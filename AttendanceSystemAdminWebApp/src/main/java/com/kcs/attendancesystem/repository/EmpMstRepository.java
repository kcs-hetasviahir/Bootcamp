package com.kcs.attendancesystem.repository;

import com.kcs.attendancesystem.core.EmpMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface EmpMstRepository extends JpaRepository<EmpMaster, Long> {

    @Query("SELECT em FROM EmpMaster em WHERE em.empId = :empId")
    EmpMaster findByEmpId(@Param("empId") String empId);
}
