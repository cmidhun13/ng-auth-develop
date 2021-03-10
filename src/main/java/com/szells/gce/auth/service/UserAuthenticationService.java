package com.szells.gce.auth.service;

import com.szells.gce.auth.client.AuthClient;
import com.szells.gce.auth.config.OAuth2ClientProperties;
import com.szells.gce.auth.domain.Member;
import com.szells.gce.auth.exception.AuthServiceException;
import com.szells.gce.auth.model.request.*;
import com.szells.gce.auth.model.response.*;
import com.szells.gce.auth.util.RequestGenerator;
import com.szells.gce.auth.util.SuccessLogDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.conn.HttpHostConnectException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static com.szells.gce.auth.constant.AuthConstants.*;
import static com.szells.gce.auth.constant.ErrorCode.ACCESS_TOKEN_ACQUIRE_FAILED;
import static org.springframework.http.HttpMethod.*;

//import com.szells.gce.core.utilities.loggingService.CoreLogger;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserAuthenticationService {
    private final SuccessLogDetails successLogDetails;

//    private final CoreLogger coreLogger;
    @Value("${customerService.url}")
    private  String customerServiceUrl;
    @Value("${customerService.getCustomer}")
    private  String getCustomerUrl;
   /* @Value("${oauth2.client-id}")
    private  String clientId;
    @Value("${oauth2.client-secret}")
    private  String clientSecret;*/

    private final RequestGenerator requestGenerator;

    private final AuthClient authClient;

    private final AuthService authService;

    private final UserService userService;

    private final LoginFailureAttemptService loginFailureAttemptService;

    private final MemberUserService memberUserService;

    private final MemberService memberService;

    private final RestTemplate restTemplate;

    @Value("${forgot-password-hash}")
    private String generateHashCodeURL;

    private final OAuth2ClientProperties oAuth2ClientProperties;


    public String resetPasswordForUser(ChangePasswordRequest changePasswordRequest, String tenantId) {
        String s = memberUserService.getUserId(changePasswordRequest.getMemberId()).orElseThrow(() -> new RuntimeException("User ID is not present"));
        HttpEntity<CredentialRepresentation> request = requestGenerator.resetpasswordRequest(changePasswordRequest, tenantId);
        return authClient.invoke(requestGenerator.generateURI(PUT_RESET_PASSWORD, tenantId, s), PUT, request, String.class);
    }


    public Member forgotEmailForUser(ForgotEmailRequest forgotEmailRequest) {
        return memberService.getUserId(forgotEmailRequest);
    }

    public Long resetPasswordForUser_AuthCode(ChangePasswordRequest changePasswordRequest, String tenantId) {
        Long memberId = memberService.findMemberIdByHashCd(changePasswordRequest.getHashCd());
        if (null != memberId) {
            changePasswordRequest.setMemberId(memberId);
            resetPasswordForUser(changePasswordRequest, tenantId);
            UpdateHashRequest updateHashRequest = new UpdateHashRequest();
            updateHashRequest.setHashCd(null);
            updateHashRequest.setMemberid(Long.valueOf(memberId.toString()));
            updateHashRequest.setDelete_fl(true);
            ResponseEntity<String> stringResponseEntity = updateHashForMember(updateHashRequest, tenantId);
        }
        return memberId;
    }
    public String getCommunPref(Long customerId){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "application/json");
        String URL = customerServiceUrl + getCustomerUrl;
        String query ="{\n" +
                "customer(id:\"dynamic_customer_id\"){\n" +
                "\tcommunicationPreferences\n" +
                "}\n" +
                "}";
        query=query.replace("dynamic_customer_id",String.valueOf(customerId));
        ResponseEntity<Object> customer = restTemplate.postForEntity(URL, new HttpEntity<>(query, headers), Object.class);
        return (String) ((LinkedHashMap)((LinkedHashMap)customer.getBody()).get("customer")).get("communicationPreferences");
    }
    public String forgotPasswordForUser(ForgotPasswordRequest forgotPasswordRequest, String tenantId) {
        Long member_id = memberService.checkIfEmailValid(forgotPasswordRequest.getEmail());
        Long customerId = memberService.getCustomerId(member_id);
        String communPref = getCommunPref(customerId);
        log.info("Member ID is {}", member_id);
        String nonce = null;
        if (!StringUtils.isEmpty(member_id)) {
            nonce = UUID.randomUUID().toString();
            UpdateHashRequest updateHashRequest = new UpdateHashRequest();
            updateHashRequest.setHashCd(nonce);
            updateHashRequest.setMemberid(member_id);
            updateHashRequest.setDelete_fl(false);
            ResponseEntity<String> stringResponseEntity = updateHashForMember(updateHashRequest, tenantId);
            log.info("Response from the service {}", stringResponseEntity);
            if (!stringResponseEntity.getStatusCode().isError() && "email".equals(communPref.toLowerCase())) {
                memberService.sendEmail(member_id,nonce);
            }
        }
        return nonce;
    }

    private ResponseEntity<String> updateHashForMember(UpdateHashRequest updateHashRequest, String tenantId) {
        org.springframework.http.HttpHeaders httpHeaders = new org.springframework.http.HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("correlationId", tenantId);
        httpHeaders.set("X-Visibility-Scope-Key", "1");
        HttpEntity<UpdateHashRequest> entity = new HttpEntity<>(updateHashRequest, httpHeaders);
        return restTemplate.exchange(generateHashCodeURL, POST, entity, String.class);
    }

    public GenericResponse generateTokenForUser(UserTokenRequest tokenRequest, String tenantId) {
        URI clientTokenUri = requestGenerator.generateForFetchToken();
        URI authTokenUri = requestGenerator.generateURI(GENERATE_TOKEN_ENDPOINT, tenantId);
        TokenResponse tokenResponse ;

            tokenRequest.setClientId(oAuth2ClientProperties.getClientId());
            tokenRequest.setClientSecret(oAuth2ClientProperties.getClientSecret());


            HttpEntity<MultiValueMap<String, String>> request = requestGenerator.generateUserTokenRequest(tokenRequest);
            tokenResponse = authClient.invoke(authTokenUri, POST, request, TokenResponse.class);
            String accessToken = Optional.ofNullable(tokenResponse)
                    .map(TokenResponse::getAccessToken)
                    .orElseThrow(() -> new AuthServiceException(ACCESS_TOKEN_ACQUIRE_FAILED.getCode(),""));


            ValidateTokenResponse claims = validateToken(tokenRequest, tenantId, accessToken);
	  

  /*       UserRepresentation userRepresentation = userService.getUser(tokenRequest.getUsername(), tenantId);
	 System.out.println("user repre");
	 System.out.println(userRepresentation);
         String  keycloakUserId = userRepresentation.getId();
	 System.out.println("user Id");
	   System.out.println(keycloakUserId);	 
    
	 String  s = memberUserService.getKeycloakUserId(keycloakUserId); //.orElseThrow(() -> new RuntimeException("Member User ID is not present"));

System.out.println(s);  //Gives the member id in case , value is there.. if not, it gives null.. TODO : if null, then take the tokenRequest.getUserName() and this keycloakUserId to member listener and do insertion to the member_user table. You can get member id frmo member table based on the email
*/



        return new GenericResponse(true, HttpStatus.OK.value(), "Login Successful", null, tokenResponse);
    }

    private ValidateTokenResponse validateToken(UserTokenRequest tokenRequest, String tenantId, String token) {
        System.out.println(tokenRequest.getClientId());
        System.out.println("am here");
        if (tokenRequest.getClientId().isEmpty() || tokenRequest.getClientId() != null) {
            tokenRequest.setClientId(oAuth2ClientProperties.getClientId());
            tokenRequest.setClientSecret(oAuth2ClientProperties.getClientSecret());
        }
        ValidateTokenRequest validateTokenRequest = ValidateTokenRequest.builder()
                .token(token)
                .clientId(tokenRequest.getClientId())
                .clientSecret(tokenRequest.getClientSecret())
                .build();

        return authService.validateToken(validateTokenRequest, tenantId);
    }

    private boolean isAccountLocked(String userId, String tenantId) {
        URI bruteForceDetectionUri = requestGenerator.generateURI(ATTACK_DETECTION_ENDPOINT, tenantId, userId);
        HttpEntity<MultiValueMap<String, String>> request = requestGenerator.generateBruteForceAttackDetectionRequest(tenantId);
        BruteForceAttackDetectionResponse bruteForceAttackDetectionResponse = authClient.invoke(bruteForceDetectionUri, GET, request, BruteForceAttackDetectionResponse.class);
        return bruteForceAttackDetectionResponse.isDisabled();
    }
}
