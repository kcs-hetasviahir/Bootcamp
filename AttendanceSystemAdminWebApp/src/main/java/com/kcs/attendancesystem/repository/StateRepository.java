package com.kcs.attendancesystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kcs.attendancesystem.core.State;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {

}
