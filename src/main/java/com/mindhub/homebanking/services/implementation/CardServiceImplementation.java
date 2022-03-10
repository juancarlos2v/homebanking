package com.mindhub.homebanking.services.implementation;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class CardServiceImplementation implements CardService {

    @Autowired
    CardRepository cardRepository;

    @Override
    public List<CardDTO> getCards() {
        return cardRepository.findAll()
                .stream().map(card -> new CardDTO(card)).collect(toList());
    }

    @Override
    public Card findByNumber(String number) {
        return cardRepository.findByNumber(number);
    }

    @Override
    public Card saveCard(Card card) {
        return cardRepository.save(card);
    }
}
