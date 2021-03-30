package com.dxunited.core.auth.service;

import com.dxunited.core.auth.domain.LoginFailureAttempt;
import com.dxunited.core.auth.repository.LoginFailureAttemptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginFailureAttemptService {

    private final LoginFailureAttemptRepository loginFailureAttemptRepository;

    public void deleteLoginAttempt(LoginFailureAttempt loginFailureAttempt) {
        loginFailureAttemptRepository.delete(loginFailureAttempt);
    }

    public Optional<LoginFailureAttempt> getLoginAttempt(String tenantId, String memberId) {
        return loginFailureAttemptRepository.findByTenantIdAndMemberId(tenantId, memberId);
    }

    public void saveLoginAttempt(LoginFailureAttempt loginFailureAttempt) {
        loginFailureAttemptRepository.save(loginFailureAttempt);
    }

    public Integer getFailedAttempts(String tenantId, String memberId) {
        return loginFailureAttemptRepository.getFailureCount(tenantId, memberId);
    }
}
