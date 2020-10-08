package com.szells.gce.auth.util;

//import com.szells.gce.core.utilities.constants.LogConstants;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
public class ErrorLogDetails {


    public Map<String, Object> getExceptionDetails(String correlationId) {
        Map<String, Object> map = new HashMap<>();
//        map.put(LogConstants.TIME_TAKEN, null);
//        map.put(LogConstants.CORRELATION_ID, correlationId);
//        map.put(LogConstants.APPLICATION, "ng-auth-service");
//        map.put(LogConstants.CREATED_BY, "ng-auth-service");
        return map;
    }
}
