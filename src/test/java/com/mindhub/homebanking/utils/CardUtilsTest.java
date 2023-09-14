package com.mindhub.homebanking.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CardUtilsTest {

    @Test
    void generateCardNumber() {
        String cardNumber = CardUtils.generateCardNumber();
        assertTrue(cardNumber.length()==19);
    }

    @Test
    void generateCvvNumber() {
        int cvvNumber = CardUtils.generateCvvNumber();
        assertTrue(cvvNumber >= 100 && cvvNumber < 1000);
    }
}