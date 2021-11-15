package com.example.task.service;

import com.example.task.data.model.Client;
import com.example.task.data.model.ClientRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ClientService {

    void create(ClientRequest clientRequest);

    List<Client> readAll();

    Client read(int id);

    boolean update(Client client, int id);

    boolean delete(int id);

    List<Client> getAllClient();

}
