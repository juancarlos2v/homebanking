package com.mindhub.homebanking.services.implementation;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class ClientServiceImplementation implements ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Override
    public List<ClientDTO> getClients(){
        return  clientRepository.findAll()
                .stream().map(client -> new ClientDTO(client)).collect(toList());
    }

    @Override
    public ClientDTO getClient(Long id) {
        //return clientRepository.findById(id).map(client->new ClientDTO(client)).orElse(null);
        ClientDTO client=new ClientDTO(clientRepository.findById(id).orElse(null));
        return client ;
    }

    @Override
    public ClientDTO findByEmail(String email) {
        return new ClientDTO(clientRepository.findByEmail(email));
    }

    @Override
    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Client findClientByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

}
