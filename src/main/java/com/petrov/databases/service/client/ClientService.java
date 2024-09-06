package com.petrov.databases.service.client;

import com.petrov.databases.dto.client.ClientDTO;
import com.petrov.databases.entity.Client;
import com.petrov.databases.exception.ClientNotFoundException;
import com.petrov.databases.exception.UserAlreadyExistsException;
import com.petrov.databases.mapper.client.ClientMapper;
import com.petrov.databases.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ClientService {
    private static final Logger log = LoggerFactory.getLogger(ClientService.class);
    private ClientRepository clientRepository;
    private ClientMapper clientMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void saveClient(ClientDTO clientDto) {
        Client client = clientMapper.clientDTOToClient(clientDto);
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        Example<Client> example = Example.of(client);
        if (!clientRepository.exists(example))
            clientRepository.save(client);
        else throw new UserAlreadyExistsException();
    }

    public void saveClient(Client client) {
        Example<Client> example = Example.of(client);
        clientRepository.save(client);
    }

    public Client getClient(Long id) {
        Optional<Client> client = clientRepository.findById(id);
        if (client.isPresent())
            return client.get();
        else throw new ClientNotFoundException();
    }

    public Client getClientByEmail(String username) {
        Client client = clientRepository
                .findByEmail(username)
                .orElseThrow(() -> new RuntimeException(STR."Client with name= \{ username } was not found"));
        return client;
    }

}
