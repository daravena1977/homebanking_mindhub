package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @RequestMapping("/clients")
    public List<ClientDTO> getClients(){
        List<Client> clients = clientService.getClients();
        return clientService.getClientsDTO(clients);
    }

    @RequestMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id){
        return clientService.getClientDTO(id);
    }



    @RequestMapping("/clients/current")
    public ClientDTO getClient(Authentication authentication){
        return clientService.getClientDTOAuthenticated(authentication);
    }

}
