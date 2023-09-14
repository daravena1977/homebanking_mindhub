package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
class CardRepositoryTest {

    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private ClientRepository clientRepository;

    @Test
    void findByNumber() {
        Card card = cardRepository.findByNumber("4567-8866-4741-2123");
        assertThat(card, is(notNullValue()));
    }

    @Test
    void existsByClientAndTypeAndColor() {
        Client client = clientRepository.findById(4L).orElse(null);
        boolean existCard = cardRepository.existsByClientAndTypeAndColor(client, CardType.CREDIT, CardColor.TITANIUM );
        assertTrue(existCard);
    }
}