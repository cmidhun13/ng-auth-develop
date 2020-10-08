package com.szells.gce.auth.controller;

import com.szells.gce.auth.constant.AuthConstants;
import com.szells.gce.auth.model.request.CreateNewUserRequest;
import com.szells.gce.auth.model.request.CreateUserRequest;
import com.szells.gce.auth.model.response.GenericResponse;
import com.szells.gce.auth.model.spi.UserRepresentation;
import com.szells.gce.auth.service.UserService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;
    @PostMapping("/users")
    @ApiOperation(value = "Create a user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 3013, message = " Authorisation Failed"),
            @ApiResponse(code = 3015, message = "Token not provided"),
            @ApiResponse(code = 3001, message = "One or more parameter(s) required is missing in the header"),
            @ApiResponse(code = 3002, message = "One or more parameter(s) is invalid in the header"),
            @ApiResponse(code = 3016, message = "Token Invalid"),
            @ApiResponse(code = 3017, message = "Token Expired")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = AuthConstants.TENANT_ID, value = "Tenant Id", required = true, paramType = "header", dataTypeClass = String.class),
            @ApiImplicitParam(name = AuthConstants.X_CORRELATION_ID, value = "Correlation Id", required = true, paramType = "header", dataTypeClass = String.class)
    })
    @ResponseStatus(CREATED)
    public GenericResponse createUser(@RequestBody @Valid CreateUserRequest createUserRequest,
                                      @RequestHeader(AuthConstants.TENANT_ID) String tenantId) {
        System.out.println("inside create- Praveen");
        return userService.createUser(createUserRequest, tenantId);
    }
    @PostMapping("/user")
    @ApiOperation(value = "Create a user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 3013, message = " Authorisation Failed"),
            @ApiResponse(code = 3015, message = "Token not provided"),
            @ApiResponse(code = 3001, message = "One or more parameter(s) required is missing in the header"),
            @ApiResponse(code = 3002, message = "One or more parameter(s) is invalid in the header"),
            @ApiResponse(code = 3016, message = "Token Invalid"),
            @ApiResponse(code = 3017, message = "Token Expired")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = AuthConstants.TENANT_ID, value = "Tenant Id", required = true, paramType = "header", dataTypeClass = String.class),
            @ApiImplicitParam(name = AuthConstants.X_CORRELATION_ID, value = "Correlation Id", required = true, paramType = "header", dataTypeClass = String.class)
    })
    @ResponseStatus(CREATED)
    public GenericResponse createNewUser(@RequestBody @Valid CreateNewUserRequest createUserRequest,
                                      @RequestHeader(AuthConstants.TENANT_ID) String tenantId) {
        System.out.println("inside create- Praveen new user creation");
        return userService.createNewUser(createUserRequest, tenantId);
    }
    @PostMapping("/create")
    @ApiOperation(value = "Create a user in keycloak")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 3013, message = " Authorisation Failed"),
            @ApiResponse(code = 3015, message = "Token not provided"),
            @ApiResponse(code = 3001, message = "One or more parameter(s) required is missing in the header"),
            @ApiResponse(code = 3002, message = "One or more parameter(s) is invalid in the header"),
            @ApiResponse(code = 3016, message = "Token Invalid"),
            @ApiResponse(code = 3017, message = "Token Expired")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = AuthConstants.TENANT_ID, value = "Tenant Id", required = true, paramType = "header", dataTypeClass = String.class),
            @ApiImplicitParam(name = AuthConstants.X_CORRELATION_ID, value = "Correlation Id", required = true, paramType = "header", dataTypeClass = String.class)
    })
    @ResponseStatus(CREATED)
    public GenericResponse createUserInKeycloak(@RequestBody @Valid CreateNewUserRequest createUserRequest,
                                         @RequestHeader(AuthConstants.TENANT_ID) String tenantId) {
        System.out.println("inside create- Praveen new user creation");
        return userService.createUserInKeycloak(createUserRequest, tenantId);
    }
    @GetMapping("/users/{memberId}")
    @ApiOperation(value = "retrieve a user by member id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 3007, message = "member-id does not exists in our records or is not eligible for the request")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = AuthConstants.TENANT_ID, value = "Tenant Id", required = true, paramType = "header", dataTypeClass = String.class),
            @ApiImplicitParam(name = AuthConstants.X_CORRELATION_ID, value = "Correlation Id", required = true, paramType = "header", dataTypeClass = String.class)
    })
    @ResponseStatus(OK)
    public UserRepresentation userByMemberId(@PathVariable String memberId,
                                             @RequestHeader(AuthConstants.TENANT_ID) String tenantId) {
        return userService.getUserByMemberId(memberId, tenantId);
    }
}
