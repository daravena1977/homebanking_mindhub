package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.utils.CardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardServiceImplement implements CardService {

    @Autowired
    private CardRepository cardRepository;

    @Override
    public Card findById(Long id) {
        return cardRepository.findById(id).orElse(null);
    }

    @Override
    public void saveCard(Card card) {
        cardRepository.save(card);
    }

    @Override
    public void editCard(Card card) {
        cardRepository.save(card);
    }

    @Override
    public List<Card> getCards() {
        return cardRepository.findAll();
    }

    @Override
    public List<CardDTO> getCardsDTO(List<Card> cards) {
        return cards
                .stream()
                .map(card -> new CardDTO(card))
                .collect(Collectors.toList());
    }

    @Override
    public CardDTO getCardDTO(Long id) {
        return new CardDTO(cardRepository.findById(id).orElse(null));
    }

    @Override
    public void deleteCard(Long id) {
        cardRepository.deleteById(id);
    }

    @Override
    public void deleteAccount(Card card) {
        cardRepository.delete(card);
    }

    @Override
    public String generateNewCardNumber() {

        String newCardNumber;

        do{
            newCardNumber = CardUtils.generateCardNumber();
        }while (cardRepository.findByNumber(newCardNumber)!=null);

        return newCardNumber;
    }

    @Override
    public boolean existsByClientAndTypeAndColor(Client client, CardType type, CardColor color) {
        return cardRepository.existsByClientAndTypeAndColor(client, type, color);
    }
}
