package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.utils.CardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    private CardService cardService;

    @Autowired
    private ClientService clientService;

    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> addCard(Authentication authentication, @RequestParam CardType cardType,
                                          @RequestParam CardColor cardColor){

        if (!authentication.isAuthenticated()){
            return new ResponseEntity<>("This user in not authenticated", HttpStatus.UNAUTHORIZED);
        }

        if (!cardType.equals(CardType.CREDIT) && !cardType.equals(CardType.DEBIT)){
            return new ResponseEntity<>("This card type is missing", HttpStatus.FORBIDDEN);
        }

        if (!cardColor.equals(CardColor.GOLD) && !cardColor.equals(CardColor.SILVER) && !cardColor.equals(CardColor.TITANIUM)) {
            return new ResponseEntity<>("This card color is missing", HttpStatus.FORBIDDEN);
        }

        Client client = clientService.findByEmail(authentication.getName());

        if (cardService.existsByClientAndTypeAndColor(client, cardType, cardColor)){
            return new ResponseEntity<>("This type of card already exist", HttpStatus.FORBIDDEN);
        }

        Card newCard = new Card(cardType, cardColor, LocalDate.now(), LocalDate.now().plusYears(5));

        String cardNumber = cardService.generateNewCardNumber();

        newCard.setCardHolder(client.getFullName());
        newCard.setNumber(cardNumber);
        newCard.setCvv(CardUtils.generateCvvNumber());

        client.addCard(newCard);

        cardService.saveCard(newCard);

        return new ResponseEntity<>("New Card created", HttpStatus.CREATED);

    }

    @GetMapping("/clients/current/cards")
    public Set<CardDTO> getCards(Authentication authentication){
        Client client = clientService.findByEmail(authentication.getName());
        return client.getCards()
                .stream()
                .map(card -> new CardDTO(card))
                .collect(Collectors.toSet());

    }

}
