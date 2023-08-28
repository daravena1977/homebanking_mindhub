package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CardRepository cardRepository;

    @RequestMapping(path = "/clients/current/cards", method = RequestMethod.POST)
    public ResponseEntity<Object> addCard(Authentication authentication, @RequestParam CardType cardType,
                                          @RequestParam CardColor cardColor){

        if (cardType.describeConstable().isEmpty() || cardColor.describeConstable().isEmpty() ){
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        Client client = new Client();
        client = clientRepository.findByEmail(authentication.getName());

        Set<Card> cards = new HashSet<>();
               cards = client.getCards()
                       .stream()
                       .filter(card -> card.getType().equals(cardType))
                       .collect(Collectors.toSet());

        if (cards.size() == 3){
            return new ResponseEntity<>("Maxim three card for type", HttpStatus.FORBIDDEN);
        }

        if (cards.stream().anyMatch(card -> card.getColor().equals(cardColor))){
            return new ResponseEntity<>("This type of card already exist", HttpStatus.FORBIDDEN);
        }

        Card newCard = new Card(cardType, cardColor, LocalDate.now(), LocalDate.now().plusYears(5));

        String cardNumber = newCard.generateCardNumber();

        if (cardRepository.findByNumber(cardNumber) != null){
            return new ResponseEntity<>("This card number already exist", HttpStatus.FORBIDDEN);
        }

        newCard.setCardHolder(client.getFullName());
        newCard.setNumber(cardNumber);
        newCard.setCvv(newCard.generateCvvNumber());

        client.addCard(newCard);

        cardRepository.save(newCard);

        return new ResponseEntity<>("New Card created", HttpStatus.CREATED);

    }

    @RequestMapping("/clients/current/cards")
    public Set<CardDTO> getCards(Authentication authentication){
        Client client = clientRepository.findByEmail(authentication.getName());
        return client.getCards()
                .stream()
                .map(card -> new CardDTO(card))
                .collect(Collectors.toSet());

    }

}
