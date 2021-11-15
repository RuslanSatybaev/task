package com.example.task.service;

import com.example.task.data.model.Client;
import com.example.task.data.model.ClientRequest;
import com.example.task.data.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImp implements ClientService {
    @Autowired
    ClientRepository clientRepository;

    @Override
    public void create(ClientRequest clientRequest) {
        Client client = new Client();
        client.setName(clientRequest.getName());
        client.setMessage(client.getMessage());

        clientRepository.save(client);
    }

    @Override
    public List<Client> readAll() {
        return null;
    }

    @Override
    public Client read(int id) {
        return null;
    }

    @Override
    public boolean update(Client client, int id) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public List<Client> getAllClient() {
        return null;
    }
}
