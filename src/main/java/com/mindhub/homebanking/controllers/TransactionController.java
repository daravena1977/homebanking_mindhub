package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;

    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<Object> createTransaction(Authentication authentication, @RequestParam String fromAccountNumber,
                                                    @RequestParam String toAccountNumber,
                                                    @RequestParam Double amount,
                                                    @RequestParam String description
                                                    ){

        if (!authentication.isAuthenticated()){
            return new ResponseEntity<>("This user in not authenticated", HttpStatus.UNAUTHORIZED);
        }

        Client client = clientService.findByEmail(authentication.getName());

        if (amount.isNaN()){
            return new ResponseEntity<>("This is not a number", HttpStatus.FORBIDDEN);
        }

        if (description.isBlank()){
            return new ResponseEntity<>("The transaction's description is missing", HttpStatus.FORBIDDEN);
        }

        if (fromAccountNumber.isBlank()){
            return new ResponseEntity<>("The from account number is missing", HttpStatus.FORBIDDEN);
        }

        if (toAccountNumber.isBlank()){
            return new ResponseEntity<>("The account number object is missing", HttpStatus.FORBIDDEN);
        }

        if (fromAccountNumber.equals(toAccountNumber)){
            return new ResponseEntity<>("Accounts are the same", HttpStatus.FORBIDDEN);
        }

        if (!accountService.existsByNumber(fromAccountNumber)){
            return new ResponseEntity<>("this account number don't exists", HttpStatus.FORBIDDEN);
        }

        if (client.getAccounts().stream().noneMatch(account -> account.getNumber().equals(fromAccountNumber))){
            return new ResponseEntity<>("This account don't belongs to client", HttpStatus.FORBIDDEN);
        }

        if (!accountService.existsByNumber(toAccountNumber)){
            return new ResponseEntity<>("this account number don't exists", HttpStatus.FORBIDDEN);
        }

        Account fromAccount = accountService.findByNumber(fromAccountNumber);
        Account toAccount = accountService.findByNumber(toAccountNumber);

        if (fromAccount.getBalance() < amount){
            return new ResponseEntity<>("This account don't have this amount available", HttpStatus.FORBIDDEN);
        }

        Transaction transactionDebit = new Transaction(-amount, TransactionType.DEBIT,
                description.concat(" to ").concat(toAccountNumber), LocalDateTime.now());
        Transaction transactionCredit = new Transaction(amount,TransactionType.CREDIT,
                description.concat(" from ").concat(fromAccountNumber), LocalDateTime.now());

        fromAccount.addTransaction(transactionDebit);
        toAccount.addTransaction(transactionCredit);

        transactionService.saveTransaction(transactionDebit);
        transactionService.saveTransaction(transactionCredit);

        fromAccount.setBalance(fromAccount.getBalance()-amount);
        toAccount.setBalance(toAccount.getBalance()+amount);

        accountService.saveAccount(fromAccount);
        accountService.saveAccount(toAccount);

        return new ResponseEntity<>("Transaction has been successful", HttpStatus.CREATED);
    }


}
