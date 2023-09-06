package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;

import java.util.List;

public interface CardService {

    Card findById(Long id);

    void saveCard(Card card);

    void editCard(Card card);

    List<Card> getCards();

    List<CardDTO> getCardsDTO(List<Card> cards);

    CardDTO getCardDTO(Long id);

    void deleteCard(Long id);

    void deleteAccount(Card card);

    String generateNewCardNumber();

    boolean existsByClientAndTypeAndColor(Client client, CardType type, CardColor color);




}
