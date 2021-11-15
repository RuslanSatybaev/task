package com.example.task.service;

import com.example.task.data.model.ClientRequest;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public interface ClientJsonToken {

    String createToken();

    String sendToken(String passwordFromClient);

    void saveMessage(ClientRequest clientRequest);

    String validateToken(HttpServletRequest request,ClientRequest clientRequest);
}
