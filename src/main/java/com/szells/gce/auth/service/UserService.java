package com.szells.gce.auth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.szells.gce.auth.client.AuthClient;
import com.szells.gce.auth.exception.AuthServiceException;
import com.szells.gce.auth.exception.UserNotFoundException;
import com.szells.gce.auth.model.request.CreateNewUserRequest;
import com.szells.gce.auth.model.request.CreateUpdateCustomerRequest;
import com.szells.gce.auth.model.request.CreateUpdateUserRequest;
import com.szells.gce.auth.model.request.CreateUserRequest;
import com.szells.gce.auth.model.response.GenericResponse;
import com.szells.gce.auth.model.spi.CustomerUserRepresentation;
import com.szells.gce.auth.model.spi.UserRepresentation;
import com.szells.gce.auth.util.RequestGenerator;
import com.szells.gce.auth.util.SuccessLogDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static com.szells.gce.auth.constant.AuthConstants.*;
import static com.szells.gce.auth.constant.ErrorCode.MEMBER_ID_NOT_FOUND;
import static com.szells.gce.auth.constant.ErrorCode.USER_NOT_FOUND;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

//import com.szells.gce.core.utilities.loggingService.CoreLogger;

@Service
@Log4j2
@RequiredArgsConstructor
public class UserService {
    private final SuccessLogDetails successLogDetails;

//    private final CoreLogger coreLogger;

    private final RequestGenerator requestGenerator;

    private final ConversionService conversionService;

    private final AuthClient authClient;

    private final RestTemplate restTemplate;

    private final MemberUserService memberUserService;

    private final MemberService memberService;

    @Value("${create-update-member}")
    private String createUpdateMemberServiceURL;
    @Value("${update-customer}")
    private String updateCustomerServiceUrl;



    @Transactional
    public GenericResponse createNewUser(CreateNewUserRequest createNewUserRequest, String tenantId) {
        try {
            CreateUserRequest createUserRequest = new CreateUserRequest();
            createUserRequest.setMember_id(createNewUserRequest.getCustomerId().toString());
            createUserRequest.setUsername(createNewUserRequest.getUsername());
            createUserRequest.setPassword(createNewUserRequest.getPassword());
            UserRepresentation userRepresentation = conversionService.convert(createUserRequest, UserRepresentation.class);
          System.out.println("conversionService completed....");
            Objects.requireNonNull(userRepresentation, "Failed to convert Create User Request");
            userRepresentation.getAttributes().putIfAbsent(CUSTOMER_ID, createNewUserRequest.getCustomerId());
            URI createUserUri = requestGenerator.generateURI(CREATE_USER_ENDPOINT, tenantId);
            System.out.println("createUserUri ->>...."+createUserUri);
            HttpEntity<UserRepresentation> request = requestGenerator.generateUserRequest(userRepresentation, tenantId);
            System.out.println("generateUserRequest  success->>...."+request.getBody());
            try{
                authClient.invoke(createUserUri, POST, request, Object.class);
            }catch(Exception e){
                e.printStackTrace();
            }

            System.out.println("HttpEntity invocation completed->>...."+request.getBody());
            UserRepresentation representation = getUser(createUserRequest.getUsername(), tenantId);
            System.out.println("representation response ->>...."+representation.getId());
            String keycloakUserId = representation.getId();
            System.out.println("keycloakUserId  ->>...."+keycloakUserId);

            if (keycloakUserId != null) {
                CreateUpdateCustomerRequest customerRequest = new CreateUpdateCustomerRequest();
                customerRequest.setUserId(createUserRequest.getUsername());
                customerRequest.setCustomerId(createNewUserRequest.getCustomerId());
                org.springframework.http.HttpHeaders httpHeaders = new org.springframework.http.HttpHeaders();
                httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<CreateUpdateCustomerRequest> entity = new HttpEntity<>(customerRequest, httpHeaders);
                ResponseEntity<String> exchange = restTemplate.exchange(updateCustomerServiceUrl, PUT, entity, String.class);
                exchange.getStatusCode();
                return new GenericResponse(true, 200, "UserId updated successfully", "UserId updated successfully ", "done");
                     }else{
                         return new GenericResponse(true, 200, "UserId updated successfully", "UserId updated successfully ", "done");
                     }
            } catch(HttpClientErrorException ex){

                try {
                    Map errResponseBody = new ObjectMapper().readValue(ex.getResponseBodyAsString(), Map.class);
                    String errorMessage = String.valueOf(errResponseBody.get("error_description"));
                    errorMessage = "null".equals(errorMessage) ? String.valueOf(errResponseBody.get("error")) : errorMessage;
                    // throw new LogoutException(null, errorMessage);
                    return new GenericResponse(false, ex.getStatusCode().value(), "Profile Creation failed.", null, errorMessage);
                } catch (JsonProcessingException e) {

                    throw new RuntimeException(e);
                }
            } catch(Exception e){
                return new GenericResponse(false, HttpStatus.OK.value(), "Profile Creation failed.", null, e.getMessage());
            }
        }
    @Transactional
    public GenericResponse createUserInKeycloak(CreateNewUserRequest createNewUserRequest, String tenantId) {
        try {
            CreateUserRequest createUserRequest = new CreateUserRequest();
            //createUserRequest.setMember_id(createNewUserRequest.getCustomerId().toString());
            createUserRequest.setUsername(createNewUserRequest.getUsername());
            createUserRequest.setPassword(createNewUserRequest.getPassword());
            UserRepresentation userRepresentation = conversionService.convert(createUserRequest, UserRepresentation.class);
            System.out.println("conversionService completed....");
            Objects.requireNonNull(userRepresentation, "Failed to convert Create User Request");
            //userRepresentation.getAttributes().putIfAbsent(CUSTOMER_ID, createNewUserRequest.getCustomerId());
            URI createUserUri = requestGenerator.generateURI(CREATE_USER_ENDPOINT, tenantId);
            System.out.println("createUserUri ->>...."+createUserUri);
            HttpEntity<UserRepresentation> request = requestGenerator.generateUserRequest(userRepresentation, tenantId);
            System.out.println("generateUserRequest  success->>...."+request.getBody());
            try{
                authClient.invoke(createUserUri, POST, request, Object.class);
            }catch(Exception e){
                e.printStackTrace();
            }

            System.out.println("HttpEntity invocation completed->>...."+request.getBody());
            UserRepresentation representation = getUser(createUserRequest.getUsername(), tenantId);
            System.out.println("representation response ->>...."+representation.getId());
            String keycloakUserId = representation.getId();
            System.out.println("keycloakUserId  ->>...."+keycloakUserId);

            if (keycloakUserId != null) {
                /*CreateUpdateCustomerRequest customerRequest = new CreateUpdateCustomerRequest();
                customerRequest.setUserId(createUserRequest.getUsername());
                customerRequest.setCustomerId(createNewUserRequest.getCustomerId());
                org.springframework.http.HttpHeaders httpHeaders = new org.springframework.http.HttpHeaders();
                httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<CreateUpdateCustomerRequest> entity = new HttpEntity<>(customerRequest, httpHeaders);
                ResponseEntity<String> exchange = restTemplate.exchange(updateCustomerServiceUrl, PUT, entity, String.class);
                exchange.getStatusCode();*/
                return new GenericResponse(true, 200, "UserId created successfully", "UserId created successfully ", keycloakUserId);
            }else{
                return new GenericResponse(true, 200, "Issue while creating user", "Issue while creating user", "done");
            }
        } catch(HttpClientErrorException ex){

            try {
                Map errResponseBody = new ObjectMapper().readValue(ex.getResponseBodyAsString(), Map.class);
                String errorMessage = String.valueOf(errResponseBody.get("error_description"));
                errorMessage = "null".equals(errorMessage) ? String.valueOf(errResponseBody.get("error")) : errorMessage;
                // throw new LogoutException(null, errorMessage);
                return new GenericResponse(false, ex.getStatusCode().value(), "Profile Creation failed.", null, errorMessage);
            } catch (JsonProcessingException e) {

                throw new RuntimeException(e);
            }
        } catch(Exception e){
            return new GenericResponse(false, HttpStatus.OK.value(), "Profile Creation failed.", null, e.getMessage());
        }
    }



    @Transactional
    public GenericResponse createUser(CreateUserRequest createUserRequest, String tenantId) {
        try {
            Long memberID = Optional.ofNullable(createUserRequest.getMember_id())
                    .map(Long::valueOf)
                    .orElse(Optional.ofNullable(memberService.checkIfEmailValid(createUserRequest.getEmail())).orElseThrow(() -> new RuntimeException("Member ID is null")));
            UserRepresentation userRepresentation = conversionService.convert(createUserRequest, UserRepresentation.class);
            Objects.requireNonNull(userRepresentation, "Failed to convert Create User Request");
            userRepresentation.getAttributes().putIfAbsent(MEMBER_ID, createUserRequest.getMemberId());
            URI createUserUri = requestGenerator.generateURI(CREATE_USER_ENDPOINT, tenantId);
            HttpEntity<UserRepresentation> request = requestGenerator.generateUserRequest(userRepresentation, tenantId);
            authClient.invoke(createUserUri, POST, request, Object.class);
            UserRepresentation representation = getUser(createUserRequest.getUsername(), tenantId);
            String keycloakUserId = representation.getId();
            if (!memberUserService.checkUserIdExists(keycloakUserId)) {
                System.out.println("Member Id is not exist ="+keycloakUserId);
                CreateUpdateUserRequest createUpdateUserRequest = new CreateUpdateUserRequest();
                createUpdateUserRequest.setUserId(keycloakUserId);
                createUpdateUserRequest.setMemberId(memberID);
                createUpdateUserRequest.setAddressLine1(createUserRequest.getAddress_line1());
                createUpdateUserRequest.setAddressLine2(createUserRequest.getAddress_line2());
                createUpdateUserRequest.setAddressLine3(createUserRequest.getAddress_line3());
                createUpdateUserRequest.setCity(createUserRequest.getCity());
                createUpdateUserRequest.setCountry(createUserRequest.getCountry());
                createUpdateUserRequest.setPostalCode(createUserRequest.getPostal_code());
                createUpdateUserRequest.setState(createUserRequest.getState());
                org.springframework.http.HttpHeaders httpHeaders = new org.springframework.http.HttpHeaders();
                httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                httpHeaders.set("correlationId", tenantId);
                httpHeaders.set("X-Visibility-Scope-Key", "1");
                HttpEntity<CreateUpdateUserRequest> entity = new HttpEntity<>(createUpdateUserRequest, httpHeaders);
                ResponseEntity<String> exchange = restTemplate.exchange(createUpdateMemberServiceURL, POST, entity, String.class);
                exchange.getStatusCode();
            }
//        postForEntity("localhost:8025/core/members/v2/createOrUpdate", createUserRequest, String.class).getStatusCode();
            System.out.println("Praveen - GenericResponse");
            return new GenericResponse(true, 200, "Profile created succeesfully", null, "done");
        } catch (HttpClientErrorException ex) {

            try {
                Map errResponseBody = new ObjectMapper().readValue(ex.getResponseBodyAsString(), Map.class);
                String errorMessage = String.valueOf(errResponseBody.get("error_description"));
                errorMessage = "null".equals(errorMessage) ? String.valueOf(errResponseBody.get("error")) : errorMessage;
                // throw new LogoutException(null, errorMessage);
                return new GenericResponse(false, ex.getStatusCode().value(), "Profile Creation failed.", null, errorMessage);
            } catch (JsonProcessingException e) {

                throw new RuntimeException(e);
            }

            //return new GenericResponse(false, ex.getStatusCode().value(), "Login Failed", null, new LoginError(String.valueOf(ex.getStatusCode().value() + " " + ex.getStatusText())));
        } catch (Exception e) {
            return new GenericResponse(false, HttpStatus.OK.value(), "Profile Creation failed.", null, e.getMessage());
        }
        // authClient.invoke(createUserUri, POST, request, Object.class);
        //UserRepresentation representation = getUser(createUserRequest.getUsername(), tenantId);
        // System.out.println("nearing response");
        //	System.out.println(representation);
//        coreLogger.log("User created successfully", Level.INFO, successLogDetails.getLogDetails(List.of("userName", "password"), createUserRequest, representation));
        //  return representation;
    }

    public UserRepresentation getUser(String userName, String tenantId) {
        System.out.println("user representation  getUser "+"");
        URI getUserUri = requestGenerator.generateURI(GET_USER_ENDPOINT, tenantId, userName);
        System.out.println("user representation  getUserUri "+getUserUri+"");
        HttpEntity<?> request = requestGenerator.generateUserRequest(null, tenantId);
        System.out.println("Requset");
        System.out.println(request);
        System.out.println(getUserUri);
        return Optional.ofNullable(authClient.invoke(getUserUri, GET, request, UserRepresentation[].class))
                .map(Arrays::asList)
                .filter(list -> !CollectionUtils.isEmpty(list))
                .map(list -> list.get(0))
                .orElseThrow(() -> new AuthServiceException(USER_NOT_FOUND.getCode(), userName));


    }

    public UserRepresentation getUserByMemberId(String memberId, String tenantId) {
        URI userUri = requestGenerator.generateURI(GET_USER_BY_MEMBER_ID_ENDPOINT, tenantId, memberId);
        HttpEntity<?> request = requestGenerator.generateUserRequest(null, tenantId);
        Optional<UserRepresentation[]> userArray = Optional.ofNullable(authClient.invoke(userUri, GET, request, UserRepresentation[].class));
        UserRepresentation user = userArray.map(Arrays::asList)
                .filter(list -> !CollectionUtils.isEmpty(list))
                .map(list -> list.get(0))
                .orElseThrow(() -> new UserNotFoundException(MEMBER_ID_NOT_FOUND.getCode(), memberId));
//        coreLogger.log("User retrived by member id successfully", Level.INFO, successLogDetails.getLogDetails(null, request, user));
        return user;
    }
}
