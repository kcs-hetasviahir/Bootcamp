package com.kcs.attendancesystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kcs.attendancesystem.core.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findOneByUsername(String username);
}
