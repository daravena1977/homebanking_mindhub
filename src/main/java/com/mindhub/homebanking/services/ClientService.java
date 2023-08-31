package com.mindhub.homebanking.services;


import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ClientService {

    Client findById(Long id);

    void saveClient(Client client);

    Client saveNewClientRegistered(String firstName, String lastName, String email, String password);

    void editClient(Client client);

    List<Client> getClients();

    List<ClientDTO> getClientsDTO(List<Client> clients);

    ClientDTO getClientDTO(Long id);

    ClientDTO getClientDTOAuthenticated(Authentication authentication);

    void deleteClient(Long id);

    void deleteClient(Client client);

    Client findByEmail(String email);


}
