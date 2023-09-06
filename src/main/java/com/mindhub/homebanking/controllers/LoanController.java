package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ClientLoanService clientLoanService;

    @Autowired
    private TransactionService transactionService;

    @RequestMapping("/loans")
    public List<LoanDTO> getLoans(){
        List<Loan> loans = loanService.getLoans();
        return loanService.getLoansDTO(loans);
    }

    @Transactional
    @RequestMapping(path = "/loans", method = RequestMethod.POST)
    public ResponseEntity<Object> createLoan(Authentication authentication,
                                         @RequestBody LoanApplicationDTO loanApplicationDTO){
        if (!authentication.isAuthenticated()){
            return new ResponseEntity<>("This user in not authenticated", HttpStatus.UNAUTHORIZED);
        }

        Client client = clientService.findByEmail(authentication.getName());

        if (client == null){
            return new ResponseEntity<>("This user don't exists in the database", HttpStatus.FORBIDDEN);
        }

        if (loanApplicationDTO.getLoanId().describeConstable().isEmpty()){
            return new ResponseEntity<>("The loan id is missing", HttpStatus.FORBIDDEN);
        }

        if (loanApplicationDTO.getAmount().isNaN()){
            return new ResponseEntity<>("The amount loan data is missing", HttpStatus.FORBIDDEN);
        }

        if (loanApplicationDTO.getPayments().describeConstable().isEmpty()){
            return new ResponseEntity<>("The payments loan data is missing", HttpStatus.FORBIDDEN);
        }

        if (loanApplicationDTO.getToAccountNumber().isBlank()){
            return new ResponseEntity<>("The to account loan data is missing", HttpStatus.FORBIDDEN);
        }

        if (loanApplicationDTO.getAmount() <= 0){
            return new ResponseEntity<>("You must be provide a valor greater than zero", HttpStatus.FORBIDDEN);
        }

        if (loanApplicationDTO.getPayments() <= 0){
            return new ResponseEntity<>("You must be provide a valor greater than zero", HttpStatus.FORBIDDEN);
        }

        if (!loanService.existsById(loanApplicationDTO.getLoanId())){
            return new ResponseEntity<>("This loan type don't exists", HttpStatus.FORBIDDEN);
        }

        Loan loanApply = loanService.findById(loanApplicationDTO.getLoanId());

        if (loanApplicationDTO.getAmount() > loanApply.getMaxAmount()){
            return new ResponseEntity<>("The amount was requested in greater than max amount for this loan",
                    HttpStatus.FORBIDDEN);
        }

        if (loanApply.getPayments().stream().noneMatch(payment -> payment.equals(loanApplicationDTO.getPayments()))){
            return new ResponseEntity<>("This payments number is not available for this loan type",
                    HttpStatus.FORBIDDEN);
        }

        if (!accountService.existsByNumber(loanApplicationDTO.getToAccountNumber())){
            return new ResponseEntity<>("This account object don't exists", HttpStatus.FORBIDDEN);
        }

        if (!accountService.existsByNumberAndClient(loanApplicationDTO.getToAccountNumber(), client)){
            return new ResponseEntity<>("This account don't belong to client authenticated", HttpStatus.FORBIDDEN);
        }

        ClientLoan newClientLoan = new ClientLoan(loanApplicationDTO.getAmount()*1.20,
                loanApplicationDTO.getPayments());

        client.addLoan(newClientLoan);

        loanApply.addLoan(newClientLoan);

        clientLoanService.save(newClientLoan);

        Transaction newTransaction = new Transaction(loanApplicationDTO.getAmount(), TransactionType.CREDIT,
                "Loan approved", LocalDateTime.now());

        Account account = accountService.findByNumber(loanApplicationDTO.getToAccountNumber());

        account.addTransaction(newTransaction);
        account.setBalance(account.getBalance()+loanApplicationDTO.getAmount());

        transactionService.saveTransaction(newTransaction);

        accountService.saveAccount(account);

        return new ResponseEntity<>("This loan has been created successfully", HttpStatus.CREATED);
    }


}
