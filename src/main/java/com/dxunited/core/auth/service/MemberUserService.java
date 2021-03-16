package com.dxunited.core.auth.service;

import com.dxunited.core.auth.repository.MemberUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberUserService {

    private final MemberUserRepository memberUserRepository;

    public Optional<String> getUserId(Long memberId) {
        return memberUserRepository.findByMemberId(Math.toIntExact(memberId));
    }

    public String getKeycloakUserId(String userId) {
        System.out.println("inside id search");

        return memberUserRepository.findByKeycloakUserId(userId);
    }

    public Boolean checkUserIdExists(String userId) {
        return memberUserRepository.checkMemberIDExists(userId).map(v -> Boolean.TRUE).orElse(Boolean.FALSE);
    }
}
