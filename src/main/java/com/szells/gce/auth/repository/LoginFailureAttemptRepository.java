package com.szells.gce.auth.repository;

import com.szells.gce.auth.domain.LoginFailureAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginFailureAttemptRepository extends JpaRepository<LoginFailureAttempt, Long> {

    @Query("SELECT l.failCount FROM LoginFailureAttempt l WHERE l.tenantId =:tenantId AND l.memberId =:memberId")
    Integer getFailureCount(@Param("tenantId") String tenantId, @Param("memberId") String memberId);

    Optional<LoginFailureAttempt> findByTenantIdAndMemberId(String tenantId, String memberId);


}
