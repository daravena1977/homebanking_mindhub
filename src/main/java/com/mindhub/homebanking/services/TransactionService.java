package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.TransactionDTO;
import com.mindhub.homebanking.models.Transaction;

import java.util.List;

public interface TransactionService {

    Transaction findById(Long id);

    void saveTransaction(Transaction transaction);

    void editTransaction(Transaction transaction);

    List<Transaction> getTransactions();

    List<TransactionDTO> getTransactionsDTO(List<Transaction> transactions);

    TransactionDTO getTransactionDTO(Long id);

    void deleteTransaction(Long id);

    void deleteTransaction(Transaction transaction);


}
