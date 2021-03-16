//package com.szells.gce.auth.service;
//
//import AuthClient;
//import CreateUserRequest;
//import UserRepresentation;
//import TenantRealmResolver;
//import RequestGenerator;
//import SuccessLogDetails;
//import com.szells.gce.core.utilities.loggingService.CoreLogger;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.core.convert.ConversionService;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//
//import java.net.URI;
//
//import static TestObjectFactory.createUserRequest;
//import static TestObjectFactory.userRepresentation;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.when;
//import static org.springframework.http.MediaType.APPLICATION_JSON;
//
//
//public class UserServiceTest {
//
//    @InjectMocks
//    private UserService userService;
//
//    @Mock
//    private ConversionService conversionService;
//
//    @Mock
//    private TenantRealmResolver tenantRealmResolver;
//
//    @Mock
//    private RequestGenerator requestGenerator;
//
//    @Mock
//    private AuthClient authClient;
//
//    @Mock
//    private CoreLogger coreLogger;
//
//    @Mock
//    private SuccessLogDetails successLogDetails;
//
//
//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    public  void createUserTest()  {
//        String clientId="1";
//        UserRepresentation mockUserRepresentation[] = new UserRepresentation[1];
//        mockUserRepresentation[0] = userRepresentation();
//
//        when(conversionService.convert(any(CreateUserRequest.class), any(Class.class)))
//                .thenReturn(userRepresentation());
//        when(requestGenerator.generateUserRequest(any(), anyString())).thenReturn(requestForGetUser(clientId));
//        when(requestGenerator.generateURI(anyString(), anyString())).thenReturn(getUri());
//        when(requestGenerator.generateURI(anyString(), anyString(),anyString())).thenReturn(getUri());
//        when(authClient.invoke(getUri(), HttpMethod.GET, requestForGetUser(clientId), UserRepresentation[].class)).thenReturn(mockUserRepresentation);
//        UserRepresentation userRepresentation=userService.createUser(createUserRequest(),clientId);
//        assertEquals(mockUserRepresentation[0],userRepresentation);
//    }
//
//    @Test
//    public void getUserTest() throws Exception{
//        String userName = "xyz@gmail.com";
//        String clientId = "1";
//        String realm = "gce";
//        URI mockURI = new URI("http://localhost:8080/auth/admin/realms/gce/users?username="+userName);
//        HttpMethod getHttpMethod = HttpMethod.GET ;
//
//        UserRepresentation mockUserRepresentation[] = new UserRepresentation[1];
//        mockUserRepresentation[0] = userRepresentation();
//
//        when(tenantRealmResolver.resolveRealm(clientId)).thenReturn(realm);
//        when(requestGenerator.generateURI(anyString(), anyString(), anyString())).thenReturn(mockURI);
//        when(requestGenerator.generateUserRequest(any(), anyString())).thenReturn(requestForGetUser(clientId));
//        when(authClient.invoke(mockURI, getHttpMethod, requestForGetUser(clientId), UserRepresentation[].class)).thenReturn(mockUserRepresentation);
//
//        UserRepresentation userRepresentation = userService.getUser(userName, clientId);
//        assertEquals(mockUserRepresentation[0], userRepresentation);
//    }
//
//    @Test
//    public void getUserByMemberId(){
//        String memberId = "1";
//        String tenantId = "1";
//        String realm = "gce";
//        URI mockURI =URI.create("http://localhost:8080/auth/admin/realms/gce/users/"+memberId);
//        HttpMethod getHttpMethod = HttpMethod.GET ;
//
//        UserRepresentation mockUserRepresentation[] = new UserRepresentation[1];
//        mockUserRepresentation[0] = userRepresentation();
//        when(requestGenerator.generateURI(anyString(), anyString(), anyString())).thenReturn(mockURI);
//        when(requestGenerator.generateUserRequest(any(), anyString())).thenReturn(requestForGetUser(tenantId));
//        when(authClient.invoke(mockURI, getHttpMethod, requestForGetUser(tenantId), UserRepresentation[].class)).thenReturn(mockUserRepresentation);
//        UserRepresentation userRepresentation = userService.getUserByMemberId(memberId, tenantId);
//        assertEquals(mockUserRepresentation[0], userRepresentation);
//    }
//
//    private UserRepresentation getUserRepresentation() {
//        UserRepresentation userRepresentation=new UserRepresentation();
//        userRepresentation.setUsername("xyz@gmail.com");
//        return userRepresentation;
//    }
//
//    private HttpEntity<UserRepresentation> requestForGetUser(String clientID) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("client-id", clientID);
//        headers.setContentType(APPLICATION_JSON);
//        return new HttpEntity<>(null, headers);
//    }
//
//    private HttpEntity<UserRepresentation> requestForCreateUser(String clientID) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("client-id", clientID);
//        headers.setContentType(APPLICATION_JSON);
//        return new HttpEntity<>(userRepresentation(), headers);
//    }
//    private URI getUri() {
//        return URI.create("http://localhost:8080/auth/admin/realms/gce/users?username=xyz@gmail.com");
//    }
//
//}
