package com.szells.gce.auth.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    ACCESS_TOKEN_ACQUIRE_FAILED("authsvc.token.acquire-failed"),
    ACCESS_TOKEN_MISSING_CLAIM("authsvc.token.missing-claim"),
    HEADERS_MISSING("requestHeaders.header-missing"),
    HEADERS_INVALID("requestHeaders.header-invalid"),
    TENANT_REALM_MAPPING_NOT_FOUND("authsvc.tenant-realm-mapping.not-found"),
    USER_AUTHENTICATION_FAILED("authsvc.user.auth-failed"),
    USER_NOT_FOUND("authsvc.user.not-found"),
    MEMBER_ID_NOT_FOUND("authsvc.memberId.not-found"),
    ACCOUNT_LOCKED("authsvc.user.account-locked");
    private String code;
}
