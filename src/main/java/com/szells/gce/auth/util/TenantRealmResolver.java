package com.szells.gce.auth.util;


import com.szells.gce.auth.exception.AuthServiceException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

import static com.szells.gce.auth.constant.ErrorCode.TENANT_REALM_MAPPING_NOT_FOUND;

//import com.szells.gce.core.utilities.loggingService.CoreLogger;

@Component
@ConfigurationProperties(prefix = "gce")
@Getter
@Setter
public class TenantRealmResolver {

    private Map<String, String> tenantRealmMapping;

    //    @Autowired
//    private CoreLogger coreLogger;
    @Autowired
    private SuccessLogDetails successLogDetails;

    public String resolveRealm(String tenantId) {
//        coreLogger.log("relam rosolver trace", Level.INFO, successLogDetails.getLogDetails(List.of("userName", "password"), "tenaneId", tenantId));
        return Optional.ofNullable(tenantRealmMapping)
                .map(mapper -> mapper.get(tenantId))
                .orElseThrow(() -> new AuthServiceException(TENANT_REALM_MAPPING_NOT_FOUND.getCode(),""));
    }
}
