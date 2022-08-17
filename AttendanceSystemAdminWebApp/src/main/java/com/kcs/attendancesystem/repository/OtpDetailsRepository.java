package com.kcs.attendancesystem.repository;

import com.kcs.attendancesystem.core.OtpDetails;
import com.kcs.attendancesystem.core.SSADistrict;
import com.kcs.attendancesystem.core.SupportTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OtpDetailsRepository extends JpaRepository<OtpDetails, Long> {

    @Query("SELECT ot FROM OtpDetails ot WHERE ot.receiverId = :receiverId")
    List<OtpDetails> findByMobileNumber(@Param("receiverId") String receiverId);

}
