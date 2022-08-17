package com.kcs.attendancesystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kcs.attendancesystem.core.Institution;

@Repository
public interface InstitutionRepository extends JpaRepository<Institution, Long> {

}
