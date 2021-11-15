package com.example.task.controller;

import com.example.task.data.model.Client;
import com.example.task.data.model.ClientRequest;
import com.example.task.service.ClientJsonToken;
import com.example.task.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

public class ClientController {


    private final ClientService clientService;
    private final ClientJsonToken clientJsonToken;

    @Autowired
    public ClientController(ClientService clientService, ClientJsonToken clientJsonToken) {
        this.clientService = clientService;
        this.clientJsonToken = clientJsonToken;
    }

    @PostMapping(value = "/api/public")
    public ResponseEntity<?> create(@RequestBody ClientRequest clientRequest) {
        clientService.create(clientRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PostMapping(value = "/api/getToken")
    public String createToken(@RequestBody Client client) {

        return clientJsonToken.sendToken(client.getPassword());
    }

    @PostMapping(value = "/api/saveMessage")
    public String saveMessage(@RequestBody Client client) {

        return "Save message";
    }

    @GetMapping("/all")
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> footballers = clientService.getAllClient();
        return new ResponseEntity<>(footballers, HttpStatus.OK);
    }
}