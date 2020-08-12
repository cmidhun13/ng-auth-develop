package com.szells.gce.auth.controller;

import com.szells.gce.auth.constant.AuthConstants;
import com.szells.gce.auth.model.request.LogoutRequest;
import com.szells.gce.auth.model.response.GenericResponse;
import com.szells.gce.auth.service.LogoutService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * Rest Controller class serves the logout endpoint
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/")
public class LogoutController {

    private final LogoutService logoutService;

    /**
     * Rest endpoint to handle user logout action
     *
     * @param logoutRequest the logout request body
     * @param tenantId      the client-id header
     * @return message
     */
    @ApiOperation(value = "Invalidate the user session and token")
    @ApiResponses(value = {
            @ApiResponse(code = 3020, message = "Invalid client credentials or refresh token"),
            @ApiResponse(code = 3001, message = "One or more parameter(s) required is missing in the header"),
            @ApiResponse(code = 3002, message = "One or more parameter(s) is invalid in the header")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = AuthConstants.TENANT_ID, value = "Tenant Id", required = true, paramType = "header", dataTypeClass = String.class),
            @ApiImplicitParam(name = AuthConstants.X_CORRELATION_ID, value = "Correlation Id", required = true, paramType = "header", dataTypeClass = String.class)
    })
    @PostMapping("/invalidate")
    public GenericResponse logout(@RequestBody LogoutRequest logoutRequest,
                                  @RequestHeader(AuthConstants.TENANT_ID) String tenantId
    ) {
        return logoutService.logout(logoutRequest, tenantId);

        //System.out.println(HttpStatus.OK);

        //return new ResponseEntity(new LogOutResponse("{success: true, 'message' : 'User successfully logged out.', 'data': null, 'status': 200}"), HttpStatus.OK);
    }
}
