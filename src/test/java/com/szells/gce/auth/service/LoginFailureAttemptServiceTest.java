//package com.szells.gce.auth.service;
//
//import LoginFailureAttempt;
//import LoginFailureAttemptRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.when;
//
//public class LoginFailureAttemptServiceTest {
//
//    @InjectMocks
//    private LoginFailureAttemptService loginFailureAttemptService;
//
//    @Mock
//    LoginFailureAttemptRepository loginFailureAttemptRepository;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    public void deleteLoginAttempt() {
//        Mockito.doNothing().when(loginFailureAttemptRepository).delete(getLoginFailureAttempt());
//        loginFailureAttemptService.deleteLoginAttempt(Mockito.any());
//    }
//
//    @Test
//    public void saveLoginAttempt() {
//        when(loginFailureAttemptRepository.saveAndFlush(getLoginFailureAttempt()))
//                .thenReturn(getLoginFailureAttempt());
//
//        LoginFailureAttempt loginFailureAttempt = loginFailureAttemptRepository.saveAndFlush(getLoginFailureAttempt());
//
//        assertEquals(getLoginFailureAttempt(), loginFailureAttempt);
//    }
//
//    @Test
//    public void getLoginAttempt() {
//        when(loginFailureAttemptRepository.findByTenantIdAndMemberId(anyString(), anyString()))
//                .thenReturn(Optional.of(getLoginFailureAttempt()));
//        Optional<LoginFailureAttempt> loginFailureAttempt= loginFailureAttemptService.getLoginAttempt("123","abc123");
//        assertEquals(loginFailureAttempt,loginFailureAttempt);
//    }
//
//    @Test
//    public void getFailedAttempts() {
//        Optional<Integer> count = Optional.of(9);
//        when(loginFailureAttemptRepository.getFailureCount(anyString(), anyString()))
//                .thenReturn(1);
//       Integer failCount= loginFailureAttemptService.getFailedAttempts("123","abc123");
//        assertEquals(1,failCount);
//    }
//
//
//    public Integer getFailedAttempts(String clientId, String memberId) {
//
//        return loginFailureAttemptRepository.getFailureCount(clientId, memberId);
//    }
//
//    private LoginFailureAttempt getLoginFailureAttempt() {
//        LoginFailureAttempt loginFailureAttempt = new LoginFailureAttempt();
//        loginFailureAttempt.setTenantId("123");
//        loginFailureAttempt.setMemberId("abc123");
//        return loginFailureAttempt;
//    }
//
//}
