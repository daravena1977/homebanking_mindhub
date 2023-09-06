package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.Loan;

import java.util.List;

public interface LoanService {

    Loan findById(Long id);

    void saveLoan(Loan loan);

    void editLoan(Loan loan);

    List<Loan> getLoans();

    List<LoanDTO> getLoansDTO(List<Loan> loans);

    LoanDTO getLoanDTO(Long id);

    void deleteLoan(Long id);

    void deleteLoan(Loan loan);

    boolean existsById(Long id);


}
