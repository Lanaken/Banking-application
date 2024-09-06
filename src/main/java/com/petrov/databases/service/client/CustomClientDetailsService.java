package com.petrov.databases.service.client;

import com.petrov.databases.dto.client.ClientDTO;
import com.petrov.databases.entity.Client;
import com.petrov.databases.mapper.client.ClientMapper;
import com.petrov.databases.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.lang.StringTemplate.STR;

@Service
@Primary
public class CustomClientDetailsService implements UserDetailsService {
	
	 @Autowired
	 private ClientRepository clientRepository;

	 @Autowired
	 private ClientMapper clientMapper;

	 @Autowired
	 private ClientService clientService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Client client = clientService.getClientByEmail(username);
		ClientDTO clientDTO = clientMapper.clientToClientDTO(client);
		
		return new ClientDetail(clientDTO);

	}

}
