package com.szells.gce.auth.controller;

import com.szells.gce.auth.constant.AuthConstants;
import com.szells.gce.auth.domain.Member;
import com.szells.gce.auth.model.request.*;
import com.szells.gce.auth.model.response.GenericResponse;
import com.szells.gce.auth.model.response.TokenResponse;
import com.szells.gce.auth.model.response.ValidateTokenResponse;
import com.szells.gce.auth.service.AuthService;
import com.szells.gce.auth.service.UserAuthenticationService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    private final UserAuthenticationService userAuthenticationService;

    @ApiOperation(value = "Generate Token using client-id and client-secret ")
    @PostMapping("/token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = AuthConstants.TENANT_ID, value = "Tenant Id", required = true, paramType = "header", dataTypeClass = String.class),
            @ApiImplicitParam(name = AuthConstants.X_CORRELATION_ID, value = "Correlation Id", required = true, paramType = "header", dataTypeClass = String.class)
    })
    public TokenResponse generateToken(@RequestBody @Valid ClientTokenRequest tokenRequest,
                                       @RequestHeader(AuthConstants.TENANT_ID) String tenantId) {
        return authService.generateToken(tokenRequest, tenantId);
    }
    @ApiOperation(value = "GenerateToken using userName and password ")
    @PostMapping("/login")
    @ApiImplicitParams({
            @ApiImplicitParam(name = AuthConstants.TENANT_ID, value = "Tenant Id", required = true, paramType = "header", dataTypeClass = String.class),
            @ApiImplicitParam(name = AuthConstants.X_CORRELATION_ID, value = "Correlation Id", required = true, paramType = "header", dataTypeClass = String.class)
    })
    public GenericResponse generateUserToken(@RequestBody @Valid UserTokenRequest tokenRequest,
                                             @RequestHeader(AuthConstants.TENANT_ID) String tenantId) {
        return userAuthenticationService.generateTokenForUser(tokenRequest, tenantId);
    }

    @ApiOperation(value = "GenerateToken using client-id and refresh-token")
    @PostMapping("/refresh")
    @ApiImplicitParams({
            @ApiImplicitParam(name = AuthConstants.TENANT_ID, value = "Tenant Id", required = true, paramType = "header", dataTypeClass = String.class),
            @ApiImplicitParam(name = AuthConstants.X_CORRELATION_ID, value = "Correlation Id", required = true, paramType = "header", dataTypeClass = String.class)
    })
    public TokenResponse generateWithRefreshToken(@RequestBody @Valid RefreshTokenRequest tokenRequest,
                                                  @RequestHeader(AuthConstants.TENANT_ID) String tenantId) {
        return authService.generateWithRefreshToken(tokenRequest, tenantId);
    }
    @ApiOperation(value = "Validate the token which is generated using client_id and client_secret")
    @ApiResponses(value = {
            @ApiResponse(code = 3016, message = "Token Invalid"),
            @ApiResponse(code = 3017, message = "Token Expired"),
            @ApiResponse(code = 3001, message = "One or more parameter(s) required is missing in the header"),
            @ApiResponse(code = 3002, message = "One or more parameter(s) is invalid in the header")
    })

    @PostMapping("/introspect")
    @ApiImplicitParams({
            @ApiImplicitParam(name = AuthConstants.TENANT_ID, value = "Tenant Id", required = true, paramType = "header", dataTypeClass = String.class),
            @ApiImplicitParam(name = AuthConstants.X_CORRELATION_ID, value = "Correlation Id", required = true, paramType = "header", dataTypeClass = String.class)
    })
    public ValidateTokenResponse validateToken(@RequestBody @Valid ValidateTokenRequest validateTokenRequest,
                                               @RequestHeader(AuthConstants.TENANT_ID) String tenantId) {
        return authService.validateToken(validateTokenRequest, tenantId);
    }
    @PostMapping("/changepassword")
    @ApiImplicitParams({
            @ApiImplicitParam(name = AuthConstants.TENANT_ID, value = "Tenant Id", required = true, paramType = "header", dataTypeClass = String.class)
    })

    public GenericResponse changePassword(@RequestBody @Valid ChangePasswordRequest changePasswordRequest, @RequestHeader(AuthConstants.TENANT_ID) String tenantId) {
//        authService
        GenericResponse genericResponse = new GenericResponse(true, HttpStatus.OK.value(), "Password Changed Successfully", null, null);
        try {
            userAuthenticationService.resetPasswordForUser(changePasswordRequest, tenantId);
        } catch (RuntimeException e) {
            genericResponse.setSuccess(false);
            genericResponse.setMessage(e.getMessage());
        }
        return genericResponse;
    }
    @PostMapping("/forgotemail")
    public GenericResponse forgotEmail(@RequestBody ForgotEmailRequest forgotEmailRequest, @RequestHeader(AuthConstants.TENANT_ID) String tenantId) {
        GenericResponse genericResponse = new GenericResponse(true, HttpStatus.OK.value(), "Please enter valid details", null, null);
        Member member = userAuthenticationService.forgotEmailForUser(forgotEmailRequest);
        if (member != null) {
            return new GenericResponse(member != null, HttpStatus.OK.value(), "Record found", null, member);
        } else {
            return new GenericResponse(false, HttpStatus.OK.value(), "Please enter the correct details", null, null);
        }
    }
    @PostMapping("/forgotpassword")
    public GenericResponse forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest, @RequestHeader(AuthConstants.TENANT_ID) String tenantId) {
        String nonce = userAuthenticationService.forgotPasswordForUser(forgotPasswordRequest, tenantId);
        return new GenericResponse(nonce != null, HttpStatus.OK.value(), nonce != null ? "A link with instructions has been sent to registered email" : "Please enter the correct email ID", null, nonce);
    }
    @PostMapping("/resetpassword")
    public GenericResponse resetPassword(@RequestBody ChangePasswordRequest changePasswordRequest, @RequestHeader(AuthConstants.TENANT_ID) String tenantId) {
        Long memberId = userAuthenticationService.resetPasswordForUser_AuthCode(changePasswordRequest, tenantId);
        if (memberId != null) {
            return new GenericResponse(true, HttpStatus.OK.value(), "Password Changed Successfully", null, memberId);
        } else {
            return new GenericResponse(false, HttpStatus.OK.value(), "The link is no longer valid", null, memberId);
        }

    }
}
