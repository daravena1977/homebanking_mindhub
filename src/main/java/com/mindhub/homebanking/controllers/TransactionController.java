package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    @RequestMapping(path = "/transactions", method = RequestMethod.POST)
    public ResponseEntity<Object> createTransaction(Authentication authentication, @RequestParam String fromAccountNumber,
                                                    @RequestParam String toAccountNumber,
                                                    @RequestParam Double amount,
                                                    @RequestParam String description
                                                    ){

        Client client = clientRepository.findByEmail(authentication.getName());

        if (amount.isNaN() || description.isBlank() || fromAccountNumber.isBlank()
                || toAccountNumber.isBlank()){
            return new ResponseEntity<>("Missing Data", HttpStatus.FORBIDDEN);
        }

        if (fromAccountNumber.equals(toAccountNumber)){
            return new ResponseEntity<>("Accounts are the same", HttpStatus.FORBIDDEN);
        }

        if (!accountRepository.existsByNumber(fromAccountNumber)){
            return new ResponseEntity<>("this account number don't exists", HttpStatus.FORBIDDEN);
        }

        if (accountRepository.findByNumberAndClient(fromAccountNumber, client)== null){
            return new ResponseEntity<>("This account don't belongs to client", HttpStatus.FORBIDDEN);
        }

        if (!accountRepository.existsByNumber(toAccountNumber)){
            return new ResponseEntity<>("this account number don't exists", HttpStatus.FORBIDDEN);
        }

        if (accountRepository.findByNumber(fromAccountNumber).getBalance() < amount){
            return new ResponseEntity<>("This account don't have this amount available", HttpStatus.FORBIDDEN);
        }

        Transaction transactionDebit = new Transaction(-amount, TransactionType.DEBIT, description, LocalDateTime.now());
        Transaction transactionCredit = new Transaction(amount,TransactionType.CREDIT, description, LocalDateTime.now());

        accountRepository.findByNumber(fromAccountNumber).addTransaction(transactionDebit);
        accountRepository.findByNumber(toAccountNumber).addTransaction(transactionCredit);

        transactionRepository.save(transactionDebit);
        transactionRepository.save(transactionCredit);

        Double actualBalanceOriginAccount = accountRepository.findByNumber(fromAccountNumber).getBalance();
        accountRepository.findByNumber(fromAccountNumber).setBalance(actualBalanceOriginAccount-amount);

        Double actualBalanceObjectAccount = accountRepository.findByNumber(toAccountNumber).getBalance();
        accountRepository.findByNumber(toAccountNumber).setBalance(actualBalanceObjectAccount+amount);

        accountRepository.save(accountRepository.findByNumber(fromAccountNumber));
        accountRepository.save(accountRepository.findByNumber(toAccountNumber));

        return new ResponseEntity<>("Transaction has been successful", HttpStatus.CREATED);
    }


}
