package com.petrov.databases.mapper.client;

import com.petrov.databases.dto.client.ClientDTO;
import com.petrov.databases.entity.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    Client clientDTOToClient(ClientDTO clientDTO);
    ClientDTO clientToClientDTO(Client client);
}
