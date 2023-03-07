package com.example.cloud.controllers;

import com.example.cloud.controller.AuthorizationController;
import com.example.cloud.entities.Credential;
import com.example.cloud.entities.User;
import com.example.cloud.service.AuthenticationServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class AuthorizationControllerTest {
    public MockMvc mockMvc;
    @Autowired
    public AuthorizationController authorizationController;

    @MockBean
    public AuthenticationServiceImpl authenticationService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authorizationController).build();
    }

    @Test
    void testSignIn() throws Exception {
        //given
        Credential credential = Credential.builder()
                .login("login")
                .password("password".toCharArray())
                .build();
        //when
        when(authenticationService.findUserByCredential(any()))
                .thenReturn(User.builder().id(1L)
                        .login("login")
                        .name("name")
                        .credential(credential)
                        .home("home")
                        .build());

        //then
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsBytes(credential)))
                .andExpect(status().is(200));

    }

    @Test
    void testSignOut() throws Exception {
        // given

        // when then
        mockMvc.perform(post("/logout"))
                .andExpect(status().is(204));
    }
}
