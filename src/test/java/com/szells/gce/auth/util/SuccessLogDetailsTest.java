//package com.szells.gce.auth.util;
//
//import com.szells.gce.core.utilities.constants.DataFormat;
//import com.szells.gce.core.utilities.constants.LogConstants;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.MockitoAnnotations;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class SuccessLogDetailsTest {
//
//    @InjectMocks
//    private SuccessLogDetails successLogDetails;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    public void getLogDetails() {
//
//        List<String> mask = new ArrayList<>();
//        mask.add("abc");
//
//        Object request = new Object();
//        Object response = new Object();
//
//        Map<String, Object> expectedMap = new HashMap<>();
//        expectedMap.put(LogConstants.REQUEST, request);
//        expectedMap.put(LogConstants.RESPONSE, response);
//        expectedMap.put(LogConstants.DATA_FORMAT, DataFormat.JSON);
//        expectedMap.put(LogConstants.IS_MASKABLE, true);
//        expectedMap.put(LogConstants.PCI_PII_FIELDS, mask);
//
//        assertEquals(expectedMap.get("isMaskable"), successLogDetails.getLogDetails(mask, new Object(), new Object()).get("isMaskable"));
//    }
//}
