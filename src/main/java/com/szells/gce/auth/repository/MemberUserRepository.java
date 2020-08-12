package com.szells.gce.auth.repository;

import com.szells.gce.auth.domain.MemberUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberUserRepository extends JpaRepository<MemberUser, String> {

    @Query("select user_id from member_user where member_id =:memberId")
    Optional<String> findByMemberId(@Param("memberId") Integer memberId);

    @Query("select member_id from member_user where user_id =:userId")
    String findByKeycloakUserId(@Param("userId") String userId);

    @Query("select user_id from member_user where user_id =:userId")
    Optional<String> checkMemberIDExists(String userId);

}
