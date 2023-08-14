package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Client;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    private Set<AccountDTO> accounts;

    private List<ClientLoanDTO> loans;

    public ClientDTO(Client client){
        id = client.getId();
        firstName = client.getFirstName();
        lastName = client.getLastName();
        email = client.getEmail();
        accounts = client.getAccounts().stream().map(account -> new AccountDTO(account)).collect(Collectors.toSet());
        loans = client.getLoans().stream().map(loan -> new ClientLoanDTO(loan)).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Set<AccountDTO> getAccounts() {
        return accounts;
    }

    public List<ClientLoanDTO> getLoans() {
        return loans;
    }
}
