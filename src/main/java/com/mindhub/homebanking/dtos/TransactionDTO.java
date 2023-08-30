package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;

import java.time.LocalDateTime;

public class TransactionDTO {
    private Long id;
    private Double amount;
    private TransactionType type;
    private String description;
    private LocalDateTime date;

    public TransactionDTO(Transaction transaction){
        id = transaction.getId();
        amount = transaction.getAmount();
        type = transaction.getType();
        description = transaction.getDescription();
        date = transaction.getDate();
    }

    public Long getId() {
        return id;
    }

    public Double getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDate() {
        return date;
    }
}
