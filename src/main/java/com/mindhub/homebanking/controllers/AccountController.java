package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping("/accounts")
    public List<AccountDTO> getAccounts(){
        return accountRepository.findAll()
                .stream()
                .map(account -> new AccountDTO(account))
                .collect(Collectors.toList());
    }

    @RequestMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id){
        return new AccountDTO(accountRepository.findById(id).orElse(null));
    }

    @RequestMapping(path = "/clients/current/accounts", method = RequestMethod.POST)
    public ResponseEntity<Object> addAccount(Authentication authentication){

        Client client = clientRepository.findByEmail(authentication.getName());

        if (client.getAccounts().size() == 3){
            return new ResponseEntity<>("maxim three accounts for client",
                    HttpStatus.FORBIDDEN);
        }

        Account account = new Account();

        String accountNumber = account.generateAccountNumber();

        if (accountRepository.findByNumber(accountNumber) != null){
            return new ResponseEntity<>("Account number already exist", HttpStatus.FORBIDDEN);
        }

        account.setNumber(account.generateAccountNumber());
        account.setCreationDate(LocalDate.now());
        account.setBalance(0d);

        client.addAccount(account);

        accountRepository.save(account);

        return new ResponseEntity<>("Account create successfully",HttpStatus.CREATED);
    }

    @RequestMapping("/clients/current/accounts")
    public Set<AccountDTO> getAccounts(Authentication authentication){
        Client client = clientRepository.findByEmail(authentication.getName());
        return client.getAccounts()
                .stream()
                .map(account -> new AccountDTO(account))
                .collect(Collectors.toSet());

    }

}
