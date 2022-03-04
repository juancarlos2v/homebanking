package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.utils.CardUtils;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
public class CardController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CardRepository cardRepository;

    @GetMapping("/api/cards")
    public List<CardDTO> getCards(){
        return cardRepository.findAll()
                .stream().map(card -> new CardDTO(card)).collect(toList());
    }

    @PatchMapping("/api/clients/current/cards")
    public ResponseEntity<Object> activateCard(
            Authentication authentication, @RequestParam boolean activate, @RequestParam String number) {

        Client client = clientRepository.findByEmail(authentication.getName());
        Card card = cardRepository.findByNumber(number);

        boolean validate = false;
        for (Card c : client.getCards()
        ) {
            if (c.getNumber() == card.getNumber()) {
                validate = true;
            }
        }
        if (validate = false) {
            return new ResponseEntity<>("La tarjeta no pertenece al cliente.", HttpStatus.FORBIDDEN);
        }
        card.setActivate(activate);
        cardRepository.save(card);
        return new ResponseEntity<>("Solicitud procesada con exito.", HttpStatus.OK);
    }


    @PostMapping("/api/clients/current/cards")
    public ResponseEntity<Object> newCard(
            Authentication authentication,
            @RequestParam String cardType, @RequestParam String cardColor
    ) {
        Client client = clientRepository.findByEmail(authentication.getName());

        int debit = 0;
        int credit = 0;
        for (Card card : client.getCards()
        ) {
            if (card.getType().equals(CardType.DEBIT) && card.getActivate()==true ) {
                debit = 1 + debit;
            }
            if (card.getType().equals(CardType.CREDIT) && card.getActivate()==true) {
                credit = 1 + credit;
            }
        }

        switch(cardType){
            case "DEBIT":
                if (debit == 3) {
                    return new ResponseEntity<>("LIMITE DE TARJETAS DE DEBITOS", HttpStatus.FORBIDDEN);
                }
            case "CREDIT":
                if(credit==3){
                    return new ResponseEntity<>("LIMITE DE TARJETAS DE CREDITO", HttpStatus.FORBIDDEN);
                }
        }

        if (client.getCards().size() >= 6) {
            return new ResponseEntity<>("LIMITE DE TARJETAS GENERADAS", HttpStatus.FORBIDDEN);
        }
        int cvv = CardUtils.getCVV();
        String numberCard = CardUtils.getCardNumber();
        cardRepository.save(new Card(client.getFirstName() + " " + client.getLastName(), CardType.valueOf(cardType), CardColor.valueOf(cardColor), numberCard, cvv, LocalDate.now(), LocalDate.now().plusYears(5), client, true));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}