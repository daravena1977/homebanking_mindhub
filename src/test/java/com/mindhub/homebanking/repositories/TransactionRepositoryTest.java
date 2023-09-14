package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.controllers.TransactionController;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Test
    void existsById() {
        boolean existsTransaction = transactionRepository.existsById(13L);
        assertTrue(existsTransaction);
    }

    @Test
    void addTransaction(){
        Account account = accountRepository.findByNumber("VIN-004");
        Transaction transaction = new Transaction(10000d, TransactionType.CREDIT,
                "Payment for ren", LocalDateTime.now());
        account.addTransaction(transaction);
        transactionRepository.save(transaction);
        accountRepository.save(account);
        assertThat(transactionRepository.findById(transaction.getId()), notNullValue());
        assertThat(account.getTransactions().stream().anyMatch(transaction1 -> transaction1.getId().equals(transaction.getId())),notNullValue());
    }


}