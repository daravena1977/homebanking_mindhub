package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.ClientLoanDTO;
import com.mindhub.homebanking.models.ClientLoan;

import java.util.List;

public interface ClientLoanService {

    ClientLoan findById(Long id);

    void save(ClientLoan clientLoan);

    void edit(ClientLoan clientLoan);

    List<ClientLoan> getClientLoans();

    List<ClientLoanDTO> getClientLoansDTO(List<ClientLoan> clientLoans);

    ClientLoanDTO getClientLoanDTO(Long id);

    void deleteClientLoan(Long id);

    void deleteClientLoan(ClientLoan clientLoan);


}
