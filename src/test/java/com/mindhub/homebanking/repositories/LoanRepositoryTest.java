package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Loan;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
class LoanRepositoryTest {

    @Autowired
    private LoanRepository loanRepository;

    @Test
    void existsById() {
        boolean existsLoan = loanRepository.existsById(16L);
        assertTrue(existsLoan);
    }

    @Test
    void addLoan(){
        Loan loan = new Loan("Home repairs", 300000d, List.of(6, 12, 24, 36, 48));
        Loan loanSaved = loanRepository.save(loan);
        assertThat(loanSaved, notNullValue());
    }
}