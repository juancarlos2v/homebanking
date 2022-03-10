package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Card;

import java.util.List;

public interface CardService {
    public List<CardDTO> getCards();
    public Card findByNumber(String number);
    public Card saveCard(Card card);
}
