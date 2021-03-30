package com.dxunited.core.auth.controller;

import com.dxunited.core.auth.model.request.CreateUserRequest;
import com.dxunited.core.auth.model.response.GenericResponse;
import com.dxunited.core.auth.model.spi.UserRepresentation;
import com.dxunited.core.auth.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.dxunited.core.auth.TestObjectFactory.createUserRequest;
import static com.dxunited.core.auth.constant.AuthConstants.TENANT_ID;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void createUserTest() throws Exception {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setLastName("lastname");
        when(userService.createUser(any(CreateUserRequest.class), anyString()))
                .thenReturn(new GenericResponse(true, 200, "Profile created succeesfully", null, "done"));

        mockMvc.perform(MockMvcRequestBuilders
                .post("/auth/users")
                .header(TENANT_ID, "1")
                .content(new ObjectMapper().writeValueAsString(createUserRequest()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void getUserByMemberIdTest() throws Exception {
        UserRepresentation user = new UserRepresentation();
        user.setUsername("xyz@gmail.com");
        when(userService.getUserByMemberId(anyString(), anyString())).thenReturn(user);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/auth/users/1")
                .header(TENANT_ID, "1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
