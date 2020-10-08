package com.szells.gce.auth.util;

//import com.szells.gce.core.utilities.constants.DataFormat;
//import com.szells.gce.core.utilities.constants.LogConstants;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SuccessLogDetails {

    public Map<String, Object> getLogDetails(List<String> mask, Object request, Object response) {
        Map<String, Object> map = new HashMap<>();
//        map.put(LogConstants.REQUEST, request);
//        map.put(LogConstants.RESPONSE, response);
//        map.put(LogConstants.APPLICATION, AuthConstants.APPLICATION_NAME);
//        map.put(LogConstants.DATA_FORMAT, DataFormat.JSON);
//        map.put(LogConstants.IS_MASKABLE, true);
//        map.put(LogConstants.PCI_PII_FIELDS, mask);
        return map;
    }
}
