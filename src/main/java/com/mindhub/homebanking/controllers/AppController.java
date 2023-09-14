package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AppController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;

    @PostMapping("/clients")
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

}
