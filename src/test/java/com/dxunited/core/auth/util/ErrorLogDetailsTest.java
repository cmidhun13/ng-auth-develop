//package com.szells.gce.auth.util;
//
//import com.szells.gce.core.utilities.constants.LogConstants;
//import org.junit.jupiter.api.Test;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class ErrorLogDetailsTest {
//
//    @Test
//    public void getExceptionDetailsTest(){
//        Map<String,Object> map = new HashMap<>();
//        String correlationId = "corrID";
//        map.put(LogConstants.TIME_TAKEN,null);
//        map.put(LogConstants.CORRELATION_ID, correlationId);
//        map.put(LogConstants.APPLICATION,"ng-auth-service");
//        map.put(LogConstants.CREATED_BY,"ng-auth-service");
//        ErrorLogDetails errorLogDetails = new ErrorLogDetails();
//        assertEquals(map, errorLogDetails.getExceptionDetails(correlationId));
//    }
//}
