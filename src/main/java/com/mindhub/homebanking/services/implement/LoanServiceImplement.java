package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.LoanRepository;
import com.mindhub.homebanking.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanServiceImplement implements LoanService {
    @Autowired
    private LoanRepository loanRepository;

    @Override
    public Loan findById(Long id) {
        return loanRepository.findById(id).orElse(null);
    }

    @Override
    public void saveLoan(Loan loan) {
        loanRepository.save(loan);
    }

    @Override
    public void editLoan(Loan loan) {
        loanRepository.save(loan);
    }

    @Override
    public List<Loan> getLoans() {
        return loanRepository.findAll();
    }

    @Override
    public List<LoanDTO> getLoansDTO(List<Loan> loans) {
        return loans
                .stream()
                .map(loan -> new LoanDTO(loan))
                .collect(Collectors.toList());
    }

    @Override
    public LoanDTO getLoanDTO(Long id) {
        return new LoanDTO(loanRepository.findById(id).orElse(null));
    }

    @Override
    public void deleteLoan(Long id) {
        loanRepository.deleteById(id);
    }

    @Override
    public void deleteLoan(Loan loan) {
        loanRepository.delete(loan);
    }

    @Override
    public boolean existsById(Long id) {
        return loanRepository.existsById(id);
    }
}
