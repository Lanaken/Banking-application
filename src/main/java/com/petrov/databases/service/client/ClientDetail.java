package com.petrov.databases.service.client;

import com.petrov.databases.dto.client.ClientDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class ClientDetail implements UserDetails {
    private ClientDTO client;

    public ClientDetail(ClientDTO clientDTO) {
        this.client = clientDTO;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> client.getRole());
    }

    @Override
    public String getPassword() {
        return client.getPassword();
    }

    @Override
    public String getUsername() {
        return client.getEmail();
    }

    public String getFirstName() {
        return client.getFirstName();
    }

}
