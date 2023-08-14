package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.ClientLoan;

public class ClientLoanDTO {
    private Long id;
    private Long idLoan;
    private String name;
    private double amount;
    private int payments;

    public ClientLoanDTO(ClientLoan clientLoan){
        id = clientLoan.getId();
        idLoan = clientLoan.getLoan().getId();
        name = clientLoan.getLoan().getName();
        amount = clientLoan.getAmount();
        payments = clientLoan.getPayments();
    }

    public Long getId() {
        return id;
    }

    public Long getIdLoan() {
        return idLoan;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public int getPayments() {
        return payments;
    }
}
