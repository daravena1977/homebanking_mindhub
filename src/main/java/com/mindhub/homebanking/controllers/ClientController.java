package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;

    @RequestMapping("/clients")
    public List<ClientDTO> getClients(){
        List<Client> clients = clientService.getClients();
        return clientService.getClientsDTO(clients);
    }

    @RequestMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id){
        return clientService.getClientDTO(id);
    }

    @RequestMapping(path = "/clients", method = RequestMethod.POST)
    public ResponseEntity<Object> register(@RequestParam String firstName, @RequestParam String lastName,
                                           @RequestParam String email, @RequestParam String password){
        if (firstName.isBlank()){
            return new ResponseEntity<>("The first name data is missing", HttpStatus.FORBIDDEN);
        }

        if (lastName.isBlank()){
            return new ResponseEntity<>("The last name data is missing", HttpStatus.FORBIDDEN);
        }

        if (email.isBlank()){
            return new ResponseEntity<>("The email data is missing", HttpStatus.FORBIDDEN);
        }

        if (password.isBlank()){
            return new ResponseEntity<>("The password data is missing", HttpStatus.FORBIDDEN);
        }

        if (clientService.findByEmail(email) != null){
            return new ResponseEntity<>("Name already in use",
                    HttpStatus.FORBIDDEN);
        }

        Client newClient = clientService.saveNewClientRegistered(firstName, lastName, email, password);

        Account newAccount = accountService.generateNewAccount();

        newClient.addAccount(newAccount);

        accountService.saveAccount(newAccount);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping("/clients/current")
    public ClientDTO getClient(Authentication authentication){
        return clientService.getClientDTOAuthenticated(authentication);
    }

}
